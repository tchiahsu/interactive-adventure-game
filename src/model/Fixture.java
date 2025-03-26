package engineDriver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fixture extends AbstractItem {

  @JsonProperty("puzzle")
  private String puzzle;
  @JsonProperty("states")
  private String states;

  public String getPuzzle() {
    return puzzle;
  }

  public String getStates() {
    return states;
  }
}
