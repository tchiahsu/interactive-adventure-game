package commands;

import java.io.IOException;

import model.IGameModel;

/**
 * The {@code MoveCommand} class represents a command for moving a player to another room in
 * the adventure game.
 * It implements the {@link ICommand} class.
 */
public class MoveCommand implements ICommand {
  private String direction;
  private final Appendable output;

  /**
   * Constructs an {@code MoveCommand} object with the specified direction and output avenue.
   * @param direction : direction the player moves
   * @param output : the {@link Appendable} object where the command's output will be written.
   */
  public MoveCommand(String direction, Appendable output) {
    this.direction = direction;
    this.output = output;
  }

  /**
   * Helper functions that modifies the user direction input to the system specified direction.
   * @param direction : direction the player moves to
   */
  private void convertInputToValid(String direction) {
    switch (this.direction.toUpperCase()) {
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

  /**
   * Executes the move command by passing the direction the player moves to the game model.
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the output.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    convertInputToValid(this.direction);
    this.output.append(model.move(this.direction));
  }
}
