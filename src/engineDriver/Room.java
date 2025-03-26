package engineDriver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {
  @JsonProperty("room_name")
  private String roomName;
  @JsonProperty("room_number")
  private int roomNumber;
  @JsonProperty("description")
  private String description;
  @JsonProperty("N")
  private int N;
  @JsonProperty("S")
  private int S;
  @JsonProperty("E")
  private int E;
  @JsonProperty("W")
  private int W;
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

  public String getRoomName() {
    return roomName;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public String getDescription() {
    return description;
  }

  public int getN() {
    return N;
  }

  public int getS() {
    return S;
  }

  public int getE() {
    return E;
  }

  public int getW() {
    return W;
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
