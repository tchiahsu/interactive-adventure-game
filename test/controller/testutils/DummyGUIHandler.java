package controller.testutils;

import java.io.IOException;

import eventhandler.IGuiEventHandler;

public class DummyGUIHandler implements IGuiEventHandler {
  private String action;

  @Override
  public void setCommandAction(String action) {
    this.action = action;
  }

  @Override
  public String read() throws IOException {
    return null;
  }

  @Override
  public void write(String s) throws IOException {}

  public String getAction() {
    return this.action;
  }
}
