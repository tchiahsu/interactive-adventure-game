package model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

  public String getNorth() {
    return north;
  }

  public String getSouth() {
    return south;
  }

  public String getEast() {
    return east;
  }

  public String getWest() {
    return west;
  }

  public String getPuzzleName() {
    return puzzle;
  }

  public String getMonsterName() {
    return monster;
  }

  public String getItems() {
    return items;
  }

  public String getFixtures() {
    return fixtures;
  }

  public String getPictureName() {
    return picture;
  }

}
