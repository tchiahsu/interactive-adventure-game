package eventhandler;

import java.io.IOException;

import view.DescriptionPanel;
import view.IGameView;
import view.InventoryPanel;
import view.NavigationPanel;

public class DummyGameView implements IGameView {
  public String popUpText = "";
  public String popUpTitle = "";
  public boolean updateViewCalled = false;
  public boolean gameOverPopUp = false;
  public boolean playerAsleep = false;

  @Override
  public void startView() {}


  @Override
  public void updateView() {
    updateViewCalled = true;
  }

  @Override
  public void showPopUp(String s, String title) throws IOException {
    popUpText = s;
    popUpTitle = title;
  }

  @Override
  public void showBlockedPopUp(String s) throws IOException {
    popUpText = s;
    popUpTitle = "Blocked";
  }

  @Override
  public void showItemUsePopUp(String s) {
    popUpText = s;
    popUpTitle = "Item Used!";
  }

  @Override
  public void showInputDialog(String title) {
    popUpTitle = "Input Dialog!";
  }

  @Override
  public void showAnswerPopUp(String s) throws IOException {
    popUpText = s;
    popUpTitle = "Answer!";
  }

  @Override
  public boolean isPlayerAsleep() {
    return playerAsleep;
  }

  @Override
  public void setMenuActionListener() {

  }

  @Override
  public void showGameOverPopUp() throws IOException {
    gameOverPopUp = true;
  }

  @Override
  public void showFullInventoryPopUp(String s) {
    popUpText = s;
  }
}
