package commands;

import java.io.IOException;

import model.IGameModel;

/**
 * The {@code ExamineCommand} class represents a command to examine an object in the adventure game.
 * It implements the {@link ICommand} class.
 */
public class ExamineCommand implements ICommand {
  private final String object;
  private final Appendable output;

  /**
   * Constructs an {@code ExamineCommand} object with the specified object and output avenue.
   * @param object : the object we are trying to examine.
   * @param output : the {@link Appendable} object where the command's output will be written.
   */
  public ExamineCommand(String object, Appendable output) {
    this.object = object;
    this.output = output;
  }

  /**
   * Executes the examine command by passing the object that we want to examine to the game model.
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the response.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.examine(this.object));
  }
}
