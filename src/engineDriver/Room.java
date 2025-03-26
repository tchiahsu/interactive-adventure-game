package engineDriver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {
  @JsonProperty("room_name")
  private String name;
  @JsonProperty("description")
  private String description;
  @JsonProperty("room_number")
  private int roomNumber;
  @JsonProperty("N")
  private int north;
  @JsonProperty("S")
  private int south;
  @JsonProperty("E")
  private int east;
  @JsonProperty("W")
  private int west;
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

  public int getRoomNumber() {
    return roomNumber;
  }

  public int getNorth() {
    return north;
  }

  public int getSouth() {
    return south;
  }

  public int getEast() {
    return east;
  }

  public int getWest() {
    return west;
  }

  public String getPuzzle() {
    return puzzle;
  }

  public String getMonster() {
    return monster;
  }

  public String getItems() {
    return items;
  }

  public String getFixture() {
    return fixtures;
  }

  public String getPicture() {
    return picture;
  }
}
