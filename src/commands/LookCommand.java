package commands;

import java.io.IOException;

import model.IGameModel;

public class LookCommand implements ICommand {
  private final Appendable output;

  public LookCommand(Appendable output) {
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.look());
  }
}
