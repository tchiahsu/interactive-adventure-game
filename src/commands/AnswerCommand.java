package commands;

import java.io.IOException;

import model.IGameModel;

public class AnswerCommand implements ICommand {
  private final String answer;
  private final Appendable output;

  public AnswerCommand(String answer, Appendable output) {
    this.answer = answer;
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    output.append(model.answer(this.answer));
  }
}
