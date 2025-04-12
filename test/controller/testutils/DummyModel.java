package controller.testutils;

import java.io.IOException;
import java.util.List;

import model.IGameModel;
import model.IPlayer;

public class DummyModel implements IGameModel {
  private final DummyPlayer player = new DummyPlayer();
  private String lastCommand = "";

  @Override
  public String move(String direction) {
    setCommand(direction.toUpperCase());
    return "Executed: Move";
  }

  @Override
  public String checkInventory() {
    return "Executed: Inventory";
  }

  @Override
  public String look() {
    setCommand("LOOK");
    return "Executed: Look";
  }

  @Override
  public String useItem(String itemName) {
    setCommand("USE " + itemName.toUpperCase());
    return "Executed: Use";
  }

  @Override
  public String takeItem(String itemName) {
    return "Executed: Take";
  }

  @Override
  public String dropItem(String itemName) {
    setCommand("DROP " + itemName.toUpperCase());
    return "Executed: Drop";
  }

  @Override
  public String examine(String object) {
    return "Executed: Examine";
  }

  @Override
  public String answer(String answer) {
    return "Executed: Answer";
  }

  @Override
  public String saveGame() throws IOException {
    return "Executed: Save";
  }

  @Override
  public String restoreGame() throws IOException {
    return "Executed: Restore";
  }

  @Override
  public IPlayer getPlayer() {
    return player;
  }

  @Override
  public String getGameName() {
    return "Adventure Game";
  }

  @Override
  public String getEndingMessage() {
    return "Game Over";
  }

  @Override
  public List<String> getCurrentState() {
    return List.of();
  }

  @Override
  public String[] getCurrentRoomItem() {
    return new String[0];
  }

  @Override
  public String[] getInventoryItems() {
    return new String[0];
  }

  @Override
  public String[] getExaminableObjects() {
    return new String[0];
  }

  @Override
  public String getImagePath(String object) {
    return "Executed: Image Path";
  }

  public String getLastCommand() {
    return lastCommand;
  }

  public void setCommand(String command) {
    this.lastCommand = command;
  }
}
