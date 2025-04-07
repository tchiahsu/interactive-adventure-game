package commands;

import java.io.IOException;

import io.IGameOutput;
import model.IGameModel;

/**
 * The {@code AnswerCommand} class represents a command for answering a puzzle in the adventure
 * game.
 * It implements the {@link ICommand} interface.
 */
public class AnswerCommand implements ICommand {
  private final String answer;
  private final IGameOutput output;

  /**
   * Constructs an {@code AnswerCommand} object with the specified response and output destination.
   *
   * @param answer : user specified answer to the puzzle.
   * @param output : the {@link IGameOutput} object where the command's output will be written.
   */
  public AnswerCommand(String answer, IGameOutput output) {
    this.answer = answer;
    this.output = output;
  }

  /**
   * Executes the answer command by passing the user's response to the game model.
   *
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the response.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    output.append(model.answer(this.answer));
  }
}
