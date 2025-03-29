package model;

public class Player implements IPlayer {
  private static final int LEGENDARY_LOWER_BOUND = 700;
  private static final int MASTER_LOWER_BOUND = 400;
  private static final int SEASONED_LOWER_BOUND = 250;
  private static final int SLEEP_UPPER_BOUND = 0;
  private static final int WOOZY_UPPER_BOUND = 40;
  private static final int FATIGUED_UPPER_BOUND = 70;
  private String name;
  private int health;
  private int score;
  private final Inventory inventory;
  private HealthStatus healthStatus;
  private Rank rank;

  public Player() {
    this.health = 100;
    this.score = 0;
    this.inventory = new Inventory();
    this.healthStatus = HealthStatus.AWAKE;
    this.rank = Rank.NOVICE;
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
    if (this.score > LEGENDARY_LOWER_BOUND) {
      this.rank = Rank.LEGENDARY;
    } else if (this.score > MASTER_LOWER_BOUND) {
      this.rank = Rank.MASTER;
    } else if (this.score > SEASONED_LOWER_BOUND) {
      this.rank = Rank.SEASONED;
    }
    return this.rank.getRankTitle();
  }

  @Override
  public String getHealthStatus() {
    if (this.health <= SLEEP_UPPER_BOUND) {
      this.healthStatus = HealthStatus.SLEEP;
    } else if (this.health < WOOZY_UPPER_BOUND) {
      this.healthStatus = HealthStatus.WOOZY;
    } else if (this.health < FATIGUED_UPPER_BOUND) {
      this.healthStatus = HealthStatus.FATIGUED;
    }
    return this.healthStatus.getStatusMessage();
  }
}

