package commands;

import java.io.IOException;

import model.IGameModel;

// Once we have the descriptions, this should return a STRING
public interface ICommand {
  void execute(IGameModel model) throws IOException;
}
