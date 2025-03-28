package commands;

import model.IGameModel;

public class AnswerCommand implements ICommand {
  private final String answer;

  public AnswerCommand(String answer) {
    this.answer = answer;
  }

  @Override
  public void execute(IGameModel model) {
    // System.out.println("This is your answer: " + this.answer + "!\n");
    String output = model.answer(this.answer);
    System.out.println(output);
  }
}
