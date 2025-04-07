package enginedriver;

import java.io.IOException;

import controller.GameController;
import controller.IController;
import io.IOHandler;
import model.GameModel;
import model.IGameModel;

/**
 * The {@code GameEngineApp} class is the single entry point for the game.
 */
public class GameEngineApp {
  private final String gameFile;
  private final IOHandler io;

  /**
   * The {@code GameEngineApp} class initializes the game with the specified game file name,
   * the input source and the output destination.
   * @param gameFileName : game file with the configurations
   * @param io : manages output and input operations
   * @throws IOException if an error occurs while accessing the game file
   */
  public GameEngineApp(String gameFileName, IOHandler io) throws IOException {
    // Error handling
    if (gameFileName == null) {
      throw new IOException("Game File could not be found!");
    } else if (io == null) {
      throw new IOException("Input and Output source could not be found!");
    }

    this.gameFile = gameFileName;
    this.io = io;
  }

  /**
   * Starts the game by initializing the game model and controller.
   * It calls the go method in the controller to start the game flow.
   */
  public void start() throws IOException {
    IGameModel model = new GameModel(this.gameFile);
    IController controller = new GameController(model, this.io);
    controller.go();
  }
}
