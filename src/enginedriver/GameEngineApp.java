package enginedriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.GameController;
import controller.IController;
import model.GameModel;
import model.IGameModel;

/**
 * The {@code GameEngineApp} class is the single entry point for the game.
 */
public class GameEngineApp {
  private final String gameFile;
  private final Readable source;
  private final Appendable output;

  /**
   * The {@code GameEngineApp} class initializes the game with the specified game file name,
   * the input source and the output destination.
   * @param gameFileName : game file with the configurations
   * @param source : input source
   * @param output : output destination
   * @throws IOException if an error occurs while accessing the game file
   */
  public GameEngineApp(String gameFileName, Readable source, Appendable output) throws IOException {
    this.gameFile = gameFileName;
    this.source = source;
    this.output = output;
  }

  /**
   * Starts the game by initializing the game model and controller.
   * It calls the go method in the controller to start the game flow.
   * @throws IOException if an I/O error occurs when the game is executed.
   */
  public void start() throws IOException {
    IGameModel model = new GameModel(this.gameFile);
    IController controller = new GameController(model, this.source, this.output);
    controller.go();
  }

  /**
   * ONLY TO RUN THE GAME, DELETE THIS BEFORE SUBMITTING.
   */
  public static void main(String [] args) throws IOException {
    String hallwayjson = "src/data/simple_hallway.json";
    BufferedReader stringReader = new BufferedReader(new InputStreamReader(System.in));
    GameEngineApp engine = new GameEngineApp(hallwayjson, stringReader, System.out);
    engine.start();
  }
}
