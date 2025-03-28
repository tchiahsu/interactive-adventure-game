package commands;

import java.io.IOException;

import model.IGameModel;

public class InventoryCommand implements ICommand {
  private final Appendable output;

  public InventoryCommand(Appendable output) {
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.checkInventory());
  }
}
