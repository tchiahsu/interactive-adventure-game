package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The {@code GameModel} class implements the game logic for the adventure game.
 * It handles player movement, inventory management and interaction with game elements.
 * It also manages saving and loading adventure games.
 */
public class GameModel implements IGameModel {
  // Constant
  private static final String ITEM_DELIMITER = ", ";
  // Game Data Fields
  private final String jsonFile;
  private final ObjectMapper objectMapper;
  private GameInfo gameInfo;
  // Game State Fields
  private GameData gameData;
  private ILocation currentRoom;
  private IPlayer player;

  /**
   * Constructor for initializing the GameModel class.
   * Loads the game data from the given JSON file and initializes the adventure game.
   *
   * @param jsonFile : the JSON file with data.
   * @throws IOException handles file not found error.
   */
  public GameModel(String jsonFile) throws IOException {
    this.jsonFile = jsonFile;
    this.objectMapper = new ObjectMapper();
    this.gameInfo = this.objectMapper.readValue(new File(jsonFile), GameInfo.class);
    this.gameData = new GameData(this.gameInfo);

    // Set the starting conditions for the game
    this.currentRoom = gameData.getRoom("1");
    this.player = new Player();
  }

  /**
   * Solves a puzzle with an answer.
   * If there is an active monster in the room, the monster attacks after the answer.
   *
   * @param answer The answer to the puzzle.
   * @return The sequence of events from solving a puzzle.
   */
  @Override
  public String answer(String answer) {
    StringBuilder output = new StringBuilder();

    // Handle answering with no puzzle in the room
    if (this.currentRoom.getPuzzleName() == null) {
      output.append("You answered, but no one heard you.\n");
      // Handle action with an active monster in the room
      return handleMonsterAttack(output.toString());
    }

    Puzzle puzzle = getPuzzleInRoom();
    // Answer matches the puzzle's solution
    if (puzzle.isActive() && puzzle.getSolution().equalsIgnoreCase(answer)) {
      puzzle.deactivate();
      player.increaseScore(puzzle.getValue());
      output.append("SUCCESS! You solved the puzzle with ").append(answer).append("\n");
      // Answer does not match the puzzle's solution or the puzzle is inactive
    } else {
      output.append("Your answer ").append(answer).append(" did nothing.\n");
    }

    return handleMonsterAttack(output.toString());
  }

  /**
   * Display the contents of the player's inventory.
   *
   * @return a string representation of the player's inventory
   */
  @Override
  public String checkInventory() {
    return this.player.getInventory().toString() + "\n";
  }

  /**
   * Drops an item from the player's inventory into a room.
   * Checks that the action is valid before performing the action.
   * If there is an active monster in the room, the monster attacks after the action.
   *
   * @param itemName The item to drop.
   * @return The sequence of events from dropping an item.
   */
  @Override
  public String dropItem(String itemName) {
    itemName = itemName.toUpperCase();
    StringBuilder output = new StringBuilder();

    // Check if player has the item
    if (!playerHasItem(itemName)) {
      output.append("You don't have ").append(itemName).append("\n");
      return handleMonsterAttack(output.toString());
    }

    // Remove item from player inventory
    Item item = this.gameData.getItem(itemName);
    Inventory playerInventory = this.player.getInventory();
    playerInventory.removeItem(item);
    playerInventory.setCurrentCapacity(playerInventory.getCurrentCapacity() - item.getWeight());
    this.player.decreaseScore(item.getValue());

    // Add the item to the room's list of items
    String roomItemNames = this.currentRoom.getItemNames();
    String updatedRoomItemNames =
      roomItemNames.isEmpty() ? item.getName() : roomItemNames.concat(", " + item.getName());
    this.currentRoom.setItemNames(updatedRoomItemNames);
    output.append(item.getName()).append(" removed from inventory.\n");

    // Handle action with an active monster in the room
    return handleMonsterAttack(output.toString());
  }

