package enginedriver;

import java.io.IOException;

import eventhandler.BatchConsoleHandler;
import eventhandler.BatchFileHandler;
import eventhandler.ConsoleHandler;
import eventhandler.GuiHandler;
import controller.GameController;
import controller.IController;
import eventhandler.IEventHandler;
import model.GameModel;
import model.IGameModel;
import view.GameView;

/**
 * The {@code GameEngineApp} class is the single entry point for the game.
 */
public class GameEngineApp {
  private final String gameFile;
  private final GameMode mode;
  private final String sourceFile;
  private final String targetFile;

  /**
   * The {@code GameEngineApp} class initializes the game with the specified game file name,
   * the input source and the output destination.
   * @param gameFileName : game file with the configurations
   * @throws IOException if an error occurs while accessing the game file
   */
  public GameEngineApp(String gameFileName, GameMode mode) throws IOException {
    // Error handling
    if (gameFileName == null) {
      throw new IOException("Game File could not be found!");
    }
    this.gameFile = gameFileName;
    this.mode = mode;
    this.sourceFile = null;
    this.targetFile = null;
  }

  public GameEngineApp(String gameFileName, GameMode mode, String sourceFile) throws IOException {
    if (gameFileName == null) {
      throw new IOException("Game File could not be found!");
    }
    this.gameFile = gameFileName;
    this.mode = mode;
    this.sourceFile = sourceFile;
    this.targetFile = null;
  }

  public GameEngineApp(String gameFileName, GameMode mode, String sourceFile, String targetFile) throws IOException {
      if (gameFileName == null) {
        throw new IOException("Game File could not be found!");
      }
      this.gameFile = gameFileName;
      this.mode = mode;
      this.sourceFile = sourceFile;
      this.targetFile = targetFile;
  }


  /**
   * Starts the game by initializing the game model and controller.
   * It calls the go method in the controller to start the game flow.
   */
  public void start() throws IOException {
    IGameModel model = new GameModel(this.gameFile);
    IController controller;

    switch (this.mode) {
      case CONSOLE -> {
        IEventHandler handler = new ConsoleHandler();
        controller = new GameController(model, handler);
        controller.go();
      }
      case GRAPHICS -> {
        GameView view = new GameView();
        IEventHandler handler = new GuiHandler(view);
        controller = new GameController(model, handler);
        view.setController(controller);
      }
      case BATCH_CONSOLE -> {
        IEventHandler handler = new BatchConsoleHandler(this.sourceFile);
        controller = new GameController(model, handler);
        controller.go();
      }
      case BATCH_FILE -> {
        IEventHandler handler = new BatchFileHandler(this.sourceFile, this.targetFile);
        controller = new GameController(model, handler);
        controller.go();
      }
    }

  }
}
