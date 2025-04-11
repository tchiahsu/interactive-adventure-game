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

    Commands commandType = Commands.getEnum(this.getCommandAction());
    switch (commandType) {
      case ANSWER -> this.view.showAnswerPopUp(s);
      case EXAMINE -> this.view.showPopUp(s, "Inspecting...");
      case MOVE -> view.showBlockedPopUp(s);
      case SAVE, RESTORE -> view.showPopUp(s, "Loading...");
      case USE -> view.showItemUsePopUp(s);
      case DROP, INVENTORY, LOOK, TAKE, QUIT -> {}
      case null -> {}
    }

    view.updateView();
    if (view.isPlayerDead()) {
      view.showGameOverPopUp();
    }
  }

  @Override
  public void setCommandAction(String action) {
    this.commandAction = action;
  }

  private String getCommandAction() {
    return this.commandAction;
  }
}
