package model;

/**
 * Represents a Player in the game.
 */
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

  /**
   * Constructs a new Player instance with default attributes.
   * Default health is set to 100, score to 0, and rank to NOVICE.
   */
  public Player() {
    this.health = 100;
    this.score = 0;
    this.inventory = new Inventory();
    this.healthStatus = HealthStatus.AWAKE;
    this.rank = Rank.NOVICE;
  }

  /**
   * Gets the current health of the player.
   * @return the player's health value.
   */
  @Override
  public int getHealth() {
    return health;
  }

  /**
   * Gets the current score of the player.
   * @return the player's score.
   */
  @Override
  public int getScore() {
    return score;
  }

  /**
   * Gets the inventory of the player.
   * @return the player's inventory.
   */
  @Override
  public Inventory getInventory() {
    return inventory;
  }

  /**
   * Increases the player's score by a specified amount.
   * @param amount the amount to increase the score.
   */
  @Override
  public void increaseScore(int amount) {
    score += amount;
  }

  /**
   * Decreases the player's score by a specified amount.
   * @param amount the amount to decrease the score.
   */
  @Override
  public void decreaseScore(int amount) {
    score -= amount;
  }

  /**
   * Decreases the player's health by a specified amount.
   * @param amount the amount to decrease the health.
   */
  @Override
  public void decreaseHealth(int amount) {
    health += amount;
  }

  /**
   * Sets the player's name.
   * @param name the name to assign to the player.
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the player's name.
   * @return the player's name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Determines and returns the player's rank based on their score.
   * Updates the player's rank accordingly.
   * @return the player's rank as a string.
   */
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

  /**
   * Checks the player's health status based on their current health and
   * updates the player's health status accordingly.
   * @return the player's health status as a string.
   */
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

