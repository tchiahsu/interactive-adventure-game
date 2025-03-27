package engineDriver;

import controller.GameController;
import model.GameModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * GameEngine class represents the starting point of the game.
 */
public class GameEngineApp {
  private final GameModel game;
  private final GameController controller;
  private final Scanner inputScanner;
  private final Appendable output;

  /**
   * Initializes the game engine with a given game file, input source, and output destination.
   */
  public GameEngineApp(String gameFileName, Readable source, Appendable output) {
    this.output = output;
    this.inputScanner = new Scanner(source);

    // Load game world from the JSON file
//    this.game = call method -> loadGameFromFile(gameFileName); - TBD

    // Initialize the game controller
    this.controller = new GameController(); // needs a parameter
  }

  /**
   * Starts the game loop, reading commands from the player and processing them.
   * @throws IOException If an error occurs while writing output
   */
  public void start() throws IOException {}
}
