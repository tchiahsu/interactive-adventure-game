package commands;

import java.io.IOException;

import model.IGameModel;

public class DropCommand implements ICommand {
  private final String item;
  private final Appendable output;

  public DropCommand(String item, Appendable output) {
    this.item = item;
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.dropItem(this.item));
  }
}
