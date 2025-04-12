package enginedriver;

import java.io.IOException;

import controller.IController;
import controller.IViewController;
import controller.TextController;
import controller.ViewController;
import eventhandler.BatchConsoleHandler;
import eventhandler.BatchFileHandler;
import eventhandler.ConsoleHandler;
import eventhandler.GuiHandler;
import eventhandler.IEventHandler;
import eventhandler.IGuiEventHandler;
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
   * @param gameFileName : the file with the game data.
   * @param mode : the game mode that will be played.
   * @throws IOException if there is an error with input/output.
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

  /**
   * Loaded Constructor for the Batch-Console game mode.
   * @param gameFileName : name of the game file.
   * @param mode : game mode we are playing in.
   * @param sourceFile : the file where input comes from.
   * @throws IOException if there is an error with input/output.
   */
  public GameEngineApp(String gameFileName, GameMode mode, String sourceFile) throws IOException {
    if (gameFileName == null) {
      throw new IOException("Game File could not be found!");
    }
    this.gameFile = gameFileName;
    this.mode = mode;
    this.sourceFile = sourceFile;
    this.targetFile = null;
  }

  /**
   * Loaded Constructor for the Batch-File game mode.
   * @param gameFileName : name of the game file.
   * @param mode : game mode we are playing in.
   * @param sourceFile : file where input comes from.
   * @param targetFile : file where output goes to.
   * @throws IOException if there is an input/output error.
   */
  public GameEngineApp(String gameFileName, GameMode mode, String sourceFile,
                       String targetFile) throws IOException {
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
        controller = new TextController(model, handler);
        controller.go();
      }
      case GRAPHICS -> {
        GameView view = new GameView();
        IGuiEventHandler handler = new GuiHandler(view);
        IViewController guiController = new ViewController(model, handler);
        view.setController(guiController);
        view.startView();
      }
      case BATCH_CONSOLE -> {
        IEventHandler handler = new BatchConsoleHandler(this.sourceFile);
        controller = new TextController(model, handler);
        controller.go();
      }
      case BATCH_FILE -> {
        IEventHandler handler = new BatchFileHandler(this.sourceFile, this.targetFile);
        controller = new TextController(model, handler);
        controller.go();
      }
    }

  }
}
