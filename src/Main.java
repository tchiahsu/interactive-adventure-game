import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import enginedriver.GameEngineApp;
import io.FileInput;
import io.FileOutput;
import io.IGameInput;
import io.IGameOutput;
import io.TextInput;
import io.TextOutput;
import view.GameView;

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
    String hallwayjson = "src/data/simple_hallway.json";
    BufferedReader stringReader = new BufferedReader(new InputStreamReader(System.in));
    GameEngineApp engine = new GameEngineApp(hallwayjson, stringReader, System.out);
    engine.start();

    //run this command if you want test running the view:
//    GameView view = new GameView();

//    if (args.length < 2 || args.length > 4) {
//      displayCommands();
//      return;
//    }
//
//    String filePath = args[0];
//    if (isInvalidFile(filePath) || !filePath.endsWith(".json")) {
//      System.out.println("Invalid game file.");
//      return;
//    }
//
//    String playStyle = args[1];
//    if (playStyle.equals(CONSOLE_GAME)) {
//      playConsoleGame(filePath);
//    } else if (playStyle.equals(GRAPHICAL_GAME)) {
//      // Play game in view mode
//    } else if (playStyle.equals(BATCH_GAME)) {
//      handleBatchGame(args);
//    } else {
//      displayCommands();
//    }
//  }
//
//    private static void displayCommands () {
//      System.out.println("Incorrect command-line format for game engine.");
//      System.out.println("Formats allowed:");
//      System.out.println("game_engine <filename> -text");
//      System.out.println("game_engine <filename> -graphics");
//      System.out.println("game_engine <filename> -batch <source file>");
//      System.out.println("game_engine <filename> -batch <source file> <target file>");
//    }
//
//    private static boolean isInvalidFile (String path) {
//      File file = new File(path);
//      return !file.isFile();
//    }
//
//    private static void playConsoleGame (String filePath) throws IOException {
//      IGameInput input = new TextInput();
//      IGameOutput output = new TextOutput();
//      GameEngineApp engine = new GameEngineApp(filePath, input, output);
//      engine.start();
//    }
//
//    private static void handleBatchGame (String[]args) throws IOException {
//      if (args.length < 3) {
//        System.out.println("Missing files for batch game.");
//        return;
//      }
//
//      String sourceFile = args[2];
//      if (isInvalidFile(sourceFile)) {
//        System.out.println("Invalid source file.");
//        return;
//      }
//
//      String filePath = args[0];
//      if (args.length == 3) {
//        playBatchConsoleGame(filePath, sourceFile);
//      } else {
//        String targetFile = args[3];
//        if (isInvalidFile(targetFile)) {
//          System.out.println("Invalid target file.");
//          return;
//        }
//        playBatchFileGame(filePath, sourceFile, targetFile);
//      }
//  }
//
//  private static void playBatchConsoleGame(String filePath, String sourcePath) throws IOException {
//    IGameInput input = new TextInput();
//    IGameOutput output = new TextOutput();
//    GameEngineApp engine = new GameEngineApp(filePath, input, output);
//    engine.start();
//  }
//
//  private static void playBatchFileGame(String filePath, String sourcePath, String targetPath) throws IOException {
//    IGameInput input = new FileInput(sourcePath);
//    IGameOutput output = new FileOutput(targetPath);
//    GameEngineApp engine = new GameEngineApp(filePath, input, output);
//    engine.start();
  }
}