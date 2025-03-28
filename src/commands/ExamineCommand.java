package commands;

import java.io.IOException;

import model.IGameModel;

public class ExamineCommand implements ICommand {
  private final String object;
  private final Appendable output;

  public ExamineCommand(String object, Appendable output) {
    this.object = object;
    this.output = output;
  }

  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.examine(this.object));
  }
}
