package commands;

import model.IGameModel;

public class MoveCommand implements ICommand {
  private String direction;

  public MoveCommand(String direction) {
    this.direction = direction;
  }

  @Override
  public void execute(IGameModel model) {
    switch(this.direction.toUpperCase()) {
      case "N", "NORTH":
        this.direction = "NORTH";
        break;
      case "S", "SOUTH":
        this.direction = "SOUTH";
        break;
      case "E", "EAST":
        this.direction = "EAST";
        break;
      case "W", "WEST":
        this.direction = "WEST";
        break;
    }
    String output = model.move(this.direction);
    System.out.println(output);
  }
}
