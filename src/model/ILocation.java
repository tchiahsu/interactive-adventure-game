package model;

import java.util.Map;

/**
 * Represents a Room in the game.
 */
public interface ILocation extends IGameObject {

  /**
   * Gets the room number of the location.
   * @return the room number as a string.
   */
  String getRoomNumber();

  /**
   * Gets the available paths from the current location.
   * This method returns a map with direction keys
   * and the corresponding room numbers as values.
   * @return a map of directions to room numbers.
   */
  Map getPaths();

  /**
   * Gets the name of the puzzle located in the current location.
   *
   * @return the name of the puzzle as a string.
   */
  String getPuzzleName();

  /**
   * Gets the name of the monster located in the current location.
   * @return the name of the monster as a string.
   */
  String getMonsterName();

  /**
   * Gets a comma-separated list of item names located in the current location.
   * @return a string containing item names.
   */
  String getItemNames();

  /**
   * Gets a comma-separated list of fixture names located in the current location.
   * @return a string containing fixture names.
   */
  String getFixtureNames();

  /**
   * Gets the room number for a specific direction.
   * @param direction the direction to check.
   */
  String getPath(String direction);

  /**
   * Sets the name of the items in a location with the delimiter ",".
   * @param items : name of the items in the location
   */
  void setItemNames(String items);

  /**
   * Sets the name of the fixtures in a location with the delimiter ",".
   * @param name : name of the items in the location
   */
  void setFixtureNames(String name);
}
