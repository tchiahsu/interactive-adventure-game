package commands;

import java.io.IOException;

import EventHandler.IEventHandler;
import model.IGameModel;

/**
 * The {@code UseCommand} class represents a command for using an item in the adventure game.
 * It implements the {@link ICommand} interface.
 */
public class UseCommand implements ICommand {
  private final String item;
  private final IEventHandler output;

  /**
   * Construct an {@code UseCommand} object with the specified item and output destination.
   *
   * @param item : the item being used.
   * @param output : the {@link IEventHandler} object where the command's output will be written.
   */
  public UseCommand(String item, IEventHandler output) {
    this.item = item;
    this.output = output;
  }

  /**
   * Execute the use item command by passing the item to the game model.
   *
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the output.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.write(model.useItem(this.item));
  }
}
