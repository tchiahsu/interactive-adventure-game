package commands;

import model.IGameModel;

public class UseCommand implements ICommand {
  private final String item;

  public UseCommand(String item) {
    this.item = item;
  }

  @Override
  public void execute(IGameModel model) {
    //System.out.println("You are USING an " + this.item + "\n");
    String output = model.useItem(this.item);
    System.out.println(output);
  }
}
