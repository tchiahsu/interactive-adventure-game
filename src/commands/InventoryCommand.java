package commands;

import java.io.IOException;

import model.IGameModel;

/**
 * The {@code InventoryCommand} class represents a command for checking the players inventory
 * in the adventure game.
 * It implements the {@link ICommand} class.
 */
public class InventoryCommand implements ICommand {
  private final Appendable output;

  /**
   * Constructs an {@code InventoryCommand} object with the specified output destination.
   *
   * @param output : the {@link Appendable} object where the command's output will be written.
   */
  public InventoryCommand(Appendable output) {
    this.output = output;
  }

  /**
   * Executes the inventory command by accessing the inventory of the player in the game model.
   *
   * @param model : the {@link IGameModel} instance that processes the answer.
   * @throws IOException when an I/O error occurs while appending the output.
   */
  @Override
  public void execute(IGameModel model) throws IOException {
    this.output.append(model.checkInventory());
  }
}
