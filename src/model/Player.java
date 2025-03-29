package model;

public class Player implements IPlayer {
  private String name;
  private int health;
  private int score;
  private Inventory inventory;

  public Player() {
    health = 100;
    score = 0;
    inventory = new Inventory();
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }

  @Override
  public void increaseScore(int amount) {
    score += amount;
  }

  @Override
  public void decreaseScore(int amount) {
    score -= amount;
  }

  @Override
  public void decreaseHealth(int amount) {
    health += amount;
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
    if (this.score <= 50) {
      return "Novice Explorer";
    } else if (this.score <= 150) {
      return "Seasoned Pathfinder";
    } else if (this.score <= 200) {
      return "Master Voyager";
    } else {
      return "Legendary Trailblazer";
    }
  }
}

