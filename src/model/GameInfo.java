package engineDriver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GameInfo {
  @JsonProperty("name")
  private String name;
  @JsonProperty("version")
  private String version;
  @JsonProperty("rooms")
  private List<Room> rooms;
  @JsonProperty("items")
  private List<Item> items;
  @JsonProperty("fixtures")
  private List<Fixture> fixtures;
  @JsonProperty("monsters")
  private List<Monster> monsters;
  @JsonProperty("puzzles")
  private List<Puzzle> puzzles;

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public List<Item> getItems() {
    return items;
  }

  public List<Fixture> getFixtures() {
    return fixtures;
  }

  public List<Monster> getMonsters() {
    return monsters;
  }

  public List<Puzzle> getPuzzles() {
    return puzzles;
  }
}
