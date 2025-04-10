package view;

import java.util.List;

public interface IGameView {
  DescriptionPanel getDescriptionPanel();
  void startView();
  NavigationPanel getNavigationPanel();
  InventoryPanel getInventoryPanel();
  void updateView();
  void showPopUp(String s);

  void showBlockedPopUp(String s);
  void showItemUsePopUp(String s);
  void showSelectionDialog(String s, String[] roomItems);
  String[] getRoomItems();


}
