package eventhandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import controller.Commands;
import view.IGameView;

/**
 * The class implements the IGuiEventHandler class. It serves as the event handler
 * for a GUI-based game interaction.
 */
public class GuiHandler implements IGuiEventHandler {
  private final IGameView view;
  private String commandAction = "N";

  /**
   * Constructs a GUI handler object.
   * @param view : the view that this handler will be interacting with.
   */
  public GuiHandler(IGameView view) {
    this.view = view;
  }

  /**
   * This method is not supported by the GUI since there are no inputs. Interaction
   * is through click events.
   * @return nothing
   * @throws IOException throws unsupported encoding exception.
   */
  @Override
  public String read() throws IOException {
    throw new UnsupportedEncodingException("Input is managed by GUI");
  }

  /**
   * Displays the given string in the correct GUI element based on the command
   * that triggered the event.
   * @param s : the string that need to be outputted.
   * @throws IOException if there is an error displaying a message.
   */
  @Override
  public void write(String s) throws IOException {

    Commands commandType = Commands.getEnum(this.getCommandAction());
    switch (commandType) {
      case ANSWER -> this.view.showAnswerPopUp(s);
      case EXAMINE -> this.view.showPopUp(s, "Inspecting...");
      case MOVE -> this.view.showBlockedPopUp(s);
      case SAVE, RESTORE -> this.view.showPopUp(s, "Loading...");
      case TAKE -> this.view.showFullInventoryPopUp(s);
      case USE -> this.view.showItemUsePopUp(s);
      case DROP, INVENTORY, LOOK, QUIT -> { }
      case null -> { }
    }

    view.updateView();
    if (view.isPlayerAsleep()) {
      view.showGameOverPopUp();
    }
  }

  /**
   * Sets the current command action, which is used to determine where the associated
   * output will be displayed.
   * @param action : command that was executed.
   */
  @Override
  public void setCommandAction(String action) {
    this.commandAction = action;
  }

  /**
   * Gets the current command action.
   * @return the current command action.
   */
  private String getCommandAction() {
    return this.commandAction;
  }
}
