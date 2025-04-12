package model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Room in a game or system.
 * This class contains information about the room, such as its name, description,
 * puzzle, monster, items, fixtures, and possible paths to other rooms.
 */
public class Room implements ILocation {

  @JsonProperty("room_name")
  private String name;
  @JsonProperty("description")
  private String description;
  @JsonProperty("room_number")
  private String roomNumber;
  @JsonProperty("N")
  private String north;
  @JsonProperty("S")
  private String south;
  @JsonProperty("E")
  private String east;
  @JsonProperty("W")
  private String west;
  @JsonProperty("puzzle")
  private String puzzleName;
  @JsonProperty("monster")
  private String monsterName;
  @JsonProperty("items")
  private String itemNames;
  @JsonProperty("fixtures")
  private String fixtureNames;
  @JsonProperty("picture")
  private String picture;

  /**
   * Method that returns the name of the Room.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the room.
   * The name is converted to uppercase when set.
   * @param name the name to assign to the room. If null, the name will be set to null.
   */
  protected void setName(String name) {
    this.name = (name != null) ? name.toUpperCase() : null;
  }

  /**
   * Returns the description of the room.
   * @return the description of the room.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the room's number.
   * @return the room number.
   */
  public String getRoomNumber() {
    return roomNumber;
  }

  /**
   * Returns the name of the puzzle located in the room.
   * @return the name of the puzzle.
   */
  public String getPuzzleName() {
    return puzzleName;
  }

  /**
   * Sets the name of the puzzle in the room.
   */
  public void setPuzzleName(String puzzleName) {
    this.puzzleName = (puzzleName != null) ? puzzleName.toUpperCase() : null;
  }

  /**
   * Returns the name of the monster located in the room.
   * @return the name of the monster.
   */
  public String getMonsterName() {
    return monsterName;
  }

  /**
   * Sets the name of the monster in the room.
   */
  public void setMonsterName(String monsterName) {
    this.monsterName = (monsterName != null) ? monsterName.toUpperCase() : null;
  }

  /**
   * Returns a comma-separated list of item names in the room.
   * @return a list of item names.
   */
  public String getItemNames() {
    return itemNames;
  }

  /**
   * Sets the item names in the room.
   * The item names are converted to uppercase before being set.
   * @param itemNames a comma-separated list of item names to set.
   */
  public void setItemNames(String itemNames) {
    this.itemNames = (itemNames != null) ? itemNames.toUpperCase() : "";
  }

  /**
   * Returns a comma-separated list of fixture names in the room.
   * @return a list of fixture names in Room.
   */
  public String getFixtureNames() {
    return fixtureNames;
  }

  /**
   * Sets the names of the fixtures in the room.
   *
   * @param fixtureNames The names of the fixtures in the room.
   */
  public void setFixtureNames(String fixtureNames) {
    this.fixtureNames = (fixtureNames != null) ? fixtureNames.toUpperCase() : "";
  }

  /**
   * Sets the picture file for the room with the path to the file.
   *
   * @param picture The picture file.
   */
  public void setPicture(String picture) {
    if (picture == null) {
      this.picture = "/data/Resources/generic_location.png";
    } else {
      this.picture = "/data/Resources/" + picture;
    }
  }

  /**
   * Returns the picture associated with the room.
   * @return the picture of the room.
   */
  public String getPicture() {
    return picture;
  }

  @JsonGetter("picture")
  public String getPictureFileName() {
    // Remove the path for serializing
    return picture == null ? null : picture.replace("/data/Resources/", "");
  }

  /**
   * Method that creates a map  of the paths.
   * @return a map of direction (as keys) to room number (as values).
   */
  @JsonIgnore
  public Map<String, String> getPaths() {
    Map<String, String> path = new HashMap<>();
    if (north != null) path.put("NORTH", north);
    if (south != null) path.put("SOUTH", south);
    if (east != null) path.put("EAST", east);
    if (west != null) path.put("WEST", west);
    return path;
  }

  /**
   * Method that returns the room number for a given direction.
   * @param direction the direction in which to check for a room
   * @return the name of the room in the specified direction
   */
  public String getPath(String direction) {
    return getPaths().get(direction.toUpperCase());
  }
}
