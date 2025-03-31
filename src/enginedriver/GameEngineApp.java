package enginedriver;

import java.io.IOException;

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
    // Error handling
    if (gameFileName == null) {
      throw new IOException("Game File could not be found!");
    } else if (source == null) {
      throw new IOException("Source data could not be found!");
    } else if (output == null) {
      throw new IOException("Output destination could not be found!");
    }
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
}