  /**
   * Examines an object in the current room or in the player's inventory.
   * You can examine fixtures, puzzles, monsters, and items.
   * If there is an active monster in the room, the monster attacks after an examination.
   *
   * @param objectName : name of the object we are examining
   * @return a description of object we are examining
   */
  @Override
  public String examine(String objectName) {
    objectName = objectName.toUpperCase();
    String output;

    if (roomHasItem(objectName) || playerHasItem(objectName)) {
      output = examineItem(objectName);
    } else if (roomHasFixture(objectName)) {
      output = examineFixture(objectName);
    } else if (objectName.equalsIgnoreCase(this.currentRoom.getMonsterName())) {
      output = examineMonster();
    } else if (objectName.equalsIgnoreCase(this.currentRoom.getPuzzleName())) {
      output = examinePuzzle();
    } else if (objectName.equals("ME")) {
      output = player.getName() + " I know you! You're a fearless adventurer!\n";
    } else {
      output = "There is no " + objectName + " to examine.\n";
    }

    return handleMonsterAttack(output);
  }

  /**
   * Gets the ending message when the player quits or goes to sleep in the game.
   *
   * @return end game message
   */
  @Override
  public String getEndingMessage() {
    return "Thank you for playing "
      + this.player.getName()
      + "!\nYour score is "
      + this.player.getScore()
      + "\nYour rank: "
      + this.player.getRank()
      + "\n";
  }

  @Override
  public String[] getExaminableObjects() {
    ArrayList<String> examinableObjects = new ArrayList<>();

    if (!currentRoom.getItemNames().isEmpty()) {
      List<String> roomsItemNames = getItemList(this.currentRoom.getItemNames());
      roomsItemNames.stream().forEach(item -> examinableObjects.add(item));
    }

    if (currentRoom.getMonsterName() != null) {
      examinableObjects.add(currentRoom.getMonsterName());
    }

    if (currentRoom.getPuzzleName() != null) {
      examinableObjects.add(currentRoom.getPuzzleName());
    }

    if (!roomHasActivePuzzle()) {
      List<String> fixtureNames = getItemList(this.currentRoom.getFixtureNames());
      fixtureNames.stream().forEach(fixture -> examinableObjects.add(fixture));
    }

    examinableObjects.add("ME");
    return examinableObjects.toArray(
            new String[examinableObjects.size()]);
  }

  @Override
  public List<String> getCurrentState() {
    List<String> currentState = new ArrayList<>();
    currentState.add(currentRoom.getName());

    StringBuilder output = new StringBuilder();
    if (roomHasActiveMonster()) {
      Monster monster = getMonsterInRoom();
      currentState.add(monster.getPicture());
      output.append(monster.getActiveDescription() + "\n");
      output.append(monster.getName() + " " + monster.getAttackMessage() + "\n");
      output.append("You took " + monster.getDamage() + " damage!\n");
    } else if (roomHasActivePuzzle()) {
      currentState.add(getPuzzleInRoom().getPicture());
      output.append(getPuzzleInRoom().getActiveDescription()).append("\n");
    } else {
      currentState.add(currentRoom.getPicture());
      output.append(currentRoom.getDescription()).append("\n");
    }
    currentState.add(getItemsInRoom(output.toString()));
    currentState.add(player.getHealthStatus());
    currentState.add(String.valueOf(player.getHealth()));
    currentState.add(String.valueOf(player.getScore()));
    currentState.add(
            player.getInventory().getItems()
                    .stream()
                    .map(item -> item.getName()).collect(Collectors.joining(",")));

    return currentState;
  }

  @Override
  public String[] getInventoryItems() {
    List<Item> itemList = this.player.getInventory().getItems();
    String[] itemNames = new String[itemList.size()];
    for (int i = 0; i < itemList.size(); i++) {
      itemNames[i] = itemList.get(i).getName();
    }
    return itemNames;
  }

  @Override
  public String[] getCurrentRoomItem() {
    String[] currentRoomItems = {currentRoom.getItemNames(), currentRoom.getFixtureNames()};
    return currentRoomItems;
  }

  @Override
  public String getImagePath(String object) {
    if (gameData.getItem(object) != null) {
      Item item = gameData.getItem(object);
      return item.getPicture();
    } else if (gameData.getFixture(object) != null) {
      Fixture fixture = gameData.getFixture(object);
      return fixture.getPicture();
    } else if (gameData.getMonster(object) != null) {
      Monster monster = gameData.getMonster(object);
      return monster.getPicture();
    } else if (gameData.getPuzzle(object) != null) {
      Puzzle puzzle = gameData.getPuzzle(object);
      return puzzle.getPicture();
    } else {
      return "/data/Resources/epic_adventurer.png";
    }
  }

