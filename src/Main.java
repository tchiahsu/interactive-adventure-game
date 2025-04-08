import java.io.File;
import java.io.IOException;

import enginedriver.GameEngineApp;
import enginedriver.GameMode;

/**
 * This Main class invokes the entry point {@link GameEngineApp} for the adventure game.
 */
public class Main {

  private static final String CONSOLE_GAME = "-text";
  private static final String GRAPHICAL_GAME = "-graphics";
  private static final String BATCH_GAME = "-batch";

  /**
   * The main method that invokes the entry point.
   * The specified Game File for this is the simple_hallway.json file.
   * It will create a new instance of {@link GameEngineApp} with the given json file configuration,
   * and then starts the game.
   *
   * @param args : Command line arguments
   * @throws IOException if an I/O error occurs during input/output operations.
   */
  public static void main(String [] args) throws IOException {
//    String fileABC = "src/data/simple_hallway.json";
//    GameEngineApp engine2 = new GameEngineApp(fileABC, GameMode.GRAPHICS);
//    engine2.start();

    if (args.length < 2 || args.length > 4) {
      displayCommands();
      return;
    }

    String filePath = args[0];
    String playStyle = args[1];

    if (isInvalidFile(filePath) || !filePath.endsWith(".json")) {
      System.out.println("Invalid game file.");
      return;
    }

    GameEngineApp engine;
    switch (playStyle) {
      case CONSOLE_GAME -> {
        engine = new GameEngineApp(filePath, GameMode.CONSOLE);
        engine.start();
      }
      case GRAPHICAL_GAME -> {
        engine = new GameEngineApp(filePath, GameMode.GRAPHICS);
        engine.start();
      }
      case BATCH_GAME -> {
        if (args.length < 3) {
          System.out.println("Missing files for batch game.");
        }

        if (args.length == 3) {
          String sourceFile = args[2];
          if (isInvalidFile(sourceFile)) {
            System.out.println("Invalid source file.");
            return;
          }
          engine = new GameEngineApp(filePath, GameMode.BATCH_CONSOLE, sourceFile);
          engine.start();
        } else if (args.length == 4) {
          String sourceFile = args[2];
          String targetFile = args[3];
          if (isInvalidFile(sourceFile) || isInvalidFile(targetFile)) {
            System.out.println("Invalid source or target file.");
          }
          engine = new GameEngineApp(filePath, GameMode.BATCH_FILE, sourceFile, targetFile);
          engine.start();
        }
      }
      default -> displayCommands();
    }
  }

  private static void displayCommands () {
    System.out.println("Incorrect command-line format for game engine.");
    System.out.println("Formats allowed:");
    System.out.println("game_engine <filename> -text");
    System.out.println("game_engine <filename> -graphics");
    System.out.println("game_engine <filename> -batch <source file>");
    System.out.println("game_engine <filename> -batch <source file> <target file>");
  }

  private static boolean isInvalidFile (String path) {
    File file = new File(path);
    return !file.isFile();
  }
}