package controller;

import java.io.IOException;
import java.util.List;

import commands.ICommand;
import eventhandler.IEventHandler;
import model.IGameModel;

/**
 * The {@code GameController} class manages the flow of the game.
 * It handles the user input, the command processing, and interacting with the game model.
 */
public class GameController implements IController {
  private final IGameModel model;
  private final IEventHandler handler;

  /**
   * Constructs a {@code GameController} with the specified game model,
   * input source and output destination.
   *
   * @param model : game model.
   * @param handler : input and output handler
   */
  public GameController(IGameModel model, IEventHandler handler) {
    this.model = model;
    this.handler = handler;
  }

  /**
   * Starts the game loop, continues prompting the user for input, getting the appropriate commands
   * and updating the game state until the user quits the game.
   *
   * @throws IOException if an I/O error occurs while writing output.
   */
  @Override
  public void go() throws IOException {
    // Initialize input reader
    GameInputReader inputReader = new GameInputReader(this.handler);

    // Prompt user for name
    String avatarName = inputReader.getAvatarName();
    this.setPlayerName(avatarName);
    this.handler.write("You shalt now be named: " + avatarName.toUpperCase() + "\n\n");

    // Display initial game state
    this.handler.write(this.model.look());

    // Read string input from user
    String userInput = inputReader.readInput();

    // Initialize command finder
    GameCommandFinder commandFinder = new GameCommandFinder(this.handler);
    ICommand associatedCommand;

    // Continuous game loop until player quits
    while (!userInput.equalsIgnoreCase("Q")) {
      associatedCommand = commandFinder.getCommand(userInput); // find command associated to input
      associatedCommand.execute(this.model); // executes command
      if (this.model.getPlayer().getHealth() <= 0) {
        break;
      }
      userInput = inputReader.readInput(); // prompt user for next command
    }

    // Game Ending Message
    this.handler.write(this.model.getEndingMessage());
  }

  public void setPlayerName(String name) {
    this.model.getPlayer().setName(name);
  }
}