package commands;

import java.io.IOException;

import model.IGameModel;

public class MoveCommand implements ICommand {
  private String direction;
  private final Appendable output;

  public MoveCommand(String direction, Appendable output) {
    this.direction = direction;
    this.output = output;
  }

  private void convertInputToValid(String direction) {
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
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    convertInputToValid(this.direction);
    this.output.append(model.move(this.direction));
  }
}
