package commands;

import java.io.IOException;

import model.IGameModel;

public class TakeCommand implements ICommand {
  private final String item;
  private final Appendable output;

  public TakeCommand(String item, Appendable output) {
    this.item = item;
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    output.append(model.takeItem(this.item));
  }
}
