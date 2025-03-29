package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The {@code GameModel} class implements the game logic for the adventure game.
 * It handles player movement, inventory management and interaction with game elements.
 * It also manages saving and loading adventure games.
 */
public class GameModel implements IGameModel {
  // Constant
  private static final String BASE_SAVE_PATH = "src/data/";
  private static final String ITEM_DELIMITER = ", ";
  // Game Data Fields
  private final String jsonFile;
  private final ObjectMapper objectMapper;
  private GameInfo gameInfo;
  // Game State Fields
  private final GameData gameData;
  private Room currentRoom;
  private Player player;

  /**
   * Constructor for initializing the GameModel class.
   * Loads the game data from the given JSON file and initializes the adventure game.
   *
   * @param jsonFile : the JSON file with data.
   * @throws IOException handles file not found error.
   */
  public GameModel(String jsonFile) throws IOException {
    if (jsonFile == null) {
      throw new IOException("jsonFile cannot be found.");
    }

    this.jsonFile = jsonFile;
    this.objectMapper = new ObjectMapper();
    this.gameInfo = this.objectMapper.readValue(new File(jsonFile), GameInfo.class);
    this.gameData = new GameData(this.gameInfo);

    // Set the starting conditions for the game
    this.currentRoom = gameData.getRoom("1");
    this.player = new Player();
  }

  /**
   * Moves the player in a given direction if the direction is valid.
   * Handles special cases such as monsters or puzzles blocking paths.
   *
   * @param direction : direction player wants to move to
   * @return a description describing the state of the game after the move
   */
  public String move(String direction) {
    StringBuilder output = new StringBuilder();
    String nextRoom = this.currentRoom.getPath(direction);
    int nextRoomNumber = Integer.parseInt(nextRoom);

    if (nextRoomNumber == 0) {
      // Invalid direction - no path
      output.append("<<You cannot go in that direction>>\n\n");
    } else if (nextRoomNumber < 0) {
      // path blocked by monster or puzzle
      if (roomHasActiveMonster()) {
        output.append(getMonsterInRoom().getActiveDescription()).append("\n");
        return monsterAttacks(output.toString());
      } else if (roomHasActivePuzzle()) {
        return output.append(getPuzzleInRoom().getActiveDescription()).append("\n").toString();
      } else {
        // Convert negative room to positive once obstacle is defeated to allow passage
        this.currentRoom = gameData.getRoom(String.valueOf(Math.abs(nextRoomNumber)));
      }
    } else {
      // Valid path to a new room
      this.currentRoom = gameData.getRoom(nextRoom);
    }

    // Create the description for the new room
    return buildRoomDescription();
  }

  /**
   * Helper method. Builds the description of the current room. It includes any active obstacle,
   * room description, and items available.
   *
   * @return a string describing the room.
   */
  private String buildRoomDescription() {
    String result = "You are in the " + this.currentRoom.getName() + "\n"
        + getCurrentRoomDescription();

    if (roomHasActiveMonster()) {
      result = monsterAttacks(result);
    }

    return result + "Items you see here: " + this.currentRoom.getItemNames() + "\n";
  }

