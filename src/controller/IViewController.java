package controller;

import java.io.IOException;
import java.util.List;

/**
 * Represents a game controller for a GUI-based game that manages the flow of the game.
 * Implementing this interface defines how the game should be initiated and how commands
 * get executed.
 */
public interface IViewController {

  /**
   * Executes a command in the game model.
   * @param command : input command.
   * @throws IOException if an I/O error occurs during execution.
   */
  void executeCommand(String command) throws IOException;

  /**
   * Gets the current state of the game.
   * @return current state of the game.
   */
  List<String> getCurrentState();

  /**
   * Sets the name of the player.
   * @param name : player name.
   */
  void setPlayerName(String name);

  /**
   * Gets an array of items in the current room.
   * @return array of items in current room.
   */
  String[] getRoomItems();

  /**
   * Gets the items in inventory.
   * @return items in inventory.
   */
  String[] getInventoryItems();

  /**
   * Gets an array of examinable objects in the room.
   * @return examinable objects.
   */
  String[] getExaminableObjects();

  /**
   * Gets the path of the image for the specified object.
   * @param object : object whose path we want.
   * @return image path of object.
   */
  String getImagePath(String object);

  /**
   * Gets the current name of the game. This depends on the game file that is
   * being used.
   * @return name of the game.
   */
  String getGameName();

  /**
   * Gets the game ending message.
   * @return the game ending message.
   */
  String getGameSummary();
}