  /**
   * Gets the player object.
   *
   * @return the player object
   */
  @Override
  public IPlayer getPlayer() {
    return this.player;
  }

  /**
   * Provides the room descriptions and the contents of the room.
   * If there is a monster in the room, they will deal damage to the player.
   *
   * @return a String describing what the player sees and what's happening in the room.
   */
  @Override
  public String look() {
    String result = buildRoomDescription(true);
    return result + this.player.getHealthStatus();
  }

  /**
   * Moves the player in a given direction if the direction is valid.
   * Handles special cases such as monsters or puzzles blocking paths.
   *
   * @param direction : direction player wants to move to
   * @return a description describing the state of the game after the move
   */
  @Override
  public String move(String direction) {
    StringBuilder output = new StringBuilder();
    String nextRoom = this.currentRoom.getPath(direction);
    int nextRoomNumber = Integer.parseInt(nextRoom);

    if (nextRoomNumber == 0) {
      // Invalid direction - no path
      output.append("<<You cannot go in that direction>>\n");
      return handleMonsterAttack(output.toString());
    } else if (nextRoomNumber < 0) {
      // path blocked by monster or puzzle
      if (roomHasActiveMonster()) {
        output.append("<<You are being blocked from moving that way!>>\n");
        output.append(getMonsterInRoom().getActiveDescription()).append("\n");
        output.append(monsterAttacks(""));
        return output.toString();
      } else if (roomHasActivePuzzle()) {
        output.append("<<You are being blocked from moving that way!>>\n");
        output.append(getPuzzleInRoom().getActiveDescription()).append("\n");
        return output.toString();
      } else {
        // Convert negative room to positive once obstacle is defeated to allow passage
        this.currentRoom = gameData.getRoom(String.valueOf(Math.abs(nextRoomNumber)));
      }
    } else {
      // Valid path to a new room
      this.currentRoom = gameData.getRoom(nextRoom);
    }

    // Create the description for the new room
    return buildRoomDescription(false);
  }

  /**
   * Restore the previously saved game state from the files.
   * Loads the game information, current room and player state.
   *
   * @return a message indicating the file was loaded successfully.
   * @throws IOException if there is an error reading the saved files.
   */
  @Override
  public String restoreGame() throws IOException {
    String gameFile = Paths.get(this.jsonFile).getFileName().toString();

    // Check if there is saved data
    if (!hasSaveData(gameFile)) {
      return "No game file to load.\n";
    }

    // Clear out the current data in the game
    this.gameInfo = null;
    this.currentRoom = null;
    this.player = null;
    this.gameData = null;

    // Restore the game with the previous save data
    this.gameInfo = this.objectMapper.readValue(
      new File("src/data/savegamedata_" + gameFile), GameInfo.class);
    this.currentRoom = this.objectMapper.readValue(
      new File("src/data/saveroomdata_" + gameFile), Room.class);
    this.player = this.objectMapper.readValue(
      new File("src/data/saveplayerdata_" + gameFile), Player.class);
    this.gameData = new GameData(gameInfo);

    updatePlayerInventory();

    return "Game loaded successfully!\n";
  }

  /**
   * Saves the current game state to a file.
   *
   * @return a message indicating the file was saved successfully
   * @throws IOException if there is an error writing to the file.
   */
  @Override
  public String saveGame() throws IOException {
    try {
      String gameFile = Paths.get(this.jsonFile).getFileName().toString();
      this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(
        new File("src/data/savegamedata_" + gameFile), this.gameInfo);
      this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(
        new File("src/data/saveroomdata_" + gameFile), this.currentRoom);
      this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(
        new File("src/data/saveplayerdata_" + gameFile), this.player);
      return "Game saved successfully!\n";
    } catch (IOException e) {
      return "Game failed to save.\n";
    }
  }

