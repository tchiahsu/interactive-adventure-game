package commands;

import java.io.IOException;

import model.IGameModel;

public class SaveCommand implements ICommand {
  private final Appendable output;

  public SaveCommand(Appendable output) {
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    output.append(model.saveGame());
  }
}
