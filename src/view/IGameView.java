package view;

import java.io.IOException;

/**
 * Interface that represents GameView of the Adventure Game.
 */
public interface IGameView {
  /**
   * Starts the game view, prompts for player name, and displays the user interface.
   */
  void startView();

  /**
   * Method that updates the view with the current description.
   */
  void updateView();

  /**
   *  Method to show a pop-up with an image and text.
   * @param s text message.
   * @param title title of the dialog.
   * @throws IOException if image cannot be loaded.
   */
  void showPopUp(String s, String title) throws IOException;

  /**
   * Method that shows a pop-up box when player tries to
   * move in a blocked path.
   * @param s String description to display.
   * @throws IOException error if image is not found.
   */
  void showBlockedPopUp(String s) throws IOException;

  /**
   * Method to show a pop-up with the description of the item to use.
   * @param s : The text to display in the dialog.
   */
  void showItemUsePopUp(String s);

  /**
   * Method that displays a dialog box that takes in user input.
   */
  void showInputDialog(String title);

  /**
   * Method that shows a pop-up for the answer command.
   * @param s description to return.
   * @throws IOException error if image is not found.
   */
  void showAnswerPopUp(String s) throws IOException;

  /**
   * Checks if the player is dead based on current health.
   * @return true if player health is 0 or below, false otherwise.
   */
  boolean isPlayerAsleep();

  /**
   * Method that handles the actions for buttons in the
   * {@code MenuBar} class.
   */
  void setMenuActionListener();

  /**
   * Shows a game-over dialog and exits the game.
   * @throws IOException if image resource fails to load.
   */
  void showGameOverPopUp() throws IOException;
  void showFullInventoryPopUp(String s);
}
