package controller.testutils;

import model.IPlayer;
import model.Inventory;

public class DummyPlayer implements IPlayer {
  private String name;
  private int health = 100;
  private int score = 0;


  @Override
  public int getHealth() {
    return this.health;
  }

  @Override
  public int getScore() {
    return this.score;
  }

  @Override
  public Inventory getInventory() {
    return null;
  }

  @Override
  public void setInventory(Inventory inventory) {
  }

  @Override
  public void increaseScore(int amount) {

  }

  @Override
  public void decreaseScore(int amount) {
    this.health = this.health + amount;
  }

  @Override
  public void decreaseHealth(int amount) {
    this.health = this.health - amount;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getRank() {
    return "";
  }

  @Override
  public String getHealthStatus() {
    return "";
  }
}
