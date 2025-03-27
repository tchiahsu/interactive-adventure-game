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

//  @Override
//  public void goNorth() {
//  }
//
//  @Override
//  public void goSouth() {
//
//  }
//
//  @Override
//  public void goEast() {
//
//  }
//
//  @Override
//  public void goWest() {
//
//  }

  @Override
  public String checkInventory() {
    return player.getInventory().toString();
  }

  @Override
  public void look() {

  }

  @Override
  public String useItem(String itemName) {
    Item item = gameData.getItem(itemName);
    Inventory playerInventory = player.getInventory();
    if (!playerInventory.hasItem(item)) {
      throw new IllegalArgumentException("You do not have this item.");
    }

    if (currentRoom.getMonsterName() != null) {
      Monster monster = gameData.getMonster(currentRoom.getMonsterName());
      if (monster.isActive() && monster.getSolution().equals(item.getName())) {
        monster.deactivate();
        item.reduceUse(); // check uses remaining is above 0
      }
    }

    if (currentRoom.getPuzzleName() != null) {
      Puzzle puzzle = gameData.getPuzzle(currentRoom.getPuzzleName());
      if (puzzle.isActive() && puzzle.getSolution().equals(item.getName())) {
        puzzle.deactivate();
        item.reduceUse(); // check uses remaining is above 0
      }
    }

    return currentRoom.getDescription();
  }

  @Override
  public void takeItem() {
    // Items in a room are stored as: "item1, item2, item3"
    // This will create a list of ["item1", "item2", "item3]
    List<String> itemsNameList = Arrays.asList(currentRoom.getItems().split(", "));

  }

  @Override
  public void dropItem() {

  }

  @Override
  public void examine() {

  }
}
