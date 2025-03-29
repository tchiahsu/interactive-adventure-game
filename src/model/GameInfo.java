package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents game data parsed from a JSON file, organizing it into lists of game elements.
 */
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

  /**
   * Returns the name of the game.
   * @return the name of the game.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the version of the game.
   * @return the version of the game.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Returns the list of rooms in the game.
   * @return a list of Room objects.
   */
  public List<Room> getRooms() {
    return rooms;
  }

  /**
   * Returns the list of items in the game.
   * @return a list of Item objects.
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * Returns the list of fixtures in the game.
   * @return a list of Fixture objects.
   */
  public List<Fixture> getFixtures() {
    return fixtures;
  }

  /**
   * Returns the list of monsters in the game.
   * @return a list of Monster objects.
   */
  public List<Monster> getMonsters() {
    return monsters;
  }

  /**
   * Returns the list of puzzles in the game.
   * @return a list of Puzzle objects.
   */
  public List<Puzzle> getPuzzles() {
    return puzzles;
  }
}
