package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

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

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = (name != null) ? name.toUpperCase() : null;
  }

  public String getDescription() {
    return description;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public String getPuzzleName() {
    return puzzleName;
  }

  public String getMonsterName() {
    return monsterName;
  }

  public String getItemNames() {
    return itemNames;
  }

  public void setItemNames(String itemNames) {
    this.itemNames = (itemNames != null) ? itemNames.toUpperCase() : null;
  }

  public String getFixtureNames() {
    return fixtureNames;
  }

  public String getPicture() {
    return picture;
  }

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
   * Method that returns the path for a given direction
   */
  public String getPath(String direction) {
    return getPaths().get(direction.toUpperCase());
  }


}
