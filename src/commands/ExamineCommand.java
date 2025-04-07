package commands;

import java.io.IOException;

import io.IGameOutput;
import model.IGameModel;

/**
 * The {@code ExamineCommand} class represents a command to examine an object in the adventure game.
 * It implements the {@link ICommand} interface.
 */
public class ExamineCommand implements ICommand {
  private final String object;
  private final IGameOutput output;

  /**
   * Constructs an {@code ExamineCommand} object with the specified object and output destination.
   *
   * @param object : the object we are trying to examine.
   * @param output : the {@link IGameOutput} object where the command's output will be written.
   */
  public ExamineCommand(String object, IGameOutput output) {
    this.object = object;
    this.output = output;
  }

  /**
   * Executes the examine command by passing the object that we want to examine to the game model.
   *
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the response.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.examine(this.object));
  }
}
