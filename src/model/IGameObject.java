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
   * Sets the name of the game object.
   * @param name The name of the game object.
   */
  void setName(String name);

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

  /**
   * Sets the picture associated with the game object.
   * @param picture The picture file.
   */
  public void setPicture(String picture);

  /**
   * Removes the path to the picture for serializing the image file to a JSON.
   * @return The picture file with the path removed.
   */
  public String getPictureFileName();
}
