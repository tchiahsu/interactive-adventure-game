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

  @Override
  public String useItem(String itemName) {
    String output = "";

    if (!playerHasItem(itemName)) {
      output = output.concat(itemName + " not found in inventory.\n");
      return monsterAttacks(output);
    }

    Item item = gameData.getItem(itemName);
    if (item.getUsesRemaining() == 0) {
      output = output.concat("Oh no! " + item.getName() + " is either empty or cannot be used again!");
      return monsterAttacks(output);
    }

    if (currentRoom.getMonsterName() != null) {
      Monster monster = gameData.getMonster(currentRoom.getMonsterName());
      if (monster.isActive() && monster.getSolution().equalsIgnoreCase(item.getName())) {
        item.reduceUse();
        monster.deactivate();
        player.increaseScore(monster.getValue());
        output = output.concat("SUCCESS! " + item.getWhenUsedDescription() + "\n");
      } else {
        output = output.concat("Using " + item.getName() + " did nothing.");
      }
    }
    else if (currentRoom.getPuzzleName() != null) {
      Puzzle puzzle = gameData.getPuzzle(currentRoom.getPuzzleName());
      if (puzzle.isActive() && puzzle.getSolution().equalsIgnoreCase(item.getName())) {
        item.reduceUse();
        puzzle.deactivate();
        player.increaseScore(puzzle.getValue());
        output = output.concat("SUCCESS! " + item.getWhenUsedDescription() + "\n");
      } else {
        output = output.concat("Using " + item.getName() + " did nothing.");
      }
    }

    return output;
  }

  @Override
  public String takeItem(String itemName) {
    String output = "";
    // Items in a room are stored as: "ITEM1, ITEM2, ITEM3"
    // This will create a list of ["ITEM1", "ITEM2", "ITEM3"]
    List<String> roomsItemNames = new ArrayList<>(Arrays.asList(currentRoom.getItemNames().split(", ")));
    if (!roomsItemNames.contains(itemName.toUpperCase())) {
      output = output.concat(itemName + " not found in " + currentRoom.getName() + "\n");
    }

    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    int newWeight = playerInventory.getCurrentCapacity() + item.getWeight();
    if (newWeight > playerInventory.getMaxCapacity()) {
      output = output.concat("Your inventory is too full!\n");
    }
    else {
      playerInventory.addItem(item);
      playerInventory.setCurrentCapacity(newWeight);
      player.increaseScore(item.getValue());
      roomsItemNames.remove(roomsItemNames.indexOf(itemName));
      output = output.concat(item.getName() + " added to inventory.\n");
    }

    String updatedRoomItemNames = String.join(", ", roomsItemNames);
    currentRoom.setItemNames(updatedRoomItemNames);
    return monsterAttacks(output);
  }

  @Override
  public String dropItem(String itemName) {
    String output = "";
    if (!playerHasItem(itemName)) {
      output = output.concat("You don't have " + itemName + ".\n");
      return monsterAttacks(output);
    }

    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    playerInventory.removeItem(item);
    playerInventory.setCurrentCapacity(playerInventory.getCurrentCapacity() - item.getWeight());

    String roomItemNames = currentRoom.getItemNames();
    String updatedRoomItemNames = roomItemNames.concat(", " + item.getName());
    currentRoom.setItemNames(updatedRoomItemNames);
    output.concat(itemName + " removed from inventory.\n");

    return monsterAttacks(output);
  }

  public String answer(String answer) {
    String output = "";
    if (currentRoom.getPuzzleName() == null) {
      output = output.concat("You answered, but no one heard you.\n");
      return output;
    }

    String puzzleName = currentRoom.getPuzzleName();
    Puzzle puzzle = gameData.getPuzzle(puzzleName);

    if (puzzle.isActive() && puzzle.getSolution().equals(answer)) {
      output = output.concat("SUCCESS! You solved the puzzle with " + answer);
    } else {
      output = output.concat("Your answer " + answer + " did nothing.");
    }

    return output;
  }

  @Override
  public void examine(String itemName) {
    // item could be item, fixture, puzzle, or monster
  }

  private boolean playerHasItem(String itemName) {
    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    if (playerInventory.hasItem(item)) {
      return true;
    }

    return false;
  }

  private String monsterAttacks(String output) {
    boolean roomHasMonster = currentRoom.getMonsterName() != null;

    if (roomHasMonster) {
      String monsterName = currentRoom.getMonsterName();
      Monster monster = gameData.getMonster(monsterName);
      if (monster.isActive()) {
        player.decreaseHealth(monster.getDamage());
        // check player health
        output = output.concat(monster.getAttackMessage() + "\n");
        output = output.concat("You took " + monster.getDamage() + " damage!\n");
      }
    }

    return output;
  }

  public static void main(String[] args) {
    String test = "item1, item2, item3";
    List<String> itemsNameList = new ArrayList<>(Arrays.asList(test.split(", ")));
    itemsNameList.remove(1);

    String newString = String.join(", ", itemsNameList);
    System.out.println(newString);
  }

}