  /**
   * Takes an item from a room and places it in the player's inventory.
   * Validates that the item exists in the room and if the player is able to carry the item.
   * If there is an active monster in the room, the monster attacks after taking an item.
   *
   * @param itemName The name of the item in the room.
   * @return a string description the result of taking the item.
   */
  @Override
  public String takeItem(String itemName) {
    itemName = itemName.toUpperCase();
    StringBuilder output = new StringBuilder();
    // Items in a room are stored as: "ITEM1, ITEM2, ITEM3"
    // This will create a list of ["ITEM1", "ITEM2", "ITEM3"]
    List<String> roomsItemNames = getItemList(this.currentRoom.getItemNames());

    // Handle item not being in the room's list of items
    if (!roomsItemNames.contains(itemName)) {
      if (isUnmovableObject(itemName)) {
        output.append("You can't take the ").append(itemName).append("!\n");
        return handleMonsterAttack(output.toString());
      } else {
        output.append(itemName).append(" not found in ").append(this.currentRoom.getName())
          .append("\n");
        // Handle action with an active monster in the room
        return handleMonsterAttack(output.toString());
      }
    }

    // Try to add item to player's inventory
    Item item = this.gameData.getItem(itemName);
    Inventory playerInventory = this.player.getInventory();
    int newWeight = playerInventory.getCurrentCapacity() + item.getWeight();
    // Check if inventory is full
    if (newWeight > playerInventory.getMaxCapacity()) {
      output.append("Your inventory is too full!\n");
    }
    else {
      // Add item to inventory and remove from the room
      playerInventory.addItem(item);
      playerInventory.setCurrentCapacity(newWeight);
      this.player.increaseScore(item.getValue());
      roomsItemNames.remove(item.getName());

      // Update room items
      String updatedRoomItemNames = String.join(ITEM_DELIMITER, roomsItemNames);
      this.currentRoom.setItemNames(updatedRoomItemNames);

      output.append(item.getName()).append(" added to inventory.\n");
    }
    return handleMonsterAttack(output.toString());
  }

  /**
   * Uses an item on an obstacle in the room.
   * Items can be used to defeat monsters or solve puzzles if the solution to them matches the
   * item that is being used.
   * After using the item, if the monster is still active it attacks.
   *
   * @param itemName : name of the item being used
   * @return the resulting sequence of events from using the item
   */
  @Override
  public String useItem(String itemName) {
    itemName = itemName.toUpperCase();
    StringBuilder output = new StringBuilder();

    // Check if item is in player's inventory
    if (!playerHasItem(itemName)) {
      output.append(itemName).append(" not found in inventory.\n");
      return handleMonsterAttack(output.toString());
    }

    Item item = this.gameData.getItem(itemName);

    // Check if item can still be used
    if (item.getUsesRemaining() == 0) {
      output.append("Oh no! ").append(item.getName())
        .append(" is either empty or cannot be used again!\n");
      return handleMonsterAttack(output.toString());
    }

    if (currentRoom.getMonsterName() != null) {
      return useItemOnObstacle(item, getMonsterInRoom());
    } else if (currentRoom.getPuzzleName() != null) {
      return useItemOnObstacle(item, getPuzzleInRoom());
    }

    return "Using " + item.getName() + " did nothing.\n";
  }

  /**
   * Helper method. Converts a string of items into a list.
   *
   * @param itemString : string of items (separated by a comma).
   * @return a list of items/objects
   */
  private List<String> getItemList(String itemString) {
    if (itemString == null || itemString.isEmpty()) {
      return new ArrayList<>();
    }
    return new ArrayList<>(Arrays.asList(itemString.split(ITEM_DELIMITER)));
  }

  /**
   * Helper method. Determines if an item can be used on an obstacle.
   *
   * @param item : item we are attempting to use.
   * @param obstacle : obstacle we are using the item on.
   * @return message indicating success or failure.
   */
  private String useItemOnObstacle(Item item, IObstacle obstacle) {
    StringBuilder output = new StringBuilder();
    String solution = obstacle.getSolution();
    boolean isActive = obstacle.isActive();
    int value = obstacle.getValue();

    if (isActive && solution.equalsIgnoreCase(item.getName())) {
      item.reduceUse();
      obstacle.deactivate();

      this.player.increaseScore(value);
      output.append("SUCCESS ").append(item.getWhenUsedDescription()).append("\n");
    } else {
      output.append("Using ").append(item.getName()).append(" did nothing.\n");

      if (this.currentRoom.getMonsterName() != null) {
        return handleMonsterAttack(output.toString());
      }
    }

    return output.toString();
  }

