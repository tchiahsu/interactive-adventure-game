package commands;

import model.IGameModel;

public class MoveCommand implements ICommand {
  private final String direction;

  public MoveCommand(String direction) {
    this.direction = direction;
  }

  @Override
  public void execute(IGameModel model) {
    switch(this.direction.toUpperCase()) {
      case "N", "NORTH":
        System.out.println("You are going NORTH!\n");
        break;
      case "S", "SOUTH":
        System.out.println("You are going SOUTH!\n");
        break;
      case "E", "EAST":
        System.out.println("You are going EAST!\n");
        break;
      case "W", "WEST":
        System.out.println("You are going WEST!\n");
        break;
    }
    // model.goNorth();
  }
}
