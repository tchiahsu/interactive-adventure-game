package model;

/**
 * Defines the contract for game objects in the system.
 */
public interface IGameObject {

  /**
   * Gets the name of the game object.
   * @return the name of the game object.
   */
  String getName();

  /**
   * Gets the description of the game object.
   * @return the description of the game object.
   */
  String getDescription();

  /**
   * Gets the picture associated with the game object.
   * @return the picture of the game object.
   */
  String getPicture();
}
