package commands;

import model.IGameModel;

public interface ICommand {
  void execute(IGameModel model);
}
