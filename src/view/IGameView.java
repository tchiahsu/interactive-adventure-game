package view;

public interface IGameView {
  DescriptionPanel getDescriptionPanel();
  void startView();

  InventoryPanel getInventoryPanel();
  void updateView();
  void showPopUp(String s);
}
