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

  }

  @Override
  public void takeItem(String itemName) {
    // Items in a room are stored as: "item1, item2, item3"
    // This will create a list of ["item1", "item2", "item3"]
    List<String> roomsItemNames = new ArrayList<>(Arrays.asList(currentRoom.getItemNames().split(", ")));
    if (!roomsItemNames.contains(itemName)) {
      throw new IllegalArgumentException(itemName + " not found in " + currentRoom.getName());
    }

    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    int newWeight = playerInventory.getCurrentCapacity() + item.getWeight();
    if (newWeight > playerInventory.getMaxCapacity()) {
      throw new IllegalArgumentException("Your bag is too full!");
    } else {
      playerInventory.addItem(item);
      playerInventory.setCurrentCapacity(newWeight);
      player.increaseScore(item.getValue());
      roomsItemNames.remove(roomsItemNames.indexOf(itemName));
    }

    String updatedRoomItemNames = String.join(", ", roomsItemNames);
    currentRoom.setItemNames(updatedRoomItemNames);
    return;
  }

  @Override
  public void dropItem(String itemName) {
    if (!playerHasItem(itemName)) {
      throw new IllegalArgumentException("You don't have " + itemName);
    }

    String roomItemNames = currentRoom.getItemNames();
    String updatedRoomItemNames = roomItemNames.concat(", " + itemName);
    currentRoom.setItemNames(updatedRoomItemNames);
    Item item = gameData.getItem(itemName);

    Inventory playerInventory = player.getInventory();
    playerInventory.removeItem(item);
    playerInventory.setCurrentCapacity(playerInventory.getCurrentCapacity() - item.getWeight());
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

  private void monsterAttacks() {
    boolean roomHasMonster = currentRoom.getMonsterName() != null;

    if (roomHasMonster) {
      String monsterName = currentRoom.getMonsterName();
      Monster monster = gameData.getMonster(monsterName);
      player.decreaseHealth(monster.getDamage());
      // check player health
      monster.getAttackMessage();
    }
  }

  public static void main(String[] args) {
    String test = "item1, item2, item3";
    List<String> itemsNameList = new ArrayList<>(Arrays.asList(test.split(", ")));
    itemsNameList.remove(1);

    String newString = String.join(", ", itemsNameList);
    System.out.println(newString);
  }

}
