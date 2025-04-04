package model;

/**
 * Represents the contract for an obstacle in the game.
 * This interface extends the IGameObject interface.
 */
public interface IObstacle extends IGameObject {

  /**
   * Checks if the obstacle is active.
   * @return true if the obstacle is active, false otherwise.
   */
  boolean isActive();

  /**
   * Checks if the obstacle affects the target.
   * @return true if the obstacle affects the target, false otherwise.
   */
  boolean affectsTarget();

  /**
   * Checks if the obstacle affects the player.
   * @return true if the obstacle affects the player, false otherwise.
   */
  boolean affectsPlayer();

  /**
   * Gets the solution required to overcome the obstacle.
   * @return the solution string for overcoming the obstacle.
   */
  String getSolution();

  /**
   * Gets the value of the obstacle.
   * @return the value of the obstacle, which may represent its difficulty, reward, or importance.
   */
  int getValue();

  /**
   * Gets the description of the effects of the obstacle.
   * @return a description of what happens when the obstacle is active.
   */
  String getActiveDescription();

  /**
   * Gets the target that the obstacle affects.
   * @return the target that the obstacle influences.
   */
  String getTarget();

  /**
   * Deactivates the obstacle, making it inactive.
   * This method sets the active status of the obstacle to false.
   */
  void deactivate();
}
