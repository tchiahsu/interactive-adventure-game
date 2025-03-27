package controller;

import java.io.IOException;

import commands.ICommand;
import model.GameModel;
import model.IGameModel;

public class GameController implements IController{
  private final IGameModel model;
  private GameInputReader dataReader;
  private GameCommandFinder commandFinder;

  public GameController(IGameModel model) {
    this.model = model;
  }

  @Override
  public void go() throws IOException {
    this.dataReader = new GameInputReader();
    this.commandFinder = new GameCommandFinder();

    String userInput = this.dataReader.readInput();
    while(!userInput.equalsIgnoreCase("Q")) {
      ICommand associatedCommand = this.commandFinder.getCommand(userInput);
      associatedCommand.execute(this.model);
      userInput = this.dataReader.readInput();
    }
  }

  // DELETE: THIS IS ONLY TO CHECK THAT IT WORKS
  public static void main(String [] args) throws IOException {
    String hallwayjson = "src/data/simple_hallway.json";
    IGameModel model = new GameModel(hallwayjson);
    IController controller = new GameController(model);
    controller.go();
  }
}
