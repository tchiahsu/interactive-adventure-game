package model;

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
  private String puzzle;
  @JsonProperty("monster")
  private String monster;
  @JsonProperty("items")
  private String items;
  @JsonProperty("fixtures")
  private String fixtures;
  @JsonProperty("picture")
  private String picture;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public String getPuzzleName() {
    return puzzle;
  }

  public String getMonsterName() {
    return monster;
  }

  public String getItemNames() {
    return items;
  }

  public String getFixtureNames() {
    return fixtures;
  }

  public String getPictureName() {
    return picture;
  }

  public Map<String, String> getPaths() {
    Map<String, String> path = new HashMap<>();
    if (north != null) path.put("north", north);
    if (south != null) path.put("south", south);
    if (east != null) path.put("east", east);
    if (west != null) path.put("west", west);
    return path;
  }

  /**
   * Method that returns the path for a given direction
   */
  public String getPath(String direction) {
    return getPaths().get(direction.toUpperCase());
  }

  public void setItems(String items) {
    this.items = items;
  }

}