  /**
   * Helper method. Gets the description for the room, the description of the room
   * changes based on whether there is a monster, puzzle or neither.
   *
   * @return description of the current room.
   */
  private String getCurrentRoomDescription() {
    StringBuilder output = new StringBuilder();
    if (roomHasActiveMonster()) {
      output.append(getMonsterInRoom().getActiveDescription());
    } else if (roomHasActivePuzzle()) {
      output.append(getPuzzleInRoom().getActiveDescription());
    } else {
      output.append(this.currentRoom.getDescription());
    }
    return output.append("\n").toString();
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
   * Helper method. Gets the puzzle in the current room.
   *
   * @return the puzzle object in the current room
   */
  private Puzzle getPuzzleInRoom() {
    String puzzleName = this.currentRoom.getPuzzleName();
    return this.gameData.getPuzzle(puzzleName);
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
   * Provides the room descriptions and the contents of the room.
   * If there is a monster in the room, they will deal damage to the player.
   *
   * @return a String describing what the player sees and whats happening in the room.
   */
  @Override
  public String look() {
    String result = "You are standing in the " + this.currentRoom.getName() + "\n"
        + getCurrentRoomDescription();

    if (roomHasActiveMonster()) {
      return monsterAttacks(result);
    }
    if (this.player.getHealth() > 0) {
      result += "Items you see here: " + this.currentRoom.getItemNames() + "\n";
    }

    return result + this.player.getHealthStatus();
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
    StringBuilder output = new StringBuilder();

    // Check if item is in player's inventory
    if (!playerHasItem(itemName)) {
      output.append(itemName).append(" not found in inventory.\n");
      return handleMonsterAttack(output.toString());
    }

    Item item = this.gameData.getItem(itemName);

    // Check if item still have uses remaining
    if (item.getUsesRemaining() == 0) {
      output.append("Oh no! ").append(item.getName())
        .append(" is either empty or cannot be used again!");
      return handleMonsterAttack(output.toString());
    }

    // Item is used on the player
    if (itemIsUsedOnPlayer(itemName)) {
      item.reduceUse();
      output.append(item.getWhenUsedDescription()).append("\n");
      return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
    }
    // Item is being used on a monster
    if (this.currentRoom.getMonsterName() != null) {
      output.append(useItemOnMonster(item));
    }
    // Item is being used on a puzzle
    else if (this.currentRoom.getPuzzleName() != null) {
      Puzzle puzzle = getPuzzleInRoom();
      output.append(useItemOnPuzzle(item));
    } else {
      output.append("Using ").append(item.getName()).append( "did nothing.\n");
    }
    return output.toString();
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
   * Helper method. Attempt to use an item on a monster.
   * Monster is deactivated if item matches monster solution, otherwise monster attacks.
   *
   * @param item : the item being used
   * @return a description of what happens after item is used
   */
  private String useItemOnMonster(Item item) {
    StringBuilder output = new StringBuilder();
    Monster monster = getMonsterInRoom();

    // Check if monster is active and if the item matches the monster's solution
    if (monster.isActive() && monster.getSolution().equalsIgnoreCase(item.getName())) {
      item.reduceUse();
      monster.deactivate();
      this.player.increaseScore(monster.getValue());
      output.append("SUCCESS! ").append(item.getWhenUsedDescription()).append("\n");
    } else {
      output.append("Using ").append(item.getName()).append(" did nothing.\n");
      if (monster.isActive()) {
        return monsterAttacks(output.toString());
      }
    }
    return output.toString();
  }

  /**
   * Helper method. Attempt to use an item on a puzzle.
   *
   * @param item : the item being used
   * @return a description of what happens after item is used
   */
  private String useItemOnPuzzle(Item item) {
    StringBuilder output = new StringBuilder();
    Puzzle puzzle = getPuzzleInRoom();

    // Check if puzzle is active and if the item matches the puzzle's solution
    if (puzzle.isActive() && puzzle.getSolution().equalsIgnoreCase(item.getName())) {
      item.reduceUse();
      puzzle.deactivate();
      this.player.increaseScore(puzzle.getValue());
      output.append("SUCCESS! ").append(item.getWhenUsedDescription()).append("\n");
    } else {
      output.append("Using ").append(item.getName()).append(" did nothing.\n");
    }
    return output.toString();
  }

  /**
   * Helper method. Attempt to use an item on a player.
   * @param itemName : the item being used
   * @return a description of what happens after item is used
   */
  private boolean itemIsUsedOnPlayer(String itemName) {
    if (currentRoom.getMonsterName() != null) {
      Monster monster = getMonsterInRoom();
      return !monster.getSolution().equalsIgnoreCase(itemName);
    } else if (currentRoom.getPuzzleName() != null) {
      Puzzle puzzle = getPuzzleInRoom();
      return !puzzle.getSolution().equalsIgnoreCase(itemName);
    }
    return true;
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
    StringBuilder output = new StringBuilder();
    // Items in a room are stored as: "ITEM1, ITEM2, ITEM3"
    // This will create a list of ["ITEM1", "ITEM2", "ITEM3"]
    List<String> roomsItemNames = new ArrayList<>(Arrays.asList(this.currentRoom.getItemNames()
                                                                  .split(ITEM_DELIMITER)));

    // Handle item not being in the room's list of items
    if (!roomsItemNames.contains(itemName.toUpperCase())) {
      if (itemIsObjectInRoom(itemName)) {
        output.append("You can't take the ").append(itemName).append("!\n");
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
      roomsItemNames.remove(item.getName().toUpperCase());

      // Update room items
      String updatedRoomItemNames = String.join(ITEM_DELIMITER, roomsItemNames);
      this.currentRoom.setItemNames(updatedRoomItemNames);

      output.append(item.getName()).append(" added to inventory.\n");
    }
    return handleMonsterAttack(output.toString());
  }

  /**
   * Determine if an fixture, monster of puzzle is in the room.
   *
   * @param objectName : name of the object they want to take
   * @return true if object is in room, false otherwise.
   */
  private boolean itemIsObjectInRoom(String objectName) {
    if (roomHasFixture(objectName)) {
      return true;
    } else if (currentRoom.getMonsterName() != null) {
      return getMonsterInRoom().getName().equalsIgnoreCase(objectName);
    } else {
      return getMonsterInRoom().getName().equalsIgnoreCase(objectName);
    }
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
    StringBuilder output = new StringBuilder();

    // Check if player has the item
    if (!playerHasItem(itemName)) {
      output.append("You don't have ").append(itemName).append(".\n");
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
   * Solves a puzzle with an answer.
   * If there is an active monster in the room, the monster attacks after the answer.
   *
   * @param answer The answer to the puzzle.
   * @return The sequence of events from solving a puzzle.
   */
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
    if (puzzle.isActive() && puzzle.getSolution().equals(answer)) {
      output.append("SUCCESS! You solved the puzzle with ").append(answer).append("\n");
      // Answer does not match the puzzle's solution or the puzzle is inactive
    } else {
      output.append("Your answer ").append(answer).append(" did nothing.\n");
    }

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
    String output;

    if (roomHasItem(objectName) || playerHasItem(objectName)) {
      output = examineItem(objectName);
    } else if (roomHasFixture(objectName)) {
      output = examineFixture(objectName);
    } else if (objectName.equalsIgnoreCase(this.currentRoom.getMonsterName())) {
      output = examineMonster();
    } else if (objectName.equalsIgnoreCase(this.currentRoom.getPuzzleName())) {
      output = examinePuzzle();
    } else {
      output = "There is no " + objectName + " to examine\n";
    }

    return roomHasActiveMonster() ? monsterAttacks(output) : output;
  }

  /**
   * Examines an item either in the room or the players inventory.
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
   * Examines a fixture in the room.
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
   * Examines a monster in the room.
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
   * Examines a puzzle in the room.
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
   * Saves the current game state to a file.
   *
   * @return a message indicating the file was saved successfully
   * @throws IOException if there is an error writing to the file.
   */
  public String saveGame() throws IOException {
    try {
      String gameFile = Paths.get(this.jsonFile).getFileName().toString();
      this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(
          new File("src/data/savegamedata" + gameFile), this.gameInfo);
      this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(
          new File("src/data/saveroomdata" + gameFile), this.currentRoom);
      this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(
          new File("src/data/saveplayerdata" + gameFile), this.player);
      return "Game saved successfully!\n";
    } catch (IOException e) {
      return "Game failed to save\n";
    }
  }

  /**
   * Restore the previously saved game state from the files.
   * Loads the game information, current room and player state.
   *
   * @return a message indicating the file was loaded successfully.
   * @throws IOException if there is an error reading the saved files.
   */
  public String restoreGame() throws IOException {
    try {
      String gameFile = Paths.get(this.jsonFile).getFileName().toString();
      this.gameInfo = this.objectMapper.readValue(
          new File("src/data/savegamedata" + gameFile), GameInfo.class);
      this.currentRoom = this.objectMapper.readValue(
          new File("src/data/saveroomdata" + gameFile), Room.class);
      this.player = this.objectMapper.readValue(
          new File("src/data/saveplayerdata" + gameFile), Player.class);
      return "Loaded your previous save\n";
    } catch (IOException e) {
      return "No game file to load\n";
    }
  }

  /**
   * Gets the player object.
   *
   * @return the player object
   */
  public IPlayer getPlayer() {
    return this.player;
  }

  /**
   * Gets the ending message when the player quits or goes to sleep in the game.
   *
   * @return end game message
   */
  public String getEndingMessage() {
    String endingMessage = "";
    endingMessage = endingMessage.concat("Thank you for playing!\nYour score is "
      + String.valueOf(this.player.getScore())
      + "\nYour rank: "
      + this.player.getRank()
      + "\n");
    return endingMessage;
  }

  /**
   * Checks if player has a specific item in their inventory.
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
   * Checks if room has a specific item.
   *
   * @param itemName item to check in the room
   * @return true if the room the item, false otherwise.
   */
  private boolean roomHasItem(String itemName) {
    //Item item = gameData.getItem(itemName);
    List<String> roomsItemNames =
        new ArrayList<>(Arrays.asList(this.currentRoom.getItemNames().split(", ")));
    return roomsItemNames.contains(itemName);
  }

  /**
   * Checks if the room has a specific fixture.
   *
   * @param fixtureName fixture to check in the room
   * @return true if the room has the fixture, false otherwise.
   */
  private boolean roomHasFixture(String fixtureName) {
    List<String> roomsFixtureNames =
        new ArrayList<>(Arrays.asList(this.currentRoom.getFixtureNames().split(", ")));
    return roomsFixtureNames.contains(fixtureName.toUpperCase());
  }

  /**
   * Checks if the current room has an active monster.
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
   * Handles the event of a monster attacking the player.
   *
   * @param output output string to attach to monster attack message.
   * @return new string with the attack message
   */
  private String monsterAttacks(String output) {
    Monster monster = getMonsterInRoom();
    player.decreaseHealth(monster.getDamage());
    // check player health
    output = output.concat(monster.getName() + " " + monster.getAttackMessage() + "\n");
    output = output.concat("You took " + monster.getDamage() + " damage!\n");

    return output;
  }

  /**
   * Check if the current room has an active puzzle.
   *
   * @return true if the current room has an active puzzle, false otherwise.
   */
  private boolean roomHasActivePuzzle() {
    if (currentRoom.getPuzzleName() != null) {
      String puzzleName = currentRoom.getPuzzleName();
      Puzzle puzzle = gameData.getPuzzle(puzzleName);
      return puzzle.isActive();
    }
    return false;
  }
}
