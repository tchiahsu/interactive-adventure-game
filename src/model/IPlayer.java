package model;

/**
 * Represents the contract for a Player in the game.
 * This interface defines the methods that a Player class must implement
 * to manage the player's health, score, inventory, name, rank, and health status.
 */
public interface IPlayer {

  /**
   * Gets the current health of the player.
   * @return the player's health value.
   */
  int getHealth();

  /**
   * Gets the current score of the player.
   * @return the player's score.
   */
  int getScore();

  /**
   * Gets the inventory of the player.
   * @return the player's inventory.
   */
  Inventory getInventory();

  /**
   * Increase the players score
   * @param amount : amount it should be increased.
   */
  void increaseScore(int amount);

  /**
   * Increases the player's score by a specified amount.
   * @param amount the amount to increase the score.
   */
  void decreaseScore(int amount);

  /**
   * Decreases the player's health by a specified amount.
   * @param amount the amount to decrease the health.
   */
  void decreaseHealth(int amount);

  /**
   * Sets the player's name.
   * @param name the name to assign to the player.
   */
  void setName(String name);

  /**
   * Gets the player's name.
   * @return the player's name.
   */
  String getName();

  /**
   * Determines and returns the player's rank based on their score.
   * @return the player's rank as a string.
   */
  String getRank();

  /**
   * Checks and returns the player's health status based on their current health.
   * @return the player's health status as a string.
   */
  String getHealthStatus();
}