  /**
   * Helper method. Converts a string separated by commas into a list.
   *
   * @return a string describing the room.
   */
  private String buildRoomDescription(boolean looking) {
    String result = "You are "
      + (looking ? "standing " : "")
      + (this.currentRoom.getName().startsWith("the ") ? "in " : "in the ")
      + this.currentRoom.getName() + "\n"
      + getCurrentRoomDescription();

    if (roomHasActiveMonster()) {
      result = monsterAttacks(result);
    }

    if (this.player.getHealth() > 0) {
      result = getItemsInRoom(result);
    }

    return result;
  }

  /**
   * Returns an output message with the items in the room added to it.
   *
   * @param result message to add items onto to return to the player.
   * @return an output message with the items in the room added to it.
   */
  private String getItemsInRoom(String result) {
    return result + "Items you see here: " + this.currentRoom.getItemNames() + "\n";
  }

  /**
   * Helper method. Gets the description for the room, the description of the room
   * changes based on whether there is a monster, puzzle or neither.
   *
   * @return description of the current room.
   */
  private String getCurrentRoomDescription() {
    if (roomHasActiveMonster()) {
      return getMonsterInRoom().getActiveDescription() + "\n";
    } else if (roomHasActivePuzzle()) {
      return getPuzzleInRoom().getActiveDescription() + "\n";
    } else {
      return this.currentRoom.getDescription() + "\n";
    }
  }

  /**
   * Helper method. Gets the monster in the current room.
   *
   * @return the monster object in the current room.
   */
  private Monster getMonsterInRoom() {
    String monsterName = this.currentRoom.getMonsterName();
    return this.gameData.getMonster(monsterName);
  }

  /**
   * Helper method that Gets the puzzle in the current room.
   *
   * @return the puzzle object in the current room
   */
  private Puzzle getPuzzleInRoom() {
    String puzzleName = this.currentRoom.getPuzzleName();
    return this.gameData.getPuzzle(puzzleName);
  }

  /**
   * Helper method that handles monster attacks after an action.
   * If there is an active monster after a command, it attacks the player.
   * Otherwise, no attack sequence is triggered.
   *
   * @param output : the output to append to attack results
   * @return the output with the attack results
   */
  private String handleMonsterAttack(String output) {
    return roomHasActiveMonster() ? monsterAttacks(output) : output;
  }

  /**
   * Helper method that determines whether an object is unmovable or not.
   * Fixtures are the only unmovable object.
   *
   * @param objectName : object we are trying to move.
   * @return true if it's unmovable.
   */
  private boolean isUnmovableObject(String objectName) {
    if (roomHasFixture(objectName)) {
      return true;
    }

    String monsterName = this.currentRoom.getMonsterName();
    if (monsterName != null && monsterName.equalsIgnoreCase(objectName)) {
      return true;
    }

    String puzzleName = this.currentRoom.getPuzzleName();
    return puzzleName != null && puzzleName.equalsIgnoreCase(objectName);
  }

  /**
   * Helper method. Examines an item either in the room or the players inventory.
   *
   * @param itemName item we are examining
   * @return description of the examined item
   *          description changes a little based on where the item is located
   */
  private String examineItem(String itemName) {
    Item item = this.gameData.getItem(itemName);
    if (roomHasItem(itemName)) {
      return "From the " + this.currentRoom.getName() + " you examine the "
        + item.getName() + ": " + item.getDescription() + "\n";
    }
    return "From your inventory, you examine the "
      + item.getName() + ": " + item.getDescription() + "\n";
  }

  /**
   * Helper method. Examines a fixture in the room.
   *
   * @param fixtureName : fixture we are examining
   * @return description of the fixture
   */
  private String examineFixture(String fixtureName) {
    Fixture fixture = this.gameData.getFixture(fixtureName);
    return "From the " + this.currentRoom.getName() + " you examine the "
      + fixture.getName() + ": " + fixture.getDescription() + "\n";
  }

