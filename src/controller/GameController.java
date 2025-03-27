package controller;

import java.io.IOException;

import commands.ICommand;
import model.GameModel;
import model.IGameModel;

public class GameController implements IController{
  private final IGameModel model;
  private GameCommandReader dataReader;

  public GameController(IGameModel model) {
    this.model = model;
  }

  @Override
  public void go() throws IOException {
    this.dataReader = new GameCommandReader();
    while(this.dataReader.getUserInput()) {
      ICommand command = this.dataReader.getCommand();
      command.execute(this.model);
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
