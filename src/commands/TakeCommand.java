package commands;

import model.IGameModel;

public class TakeCommand implements ICommand {
  private final String item;

  public TakeCommand(String item) {
    this.item = item;
  }

  @Override
  public void execute(IGameModel model) {
//    System.out.println("You are TAKING an " + this.item + "!\n");
    String output = model.takeItem(this.item);
    System.out.println(output);
  }
}
