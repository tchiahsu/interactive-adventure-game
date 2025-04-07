package commands;

import java.io.IOException;

import io.IOHandler;
import model.IGameModel;

/**
 * The {@code RestoreCommand} class represents a command for answering a puzzle in the adventure
 * game. It implements the {@link ICommand} interface.
 */
public class RestoreCommand implements ICommand {
  private final IOHandler output;

  /**
   * Constructs an {@code RestoreCommand} object with the specified output destination for the
   * restore message.
   *
   * @param output : the {@link IOHandler} object where the command's output will be written.
   */
  public RestoreCommand(IOHandler output) {
    this.output = output;
  }

  /**
   * Executes the answer command by passing the user's response to the game model.
   *
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the output.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.write(model.restoreGame());
  }
}
