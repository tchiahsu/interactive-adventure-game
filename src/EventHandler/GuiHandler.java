package EventHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import view.IGameView;

public class GuiHandler implements IEventHandler {
  private final IGameView view;

  public GuiHandler(IGameView view) {
    this.view = view;
  }

  @Override
  public String read() throws IOException {
    throw new UnsupportedEncodingException("Input is managed by GUI");
  }

  @Override
  public void write(String s) throws IOException {
    view.getDescriptionPanel().updateDescriptionPanel(s);
  }
}
