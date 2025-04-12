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
  public DescriptionPanel getDescriptionPanel() {
    return null;
  }

  @Override
  public void startView() {}

  @Override
  public NavigationPanel getNavigationPanel() {
    return null;
  }

  @Override
  public InventoryPanel getInventoryPanel() {
    return null;
  }

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
  public String[] getRoomItems() {
    return new String[0];
  }

  @Override
  public void showSelectionDialog(String s, String[] roomItems) {}

  @Override
  public boolean isPlayerDead() {
    return playerAsleep;
  }

  @Override
  public void setMenuActionListener() {}

  @Override
  public void showTextPopUp(String s) {}

  @Override
  public void showGameOverPopUp() throws IOException {
    gameOverPopUp = true;
  }

  @Override
  public void showFullInventoryPopUp(String s) {
    popUpText = s;
  }
}
