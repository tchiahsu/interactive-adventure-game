package commands;

import model.IGameModel;

public class ExamineCommand implements ICommand {
  private final String object;

  public ExamineCommand(String object) {
    this.object = object;
  }

  @Override
  public void execute(IGameModel model) {
    // System.out.println("You are EXAMINING an " + this.object + "\n");
    String output = model.examine(this.object);
  }
}
