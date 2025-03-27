package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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
      throw new IOException("jsonFile cannot be null");
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
   */
  public void move(String direction) {
    String nextRoomId = currentRoom.getPath(direction);
    if (nextRoomId == null) {
      System.out.println("<<You can't go " + direction + ">>");
      return;
    }

    currentRoom = gameData.getRoom(nextRoomId);
    System.out.println("You Enter " + currentRoom.getName());
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
    // This will create a list of ["item1", "item2"]
    List<String> itemsNameList = Arrays.asList(currentRoom.getItemNames().split(", "));

    if(itemsNameList.contains(itemName)) {
      String removedItemName = itemsNameList.remove(itemsNameList.indexOf(itemName));
      String newItemNamesList = String.join(", ", itemsNameList);
      currentRoom.setItems(newItemNamesList);


      int newCapacity = player.getInventory().getCurrentCapacity() + gameData.getItem(removedItemName).getWeight();
      boolean checkAdd = player.getInventory().getMaxCapacity() >= newCapacity;
      if (checkAdd) {
        player.getInventory().addItem(gameData.getItem(removedItemName));
        player.increaseScore(gameData.getItem(removedItemName).getValue());
      }

    }
    //remove item from room - done
      //add to players inventory
        //check maxCapacity
      //increase Player score

  }

  @Override
  public void dropItem(String itemName) {


  }

  @Override
  public void examine(String itemName) {

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
    String monsterName = currentRoom.getMonsterName();
    Monster monster = gameData.getMonster(monsterName);
    player.decreaseHealth(monster.getDamage());
  }
}
