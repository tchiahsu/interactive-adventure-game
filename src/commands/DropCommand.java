package commands;

import model.IGameModel;

public class DropCommand implements ICommand {
  private String item;

  public DropCommand(String item) {
      this.item = item;
  }

  @Override
  public void execute(IGameModel model) {
    // System.out.println("You are DROPPING " + this.item + "!\n");
    String output = model.dropItem(this.item);
    System.out.println(output);
  }
}
