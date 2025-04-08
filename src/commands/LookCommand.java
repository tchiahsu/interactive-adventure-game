package commands;

import java.io.IOException;

import eventhandler.IEventHandler;
import model.IGameModel;

/**
 * The {@code LookCommand} class represents a command looking at the environment/surroundings
 * of a room in the adventure game.
 * It implements the {@link ICommand} interface.
 */
public class LookCommand implements ICommand {
  private final IEventHandler output;

  /**
   * Constructs an {@code LookCommand} object with the specified output destination.
   *
   * @param output : the {@link IEventHandler} object where the command's output will be written.
   */
  public LookCommand(IEventHandler output) {
    this.output = output;
  }

  /**
   * Executes the look command by accessing the description of the room in the game model.
   *
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the output.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.write(model.look());
  }
}
