package model;

/**
 * Represents the contract for an item in the game.
 * Any class implementing this interface must define a method to get the weight of the item.
 * This interface extends the IGameObject interface, inheriting common behaviors for game objects.
 */
public interface IItem extends IGameObject {

  /**
   * Gets the weight of the item.
   * @return the weight of the item.
   */
  int getWeight();
}
