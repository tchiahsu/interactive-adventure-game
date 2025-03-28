package engineDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import controller.GameController;
import controller.GameInputReader;
import controller.IController;
import model.GameModel;
import model.IGameModel;

public class GameEngineApp {
  private final String gameFile;
  private final Readable source;
  private final Appendable output;

  public GameEngineApp(String gameFileName, Readable source, Appendable output) throws IOException {
    this.gameFile = gameFileName;
    this.source = source;
    this.output = output;
  }

  public void start() throws IOException {
    IGameModel model = new GameModel(this.gameFile);
    IController controller = new GameController(model, this.source, this.output);
    controller.go();
  }

  // DELETE, ONLY FOR TESTING PURPOSES
  public static void main(String [] args) throws IOException {
    String hallwayjson = "src/data/simple_hallway.json";
    BufferedReader stringReader = new BufferedReader(new InputStreamReader(System.in));
    GameEngineApp engine = new GameEngineApp(hallwayjson, stringReader, System.out);
    engine.start();
  }
}
