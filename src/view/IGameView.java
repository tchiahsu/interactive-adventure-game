package view;

import java.io.IOException;
import java.util.List;

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
  void showPopUpAnswer(String s) throws IOException;

  String[] getRoomItems();

  void showSelectionDialog(String s, String[] roomItems);
//  String[] getRoomItems();


}
