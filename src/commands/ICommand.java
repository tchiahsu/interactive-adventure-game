package commands;

import java.io.IOException;

import model.IGameModel;

/**
 * Represents a command that can be executed within a game model.
 * Each implementing class will define the specific behavior.
 */
public interface ICommand {

  /**
   * The {@code execute} method triggers the methods in game model that will change the state of
   * the game based on the user input command.
   *
   * @param model : the {@link IGameModel} instance that whose state needs to change
   * @throws IOException when an I/O error occurs while appending the output.
   */
  void execute(IGameModel model) throws IOException;
}
