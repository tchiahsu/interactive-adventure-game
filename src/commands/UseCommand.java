package commands;

import java.io.IOException;

import model.IGameModel;

public class UseCommand implements ICommand {
  private final String item;
  private final Appendable output;

  public UseCommand(String item, Appendable output) {
    this.item = item;
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.useItem(this.item));
  }
}
