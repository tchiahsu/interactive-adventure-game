package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModel implements IGameModel {

  private final String jsonFile;
  private final ObjectMapper objectMapper;
  private GameInfo gameInfo;
  private final GameData gameData;

  private Room currentRoom;
  private Player player;

  /**
   * Constructor for initialising the GameModel class.
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

    this.currentRoom = gameData.getRoom("1");
    this.player = new Player();
  }

  /**
   * Moves the player in a given valid direction.
   * @param direction : the direction the player wants to move
   * @return String describing the result of the move
   */
  public String move(String direction) {
    StringBuilder output = new StringBuilder();
    String nextRoom = this.currentRoom.getPath(direction);
    int nextRoomNumber = Integer.parseInt(nextRoom);

    if (nextRoomNumber == 0) {
      output.append("<<You cannot go in that direction>>\n\n");
    } else if (nextRoomNumber < 0) {
      // Check if monster or puzzle is blocking the path
      if (roomHasActiveMonster()) {
        output.append(getMonsterInRoom().getActiveDescription()).append("\n");
        return monsterAttacks(output.toString());
      } else if (roomHasActivePuzzle()) {
        return output.append(getPuzzleInRoom().getActiveDescription()).append("\n").toString();
      } else {
        this.currentRoom = gameData.getRoom(String.valueOf(Math.abs(nextRoomNumber)));
      }
    } else {
      this.currentRoom = gameData.getRoom(nextRoom);
    }

    output.append("You are in the ").append(this.currentRoom.getName()).append("\n");
    output.append(getCurrentRoomDescription());
    String result = output.toString();

    if (roomHasActiveMonster()) {
      result = monsterAttacks(result);
    }

    return result + "Items you see here: " + this.currentRoom.getItemNames() + "\n";
  }

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

  private Monster getMonsterInRoom() {
    String monsterName = this.currentRoom.getMonsterName();
    return this.gameData.getMonster(monsterName);
  }

  private Puzzle getPuzzleInRoom() {
    String puzzleName = this.currentRoom.getPuzzleName();
    return this.gameData.getPuzzle(puzzleName);
  }

  @Override
  public String checkInventory() {
    return this.player.getInventory().toString() + "\n";
  }

  @Override
  public String look() {
    String result = "You are standing in the " + this.currentRoom.getName() + "\n" +
      getCurrentRoomDescription();

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
   * @param itemName The name of the item.
   * @return The sequence of events from using an item.
   */
  @Override
  public String useItem(String itemName) {
    StringBuilder output = new StringBuilder();

    // Handle player not having item in their inventory
    if (!playerHasItem(itemName)) {
      output.append(itemName).append(" not found in inventory.\n");
      // Handle action with an active monster in the room
      return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
    }

    Item item = this.gameData.getItem(itemName);

    // Handle using an item with no uses remaining
    if (item.getUsesRemaining() == 0) {
      output.append("Oh no! ").append(item.getName())
        .append(" is either empty or cannot be used again!");
      // Handle action with an active monster in the room
      return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
    }

    // Item is used on the player
    if (itemIsUsedOnPlayer(itemName)) {
      item.reduceUse();
      output.append(item.getWhenUsedDescription()).append("\n");
      return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
    }
    // Item is being used on a monster
    if (this.currentRoom.getMonsterName() != null) {
      Monster monster = getMonsterInRoom();
      // Item is monster's solution
      if (monster.isActive() && monster.getSolution().equalsIgnoreCase(item.getName())) {
        item.reduceUse();
        monster.deactivate();
        this.player.increaseScore(monster.getValue());
        output.append("SUCCESS! ").append(item.getWhenUsedDescription()).append("\n");
        // Item is not monster's solution or the monster is inactive
      } else {
        output.append("Using ").append(item.getName()).append(" did nothing.\n");
        if (monster.isActive()) {
          return monsterAttacks(output.toString());
        }
      }
    }
    // Item is being used on a puzzle
    else if (this.currentRoom.getPuzzleName() != null) {
      Puzzle puzzle = getPuzzleInRoom();
      // Item is puzzle's solution
      if (puzzle.isActive() && puzzle.getSolution().equalsIgnoreCase(item.getName())) {
        item.reduceUse();
        puzzle.deactivate();
        this.player.increaseScore(puzzle.getValue());
        output.append("SUCCESS! ").append(item.getWhenUsedDescription()).append("\n");
        // Item is not puzzle's solution or the puzzle is inactive
      } else {
        output.append("Using ").append(item.getName()).append(" did nothing.\n");
      }
    }
    return output.toString();
  }

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
   * Takes an item from a room into the player's inventory.
   *
   * @param itemName The name of the item in the room.
   * @return The sequence of events from taking an item from a room.
   */
  @Override
  public String takeItem(String itemName) {
    StringBuilder output = new StringBuilder();
    // Items in a room are stored as: "ITEM1, ITEM2, ITEM3"
    // This will create a list of ["ITEM1", "ITEM2", "ITEM3"]
    List<String> roomsItemNames = new ArrayList<>(Arrays.asList(this.currentRoom.getItemNames()
                                                                  .split(", ")));

    // Handle item not being in the room's list of items
    if (!roomsItemNames.contains(itemName.toUpperCase())) {
      if (itemIsObjectInRoom(itemName)) {
        output.append("You can't take the ").append(itemName).append("!\n");
      } else {
        output.append(itemName).append(" not found in ").append(this.currentRoom.getName())
                .append("\n");
        // Handle action with an active monster in the room
        return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
      }
    }

    // Item is in the room's list of items
    // Check if player can hold the item
    Item item = this.gameData.getItem(itemName);
    Inventory playerInventory = this.player.getInventory();
    int newWeight = playerInventory.getCurrentCapacity() + item.getWeight();
    // Player cannot hold the item
    if (newWeight > playerInventory.getMaxCapacity()) {
      output.append("Your inventory is too full!\n");
    }
    // Player can hold the item
    // Add to their inventory
    else {
      // Player cannot hold the item
      playerInventory.addItem(item);
      playerInventory.setCurrentCapacity(newWeight);
      this.player.increaseScore(item.getValue());
      roomsItemNames.remove(roomsItemNames.indexOf(item.getName()));
      output.append(item.getName()).append(" added to inventory.\n");
    }

    // Update the items in the room
    String updatedRoomItemNames = String.join(", ", roomsItemNames);
    this.currentRoom.setItemNames(updatedRoomItemNames);
    // Handle action with an active monster in the room
    return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
  }

  private boolean itemIsObjectInRoom(String itemName) {
    if (roomHasFixture(itemName)) {
      return true;
    } else if (currentRoom.getMonsterName() != null) {
      return getMonsterInRoom().getName().equalsIgnoreCase(itemName);
    } else {
      return getMonsterInRoom().getName().equalsIgnoreCase(itemName);
    }
  }

  /**
   * Drops an item from the player's inventory into a room.
   *
   * @param itemName The item to drop.
   * @return The sequence of events from dropping an item.
   */
  @Override
  public String dropItem(String itemName) {
    StringBuilder output = new StringBuilder();
    // Handle player not having item in their inventory
    if (!playerHasItem(itemName)) {
      output.append("You don't have ").append(itemName).append(".\n");
      // Handle action with an active monster in the room
      return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
    }

    // Player has the item in their inventory
    // Remove the item from their inventory
    Item item = this.gameData.getItem(itemName);
    Inventory playerInventory = this.player.getInventory();
    playerInventory.removeItem(item);
    playerInventory.setCurrentCapacity(playerInventory.getCurrentCapacity() - item.getWeight());
    this.player.decreaseScore(item.getValue());

    // Add the item to the room's list of items
    String roomItemNames = this.currentRoom.getItemNames();
    String updatedRoomItemNames = roomItemNames.isEmpty() ? item.getName() : roomItemNames.concat(", " + item.getName());
    this.currentRoom.setItemNames(updatedRoomItemNames);
    output.append(item.getName()).append(" removed from inventory.\n");

    // Handle action with an active monster in the room
    return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
  }

  /**
   * Solves a puzzle with an answer.
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
      return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
    }

    Puzzle puzzle = getPuzzleInRoom();

    // Answer matches the puzzle's solution
    if (puzzle.isActive() && puzzle.getSolution().equals(answer)) {
      output.append("SUCCESS! You solved the puzzle with ").append(answer).append("\n");
      // Answer does not match the puzzle's solution or the puzzle is inactive
    } else {
      output.append("Your answer ").append(answer).append(" did nothing.\n");
    }

    // Handle action with an active monster in the room
    return roomHasActiveMonster() ? monsterAttacks(output.toString()) : output.toString();
  }

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

  private String examineItem(String itemName) {
    Item item = this.gameData.getItem(itemName);
    if (roomHasItem(itemName)) {
      return "From the " + this.currentRoom.getName() + " you examine the "
              + item.getName() + ": " + item.getDescription() + "\n";
    }
    return "From your inventory, you examine the "
              + item.getName() + ": " + item.getDescription() + "\n";
  }

  private String examineFixture(String fixtureName) {
    Fixture fixture = this.gameData.getFixture(fixtureName);
    return "From the " + this.currentRoom.getName() + " you examine the "
            + fixture.getName() + ": " + fixture.getDescription() + "\n";
  }

  private String examineMonster() {
    Monster monster = getMonsterInRoom();
    if (!monster.isActive()) {
      return "From the " + this.currentRoom.getName() + " you examine the "
                + monster.getName() + ": " + monster.getDescription() + "\n";
    }
    return "From the " + this.currentRoom.getName() + " you examine the "
            + monster.getName() + ": " + monster.getActiveDescription() + "\n";
  }

  private String examinePuzzle() {
    Puzzle puzzle = getPuzzleInRoom();
    if (!puzzle.isActive()) {
      return "From the " + this.currentRoom.getName() + " you examine the "
                + puzzle.getName() + ": " + puzzle.getDescription() + "\n";
    }

    return "From the " + this.currentRoom.getName() + " you examine the "
            + puzzle.getName() + ": " + puzzle.getActiveDescription() + "\n";
  }

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

  public IPlayer getPlayer() {
    return this.player;
  }

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
   * Checks if player has an item in their inventory.
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
   * Checks if room has an item.
   * @param itemName item to check in the Room
   * @return true if the room the item, false otherwise.
   */
  private boolean roomHasItem(String itemName) {
    //Item item = gameData.getItem(itemName);
    List<String> roomsItemNames =
        new ArrayList<>(Arrays.asList(this.currentRoom.getItemNames().split(", ")));
    return roomsItemNames.contains(itemName);
  }

  /**
   * Checks if room has an item.
   * @param fixtureName item to check in the Room
   * @return true if the room the item, false otherwise.
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
   * @param output The sequence of events to add the description of monster attacking the player to.
   * @return The sequence of events with the monster attacking the player.
   */
  private String monsterAttacks(String output) {
    Monster monster = getMonsterInRoom();
    player.decreaseHealth(monster.getDamage());
    // check player health
    output = output.concat(monster.getName() + " " + monster.getAttackMessage() + "\n");
    output = output.concat("You took " + monster.getDamage() + " damage!\n");

    return output;
  }

  private boolean roomHasActivePuzzle() {
    if (currentRoom.getPuzzleName() != null) {
      String puzzleName = currentRoom.getPuzzleName();
      Puzzle puzzle = gameData.getPuzzle(puzzleName);
      return puzzle.isActive();
    }
    return false;
  }
}
