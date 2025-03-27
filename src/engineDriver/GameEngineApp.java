package engineDriver;

import controller.GameController;
import model.GameModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * GameEngine class represents the starting point of the game.
 */
public class GameEngineApp {
  private final GameModel model;
  private final GameController controller;
  private final Scanner inputScanner;
  private final Appendable output;

  /**
   * Initializes the game engine with a given game file, input source, and output destination.
   */
  public GameEngineApp(String gameFileName, Readable source, Appendable output) throws IOException {
    this.output = output;
    this.inputScanner = new Scanner(source);

    // Load game world from the JSON file
    this.model = new GameModel(gameFileName);
            //call method -> loadGameFromFile(gameFileName); - TBD

    // Initialize the game controller
    this.controller = new GameController(this.model); // needs a parameter
  }

  /**
   * Starts the game loop, reading commands from the player and processing them.
   * @throws IOException If an error occurs while writing output
   */
  public void start() throws IOException {
    //Prints the game intro and current room description
    //Reads user input via Scanner
    //Processes commands via GameController
    //Outputs responses and continues until the game ends or the player quits - while loop
  }
}
