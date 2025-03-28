package commands;

import java.io.IOException;

import model.IGameModel;

/**
 * The {@code TakeCommand} class represents a command for taking an item in the adventure game.
 * It implements the {@link ICommand} class.
 */
public class TakeCommand implements ICommand {
  private final String item;
  private final Appendable output;

  /**
   * Constructs an {@code TakeCommand} object with the specified item and output avenue.
   * @param item : the item that is being taken
   * @param output : the {@link Appendable} object where the command's output will be written.
   */
  public TakeCommand(String item, Appendable output) {
    this.item = item;
    this.output = output;
  }

  /**
   * Executes the take command by passing the item to the game model.
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the output.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    output.append(model.takeItem(this.item));
  }
}
