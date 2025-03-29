package controller;

import java.io.IOException;

import commands.ICommand;
import model.IGameModel;

/**
 * The {@code GameController} class manages the flow of the game.
 * It handles the user input, the command processing, and interacting with the game model.
 */
public class GameController implements IController {
  private final IGameModel model;
  private final Readable input;
  private final Appendable output;

  /**
   * Constructs a {@code GameController} with the specified game model,
   * input source and output destination.
   * @param model : game model
   * @param input : input source
   * @param output : output destination
   */
  public GameController(IGameModel model, Readable input, Appendable output) {
    this.model = model;
    this.input = input;
    this.output = output;
  }

  /**
   * Starts the game loop, continues prompting the user for input, getting the appropriate commands
   * and updating the game state until the user quits the game.
   * @throws IOException if an I/O error occurs while writing output
   */
  @Override
  public void go() throws IOException {
    // Initialize input reader and command processor
    GameInputReader inputReader = new GameInputReader(this.input, this.output);
    GameCommandFinder commandFinder = new GameCommandFinder(this.output);
    ICommand associatedCommand;

    // Prompt user for name
    String avatarName = inputReader.getAvatarName();
    this.model.getPlayer().setName(avatarName);
    this.output.append("You shalt now be named: ").append(avatarName.toUpperCase()).append("\n\n");

    // Display initial game state
    this.output.append(this.model.look());

    // Read string input from user
    String userInput = inputReader.readInput();

    // Continuous game loop until player quits
    while (!userInput.equalsIgnoreCase("Q")) {
      associatedCommand = commandFinder.getCommand(userInput); // find command associated to input
      associatedCommand.execute(this.model); // executes command
      userInput = inputReader.readInput(); // prompt user for next command
    }
  }
}
