package model;

public interface IPlayer {
  int getHealth();
  int getScore();
  Inventory getInventory();
  void increaseScore(int amount);
  void decreaseHealth(int amount);
}
