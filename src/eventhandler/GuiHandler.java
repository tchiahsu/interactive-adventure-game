package eventhandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import controller.Commands;
import view.IGameView;

public class GuiHandler implements IGuiEventHandler {
  private final IGameView view;
  private String commandAction = "N";

  public GuiHandler(IGameView view) {
    this.view = view;
  }

  @Override
  public String read() throws IOException {
    throw new UnsupportedEncodingException("Input is managed by GUI");
  }

  @Override
  public void write(String s) throws IOException {

    String testDescription = "This is a description for testing"; //to be deleted
    String testImage = "/data/Resources/lamp.png"; //to be deleted
    String testItem = "LAMP";

    Commands commandType = Commands.getEnum(this.getCommandAction());
    switch (commandType) {
      case MOVE, LOOK -> {
        view.updateView();
        if (s.contains("You cannot go in")) {
          view.showPopUp(s);
        }
      }
      case INVENTORY -> System.out.println("INVENTORY!");
      case USE -> view.getInventoryPanel().getDescriptionBox(view.getInventoryPanel().getUseBtn(), s, "USE", testImage);
      case TAKE -> view.updateView();
      case DROP -> view.getInventoryPanel().getDescriptionBox(view.getInventoryPanel().getDropBtn(), s, "DROP", testImage);
      case EXAMINE -> System.out.println("EXAMINE!");
      case ANSWER -> System.out.println("ANSWER!");
      case SAVE, RESTORE -> view.showPopUp(s);
      case QUIT -> System.out.println("QUIT!");
      case null -> System.out.println("INVALID");
    }
  }

  public void setCommandAction(String action) {
    this.commandAction = action;
  }

  private String getCommandAction() {
    return this.commandAction;
  }
}
