package commands;

import model.IGameModel;

public class LookCommand implements ICommand {

  @Override
  public void execute(IGameModel model) {
    //System.out.println("You are LOOKING around!\n");
    String output = model.look();
    System.out.println(output);
  }
}
