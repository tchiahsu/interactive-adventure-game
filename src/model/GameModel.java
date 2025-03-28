package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModel implements IGameModel {

  private ObjectMapper objectMapper;
  private GameInfo gameInfo;
  private GameData gameData;

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

    this.objectMapper = new ObjectMapper();
    this.gameInfo = this.objectMapper.readValue(new File(jsonFile), GameInfo.class);
    GameData gameData = createGameData(this.gameInfo);

    currentRoom = gameData.getRoom("1");
    player = new Player();
  }

  private GameData createGameData(GameInfo gameInfo) {
    return new GameData(gameInfo);
  }

  /**
   * Moves the player in a given valid direction.
   * @param direction the direction player wants to move to.
   */
  public void move(String direction) {
    String nextRoom = currentRoom.getPath(direction);
    if (nextRoom != null) {
      //if monster
      //display

      // last step: this.currentRoom = gameData.getRoom(nextRoom);
    }
    else{
      //throw error or print
      System.out.println("<<You cannot go in that direction>>");
    }

  }

  @Override
  public String checkInventory() {
    return player.getInventory().toString();
  }

  @Override
  public void look() {

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
      Monster monster = gameData.getMonster(currentRoom.getMonsterName());
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
      Puzzle puzzle = gameData.getPuzzle(currentRoom.getPuzzleName());
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
      roomsItemNames.remove(roomsItemNames.indexOf(itemName));
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

    // Add the item to the room's list of items
    String roomItemNames = currentRoom.getItemNames();
    String updatedRoomItemNames = roomItemNames.concat(", " + item.getName());
    currentRoom.setItemNames(updatedRoomItemNames);
    output.concat(itemName + " removed from inventory.\n");

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

    String puzzleName = currentRoom.getPuzzleName();
    Puzzle puzzle = gameData.getPuzzle(puzzleName);

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
  public void examine(String itemName) {
    // item could be item, fixture, puzzle, or monster
  }

  public String saveGame() throws IOException {
    try {
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/savegamedata.json"), gameInfo);
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/saveroomdata.json"), currentRoom);
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/saveplayerdata.json"), player);
      return "Game saved successfully!\n";
    } catch (IOException e) {
      return "Game failed to save\n";
    }
  }

  public String restoreGame() throws IOException {
    try {
      gameInfo = objectMapper.readValue(new File("src/data/savegamedata.json"), GameInfo.class);
      currentRoom = objectMapper.readValue(new File("src/data/saveroomdata.json"), Room.class);
      player = objectMapper.readValue(new File("src/data/saveplayerdata.json"), Player.class);
      return "Loaded your previous save\n";
    } catch (IOException e) {
      return "No game file to load\n";
    }
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
   * Checks if the current room has an active monster.
   *
   * @return true if the current room has an active monster, false otherwise.
   */
  private boolean roomHasActiveMonster() {
    boolean roomHasMonster = currentRoom.getMonsterName() != null;
    if (roomHasMonster) {
      String monsterName = currentRoom.getMonsterName();
      Monster monster = gameData.getMonster(monsterName);
      if (monster.isActive()) {
        return true;
      }
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
    String monsterName = currentRoom.getMonsterName();
    Monster monster = gameData.getMonster(monsterName);
    player.decreaseHealth(monster.getDamage());
    // check player health
    output = output.concat(monster.getAttackMessage() + "\n");
    output = output.concat("You took " + monster.getDamage() + " damage!\n");

    return output;
  }

  public static void main(String[] args) {

  }

}
