package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commands.ICommand;
import eventhandler.IGuiEventHandler;
import model.IGameModel;

/**
 * The {@code ViewController} class manages the flow of the game.
 * It handles the event clicks, the command processing, and interacting GUI-based game.
 */
public class ViewController implements IViewController {
  private final IGameModel model;
  private final IGuiEventHandler handler;

  /**
   * Creates a ViewController objects with the specified model and event handler.
   * @param model : the game model that we are playing.
   * @param handler : the type of game mode that we are playing.
   */
  public ViewController(IGameModel model, IGuiEventHandler handler) {
    this.model = model;
    this.handler = handler;
  }

  /**
   * Executes a command in the game model.
   * @param command : input command.
   * @throws IOException if an I/O error occurs during execution.
   */
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

  /**
   * Gets the current state of the game.
   * @return current state of the game.
   */
  @Override
  public List<String> getCurrentState() {
    return this.model.getCurrentState();
  }

  /**
   * Gets an array of items in the current room.
   * @return array of items in current room.
   */
  @Override
  public String[] getRoomItems() {
    return this.model.getRoomItems();
  }

  /**
   * Gets the items in inventory.
   * @return items in inventory.
   */
  @Override
  public String[] getInventoryItems() {
    return this.model.getInventoryItems();
  }

  /**
   * Gets an array of examinable objects in the room.
   * @return examinable objects.
   */
  @Override
  public String[] getExaminableObjects() {
    return this.model.getExaminableObjects();
  }

  /**
   * Gets the path of the image for the specified object.
   * @param object : object whose path we want.
   * @return image path of object.
   */
  @Override
  public String getImagePath(String object) {
    return this.model.getImagePath(object);
  }

  /**
   * Gets the current name of the game. This depends on the game file that is
   * being used.
   * @return name of the game.
   */
  @Override
  public String getGameName() {
    return this.model.getGameName();
  }

  /**
   * Gets the game ending message.
   * @return the game ending message.
   */
  @Override
  public String getGameSummary() {
    return this.model.getEndingMessage();
  }

  /**
   * Sets the name of the player's avatar.
   * @param name : player avatar name.
   */
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
