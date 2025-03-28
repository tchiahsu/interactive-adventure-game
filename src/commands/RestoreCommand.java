package commands;

import java.io.IOException;

import model.IGameModel;

public class RestoreCommand implements ICommand {
  private final Appendable output;

  public RestoreCommand(Appendable output) {
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.restoreGame());
  }
}
