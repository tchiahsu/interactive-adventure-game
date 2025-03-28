package model;

public interface IGameModel {
  String move(String direction);
  String checkInventory();
  String look();
  String useItem(String itemName);
  String takeItem(String itemName);
  String dropItem(String itemName);
  String examine(String object);
  String answer(String answer);
}
