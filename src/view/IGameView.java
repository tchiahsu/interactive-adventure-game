package view;

import java.io.IOException;

public interface IGameView {
  DescriptionPanel getDescriptionPanel();
  void startView();
  NavigationPanel getNavigationPanel();
  InventoryPanel getInventoryPanel();
  void updateView();
  void showPopUp(String s, String title) throws IOException;
  void showBlockedPopUp(String s) throws IOException;
  void showItemUsePopUp(String s);
  void showInputDialog(String title);
// void showItemUsePopUp(String s);
  void showAnswerPopUp(String s) throws IOException;

  String[] getRoomItems();
  void showSelectionDialog(String s, String[] roomItems);
  boolean isPlayerDead();
  void setMenuActionListener();
  void showTextPopUp(String s);


}
