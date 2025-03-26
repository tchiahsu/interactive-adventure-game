package engineDriver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fixture {
  @JsonProperty("name")
  private String name;
  @JsonProperty("description")
  private String description;
  @JsonProperty("weight")
  private int weight;
  @JsonProperty("puzzle")
  private String puzzle;
  @JsonProperty("states")
  private String states;
  @JsonProperty("picture")
  private Picture picture;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getWeight() {
    return weight;
  }

  public String getStates() {
    return states;
  }

  public String getPuzzle() {
    return puzzle;
  }

  public Picture getPicture() {
    return picture;
  }
}
