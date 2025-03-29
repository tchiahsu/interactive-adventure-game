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
   * @param jsonFile the JSON file with data.
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

    currentRoom = gameData.getRoom("1");
    player = new Player();
  }

  /**
   * Moves the player in a given valid direction.
   * @param direction the direction player wants to move to.
   */
  public String move(String direction) {
    String output = "";
    String nextRoom = currentRoom.getPath(direction);
    int nextRoomNumber = Integer.parseInt(nextRoom);

    if (nextRoomNumber == 0) {
      output = output.concat("<<You cannot go in that direction>> \n");
    } else if (nextRoomNumber < 0) {
      // Check if monster or puzzle is blocking the path
      if (roomHasActiveMonster()) {
        output = output.concat(getMonsterInRoom().getActiveDescription() + "\n");
        return output = monsterAttacks(output);
      } else if (roomHasActivePuzzle()) {
        return output = output.concat(getPuzzleInRoom().getActiveDescription() + "\n");
      } else {
        currentRoom = gameData.getRoom(Math.abs(nextRoomNumber) + "");
      }
    } else {
      currentRoom = gameData.getRoom(nextRoom);
    }

    output = output.concat("You are in the " + currentRoom.getName() + "\n");
    output = output.concat(getCurrentRoomDescription());
    output = roomHasActiveMonster() ? monsterAttacks(output) : output;
    output = output.concat("Items you see here: " + currentRoom.getItemNames() + "\n");
    return output;
  }

  private String getCurrentRoomDescription() {
    String output = "";
    if (roomHasActiveMonster()) {
      output = output.concat(getMonsterInRoom().getActiveDescription());
    } else if (roomHasActivePuzzle()) {
      output = output.concat(getPuzzleInRoom().getActiveDescription());
    } else {
      output = output.concat(currentRoom.getDescription());
    }

    return output.concat("\n");
  }

  private Monster getMonsterInRoom() {
    String monsterName = currentRoom.getMonsterName();
    Monster monster = gameData.getMonster(monsterName);
    return monster;
  }

  private Puzzle getPuzzleInRoom() {
    String puzzleName = currentRoom.getPuzzleName();
    Puzzle puzzle = gameData.getPuzzle(puzzleName);
    return puzzle;
  }

  @Override
  public String checkInventory() {
    return player.getInventory().toString() + "\n";
  }

  @Override
  public String look() {
    String output = "";
    output = output.concat("You are standing in the " + currentRoom.getName() + "\n");
    output = output.concat(getCurrentRoomDescription());
    output = roomHasActiveMonster() ? monsterAttacks(output) : output;
    if (this.player.getHealth() > 0) {
      output = output.concat("Items you see here: " + this.currentRoom.getItemNames() + "\n");
    }
    return output.concat(this.player.getHealthStatus());
  }

  /**
   * Uses an item on an obstacle in the room.
   *
   * @param itemName The name of the item.
   * @return The sequence of events from using an item.
   */
  @Override
  public String useItem(String itemName) {
    String output = "";

    // Handle player not having item in their inventory
    if (!playerHasItem(itemName)) {
      output = output.concat(itemName + " not found in inventory.\n");
      // Handle action with an active monster in the room
      output = roomHasActiveMonster() ? monsterAttacks(output) : output;
      return output;
    }

    Item item = gameData.getItem(itemName);

    // Handle using an item with no uses remaining
    if (item.getUsesRemaining() == 0) {
      output = output.concat("Oh no! " + item.getName() + " is either empty or cannot be used again!");
      // Handle action with an active monster in the room
      output = roomHasActiveMonster() ? monsterAttacks(output) : output;
      return output;
    }

    // Item is being used on a monster
    if (currentRoom.getMonsterName() != null) {
      Monster monster = getMonsterInRoom();
      // Item is monster's solution
      if (monster.isActive() && monster.getSolution().equalsIgnoreCase(item.getName())) {
        item.reduceUse();
        monster.deactivate();
        player.increaseScore(monster.getValue());
        output = output.concat("SUCCESS! " + item.getWhenUsedDescription() + "\n");
        // Item is not monster's solution or the monster is inactive
      } else {
        output = output.concat("Using " + item.getName() + " did nothing.\n");
        output = monster.isActive() ? monsterAttacks(output) : output;
      }
    }
    // Item is being used on a puzzle
    else if (currentRoom.getPuzzleName() != null) {
      Puzzle puzzle = getPuzzleInRoom();
      // Item is puzzle's solution
      if (puzzle.isActive() && puzzle.getSolution().equalsIgnoreCase(item.getName())) {
        item.reduceUse();
        puzzle.deactivate();
        player.increaseScore(puzzle.getValue());
        output = output.concat("SUCCESS! " + item.getWhenUsedDescription() + "\n");
        // Item is not puzzle's solution or the puzzle is inactive
      } else {
        output = output.concat("Using " + item.getName() + " did nothing.\n");
      }
    }

    return output;
  }

  /**
   * Takes an item from a room into the player's inventory.
   *
   * @param itemName The name of the item in the room.
   * @return The sequence of events from taking an item from a room.
   */
  @Override
  public String takeItem(String itemName) {
    String output = "";
    // Items in a room are stored as: "ITEM1, ITEM2, ITEM3"
    // This will create a list of ["ITEM1", "ITEM2", "ITEM3"]
    List<String> roomsItemNames = new ArrayList<>(Arrays.asList(currentRoom.getItemNames().split(", ")));

    // Handle item not being in the room's list of items
    if (!roomsItemNames.contains(itemName.toUpperCase())) {
      output = output.concat(itemName + " not found in " + currentRoom.getName() + "\n");
      // Handle action with an active monster in the room
      output = roomHasActiveMonster() ? monsterAttacks(output) : output;
      return output;
    }

    // Item is in the room's list of items
    // Check if player can hold the item
    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    int newWeight = playerInventory.getCurrentCapacity() + item.getWeight();
    // Player cannot hold the item
    if (newWeight > playerInventory.getMaxCapacity()) {
      output = output.concat("Your inventory is too full!\n");
    }
    // Player can hold the item
    // Add to their inventory
    else {
      playerInventory.addItem(item);
      playerInventory.setCurrentCapacity(newWeight);
      player.increaseScore(item.getValue());
      roomsItemNames.remove(roomsItemNames.indexOf(item.getName()));
      output = output.concat(item.getName() + " added to inventory.\n");
    }

    // Update the items in the room
    String updatedRoomItemNames = String.join(", ", roomsItemNames);
    currentRoom.setItemNames(updatedRoomItemNames);
    // Handle action with an active monster in the room
    output = roomHasActiveMonster() ? monsterAttacks(output) : output;
    return output;
  }

  /**
   * Drops an item from the player's inventory into a room.
   *
   * @param itemName The item to drop.
   * @return The sequence of events from dropping an item.
   */
  @Override
  public String dropItem(String itemName) {
    String output = "";
    // Handle player not having item in their inventory
    if (!playerHasItem(itemName)) {
      output = output.concat("You don't have " + itemName + ".\n");
      // Handle action with an active monster in the room
      output = roomHasActiveMonster() ? monsterAttacks(output) : output;
      return output;
    }

    // Player has the item in their inventory
    // Remove the item from their inventory
    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    playerInventory.removeItem(item);
    playerInventory.setCurrentCapacity(playerInventory.getCurrentCapacity() - item.getWeight());
    player.decreaseScore(item.getValue());

    // Add the item to the room's list of items
    String roomItemNames = currentRoom.getItemNames();
    String updatedRoomItemNames = roomItemNames.isEmpty() ? item.getName() : roomItemNames.concat(", " + item.getName());
    currentRoom.setItemNames(updatedRoomItemNames);
    output = output.concat(item.getName() + " removed from inventory.\n");

    // Handle action with an active monster in the room
    output = roomHasActiveMonster() ? monsterAttacks(output) : output;
    return output;
  }

  /**
   * Solves a puzzle with an answer.
   *
   * @param answer The answer to the puzzle.
   * @return The sequence of events from solving a puzzle.
   */
  public String answer(String answer) {
    String output = "";

    // Handle answering with no puzzle in the room
    if (currentRoom.getPuzzleName() == null) {
      output = output.concat("You answered, but no one heard you.\n");
      // Handle action with an active monster in the room
      output = roomHasActiveMonster() ? monsterAttacks(output) : output;
      return output;
    }

    Puzzle puzzle = getPuzzleInRoom();

    // Answer matches the puzzle's solution
    if (puzzle.isActive() && puzzle.getSolution().equals(answer)) {
      output = output.concat("SUCCESS! You solved the puzzle with " + answer + "\n");
      // Answer does not match the puzzle's solution or the puzzle is inactive
    } else {
      output = output.concat("Your answer " + answer + " did nothing.\n");
    }

    // Handle action with an active monster in the room
    output = roomHasActiveMonster() ? monsterAttacks(output) : output;
    return output;
  }

  @Override
  public String examine(String objectName) {
    String output = "";

    if (roomHasItem(objectName) || playerHasItem(objectName)) {
      output = examineItem(objectName);
    } else if (roomHasFixture(objectName)) {
      Fixture fixture = gameData.getFixture(objectName);
      output = output.concat("From the " + this.currentRoom.getName() + " you examine the "
              + fixture.getName() + ": " + fixture.getDescription() + "\n");
    } else if (currentRoom.getMonsterName() != null) {
      output = examineMonster();
    } else if (currentRoom.getPuzzleName() != null) {
      output = examinePuzzle();
    } else {
      output = "There is no " + objectName + " to examine\n";
    }

    output = roomHasActiveMonster() ? monsterAttacks(output) : output;
    return output;
  }

  private String examineItem(String itemName) {
    Item item = gameData.getItem(itemName);
    if (roomHasItem(itemName)) {
      return "From the " + this.currentRoom.getName() + " you examine the "
              + item.getName() + ": " + item.getDescription() + "\n";
    }
    return "From your inventory, you examine the "
              + item.getName() + ": " + item.getDescription() + "\n";
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
      String gameFile = Paths.get(jsonFile).getFileName().toString();
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/savegamedata" + gameFile), gameInfo);
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/saveroomdata" + gameFile), currentRoom);
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/saveplayerdata" + gameFile), player);
      return "Game saved successfully!\n";
    } catch (IOException e) {
      return "Game failed to save\n";
    }
  }

  public String restoreGame() throws IOException {
    try {
      String gameFile = Paths.get(jsonFile).getFileName().toString();
      this.gameInfo = objectMapper.readValue(new File("src/data/savegamedata" + gameFile), GameInfo.class);
      this.currentRoom = objectMapper.readValue(new File("src/data/saveroomdata" + gameFile), Room.class);
      this.player = objectMapper.readValue(new File("src/data/saveplayerdata" + gameFile), Player.class);
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
    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    if (playerInventory.hasItem(item)) {
      return true;
    }

    return false;
  }

  /**
   * Checks if room has an item.
   * @param itemName item to check in the Room
   * @return true if the room the item, false otherwise.
   */
  private boolean roomHasItem(String itemName) {
    //Item item = gameData.getItem(itemName);
    List<String> roomsItemNames = new ArrayList<>(Arrays.asList(currentRoom.getItemNames().split(", ")));
    if (roomsItemNames.contains(itemName)) {
      return true;
    }
    return false;
  }

  /**
   * Checks if room has an item.
   * @param fixtureName item to check in the Room
   * @return true if the room the item, false otherwise.
   */
  private boolean roomHasFixture(String fixtureName) {
    List<String> roomsFixtureNames = new ArrayList<>(Arrays.asList(currentRoom.getFixtureNames().split(", ")));
    if (roomsFixtureNames.contains(fixtureName.toUpperCase())) {
      return true;
    }
    return false;
  }

  /**
   * Checks if the current room has an active monster.
   *
   * @return true if the current room has an active monster, false otherwise.
   */
  private boolean roomHasActiveMonster() {
    boolean roomHasMonster = currentRoom.getMonsterName() != null;

    if (roomHasMonster) {
      String monsterName = currentRoom.getMonsterName();
      Monster monster = gameData.getMonster(monsterName);
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
    boolean roomHasPuzzle = currentRoom.getPuzzleName() != null;
    if (roomHasPuzzle) {
      String puzzleName = currentRoom.getPuzzleName();
      Puzzle puzzle = gameData.getPuzzle(puzzleName);
      return puzzle.isActive();
    }
    return false;
  }
}
