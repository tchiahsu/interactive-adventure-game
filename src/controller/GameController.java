package controller;

import java.io.IOException;

import commands.ICommand;
import model.GameModel;
import model.IGameModel;

public class GameController implements IController {
  private final IGameModel model;
  private final Readable input;
  private final Appendable output;


  public GameController(IGameModel model, Readable input, Appendable output) {
    this.model = model;
    this.input = input;
    this.output = output;
  }

  @Override
  public void go() throws IOException {
    GameInputReader dataReader = new GameInputReader(this.input, this.output);
    GameCommandFinder commandFinder = new GameCommandFinder();

    String userInput = dataReader.readInput();
    while(!userInput.equalsIgnoreCase("Q")) {
      ICommand associatedCommand = commandFinder.getCommand(userInput);
      associatedCommand.execute(this.model);
      userInput = dataReader.readInput();
    }
  }
}
