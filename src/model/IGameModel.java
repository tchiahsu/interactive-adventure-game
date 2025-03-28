package model;

public interface IGameModel {
  void move(String direction);
  String checkInventory();
  String look();
  String useItem(String itemName);
  String takeItem(String itemName);
  String dropItem(String itemName);
  void examine(String object);
  String answer(String answer);
}