  /**
   * Helper method. Examines a monster in the room.
   *
   * @return description of the monster
   *          description changes based on whether monster is active or not
   */
  private String examineMonster() {
    Monster monster = getMonsterInRoom();
    if (!monster.isActive()) {
      return "From the " + this.currentRoom.getName() + " you examine the "
        + monster.getName() + ": " + monster.getDescription() + "\n";
    }
    return "From the " + this.currentRoom.getName() + " you examine the "
      + monster.getName() + ": " + monster.getActiveDescription() + "\n";
  }

  /**
   * Helper method. Examines a puzzle in the room.
   *
   * @return description of the puzzle
   *          description changes based on whether puzzle is active or not
   *
   */
  private String examinePuzzle() {
    Puzzle puzzle = getPuzzleInRoom();
    if (!puzzle.isActive()) {
      return "From the " + this.currentRoom.getName() + " you examine the "
        + puzzle.getName() + ": " + puzzle.getDescription() + "\n";
    }

    return "From the " + this.currentRoom.getName() + " you examine the "
      + puzzle.getName() + ": " + puzzle.getActiveDescription() + "\n";
  }

  /**
   * Checks if the .json game file has save data.
   *
   * @param gameFile The .json game file to check.
   * @return true if the game has save data, false otherwise.
   */
  private boolean hasSaveData(String gameFile) {
    // The saved data files for the .json game file
    String gameDataFile = "src/data/savegamedata_" + gameFile;
    String roomDataFile = "src/data/saveroomdata_" + gameFile;
    String playerDataFile = "src/data/saveplayerdata_" + gameFile;

    return new File(gameDataFile).isFile()
      && new File(roomDataFile).isFile()
      && new File(playerDataFile).isFile();
  }

  /**
   * Helper method. Checks if player has a specific item in their inventory.
   *
   * @param itemName The name of the item to check.
   * @return true if the player has the item in their inventory, false otherwise.
   */
  private boolean playerHasItem(String itemName) {
    Item item = this.gameData.getItem(itemName);
    Inventory playerInventory = this.player.getInventory();
    return playerInventory.hasItem(item);
  }

  /**
   * Helper method. Checks if room has a specific item.
   *
   * @param itemName item to check in the room
   * @return true if the room the item, false otherwise.
   */
  private boolean roomHasItem(String itemName) {
    List<String> roomsItemNames = getItemList(this.currentRoom.getItemNames());
    return roomsItemNames.contains(itemName);
  }

  /**
   * Helper method. Checks if the room has a specific fixture.
   *
   * @param fixtureName fixture to check in the room
   * @return true if the room has the fixture, false otherwise.
   */
  private boolean roomHasFixture(String fixtureName) {
    List<String> roomsFixtureNames = getItemList(this.currentRoom.getFixtureNames());
    return roomsFixtureNames.contains(fixtureName);
  }

  /**
   * Helper method. Checks if the current room has an active monster.
   *
   * @return true if the current room has an active monster, false otherwise.
   */
  private boolean roomHasActiveMonster() {
    if (this.currentRoom.getMonsterName() != null) {
      Monster monster = gameData.getMonster(currentRoom.getMonsterName());
      return monster.isActive();
    }
    return false;
  }

  /**
   * Helper method. Handles the event of a monster attacking the player.
   *
   * @param output output string to attach to monster attack message.
   * @return new string with the attack message
   */
  private String monsterAttacks(String output) {
    Monster monster = getMonsterInRoom();
    player.decreaseHealth(monster.getDamage());
    output = output.concat(monster.getName() + " " + monster.getAttackMessage() + "\n");
    output = output.concat("You took " + monster.getDamage() + " damage!\n");

    return output;
  }

  /**
   * Helper method. Check if the current room has an active puzzle.
   *
   * @return true if the current room has an active puzzle, false otherwise.
   */
  private boolean roomHasActivePuzzle() {
    if (currentRoom.getPuzzleName() != null) {
      Puzzle puzzle = gameData.getPuzzle(currentRoom.getPuzzleName());
      return puzzle.isActive();
    }
    return false;
  }

  /**
   * Helper method. Updates the player inventory when restoring the game.
   */
  private void updatePlayerInventory() {
    Inventory oldInventory = player.getInventory();
    Inventory newInventory = new Inventory();

    for (Item item : oldInventory.getItems()) {
      Item updatedItem = gameData.getItem(item.getName());
      newInventory.addItem(updatedItem);
    }

    player.setInventory(newInventory);
  }
}