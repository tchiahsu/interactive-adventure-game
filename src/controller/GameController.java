package controller;

import java.io.IOException;

import commands.ICommand;
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
    System.out.println(this.model.look());
    GameInputReader inputReader = new GameInputReader(this.input, this.output);
    GameCommandFinder commandFinder = new GameCommandFinder(this.output);
    ICommand associatedCommand;

    String userInput = inputReader.readInput();
    while(!userInput.equalsIgnoreCase("Q")) {
      associatedCommand = commandFinder.getCommand(userInput);
      associatedCommand.execute(this.model);
      userInput = inputReader.readInput();
    }
  }
}
