package model;

public interface IGameModel {
  void move(String direction);
//  void goNorth();
//  void goSouth();
//  void goEast();
//  void goWest();
  String checkInventory();
  void look();
  String useItem(String itemName);
  void takeItem(String itemName);
  void dropItem(String itemName);
  void examine(String itemName);
}
