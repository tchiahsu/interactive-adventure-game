package commands;

import model.IGameModel;

public class InventoryCommand implements ICommand {

  @Override
  public void execute(IGameModel model) {
    System.out.println("You are checking your INVENTORY!\n");
    //model.checkInventory();
  }
}
