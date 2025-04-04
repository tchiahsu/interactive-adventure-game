import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import enginedriver.GameEngineApp;
import view.View;

/**
 * This Main class invokes the entry point {@link GameEngineApp} for the adventure game.
 */
public class Main {

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
//      View view = new View();

  }
}