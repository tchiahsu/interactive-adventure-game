package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commands.ICommand;
import eventhandler.IGuiEventHandler;
import model.IGameModel;

public class ViewController implements IViewController {
  private final IGameModel model;
  private final IGuiEventHandler handler;

  public ViewController(IGameModel model, IGuiEventHandler handler) {
    this.model = model;
    this.handler = handler;
  }

  @Override
  public void executeCommand(String command) throws IOException {
    if (validateInput(command)) {
      String[] splitCommand = command.split(" ", 2);
      this.handler.setCommandAction(splitCommand[0]);
      GameCommandFinder commandFinder = new GameCommandFinder(this.handler);
      ICommand associatedCommand = commandFinder.getCommand(command);
      associatedCommand.execute(this.model);
    }
  }

  @Override
  public List<String> getCurrentState() {
    return this.model.getCurrentState();
  }

  @Override
  public String[] getCurrentRoomItems() {
    return this.model.getCurrentRoomItem();
  }

  @Override
  public String[] getInventoryItems() {
    return this.model.getInventoryItems();
  }

  @Override
  public String[] getExaminableObjects() {
    return this.model.getExaminableObjects();
  }

  @Override
  public String getImagePath(String object) {
    return this.model.getImagePath(object);
  }

  @Override
  public String getGameName() {
    return this.model.getGameName();
  }

  @Override
  public String getGameSummary() {
    return this.model.getEndingMessage();
  }

  @Override
  public void setPlayerName(String name) {
    this.model.getPlayer().setName(name);
  }


  /**
   * Validates if the given input command is a valid command for the adventure game.
   *
   * @param input : user string input.
   * @return true if command is valid, false otherwise.
   */
  private boolean validateInput(String input) {
    String[] parts = input.split(" ", 2);
    String action = parts[0];

    Commands commandType = Commands.getEnum(action);
    if (commandType == null) {
      return false;
    }

    return commandType.getRequiresArgument() ? parts.length == 2
      && !parts[1].trim().isEmpty() : parts.length == 1;
  }
}
