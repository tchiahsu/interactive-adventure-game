package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a fixture item in the game, extending from AbstractItem.
 */
public class Fixture extends AbstractItem {

  @JsonProperty("puzzle")
  private String puzzle;
  @JsonProperty("states")
  private String states;

  /**
   * Gets the puzzle associated with the fixture.
   * @return the puzzle in the fixture.
   */
  public String getPuzzle() {
    return puzzle;
  }

  /**
   * Gets the states associated with the fixture.
   * @return the states that describe the fixture's states.
   */
  public String getStates() {
    return states;
  }
}
