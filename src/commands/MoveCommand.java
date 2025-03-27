package commands;

import model.IGameModel;

public class MoveCommand implements ICommand {
  private final String direction;

  public MoveCommand(String direction) {
    this.direction = direction;
  }

  @Override
  public void execute(IGameModel model) {
    switch(this.direction.toLowerCase()) {
      case "n":
        System.out.println("You are going " + this.direction.toUpperCase() + "ORTH!\n");
        break;
      case "s":
        System.out.println("You are going " + this.direction.toUpperCase() + "OUTH!\n");
        break;
      case "e":
        System.out.println("You are going " + this.direction.toUpperCase() + "AST!\n");
        break;
      case "w":
        System.out.println("You are going " + this.direction.toUpperCase() + "EST!\n");
        break;
    }
    // model.goNorth();
  }
}
