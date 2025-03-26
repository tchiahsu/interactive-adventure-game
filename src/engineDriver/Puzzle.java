package engineDriver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Puzzle {
  @JsonProperty("name")
  private String name;
  @JsonProperty("description")
  private String description;
  @JsonProperty("active")
  private boolean active;
  @JsonProperty("affects_target")
  private boolean affectsTarget;
  @JsonProperty("affects_player")
  private boolean affectsPlayer;
  @JsonProperty("solution")
  private String solution;
  @JsonProperty("value")
  private int value;
  @JsonProperty("effects")
  private String effects;
  @JsonProperty("target")
  private String target;
  @JsonProperty("picture")
  private String picture;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isActive() {
    return active;
  }

  public boolean AffectsTarget() {
    return affectsTarget;
  }

  public boolean AffectsPlayer() {
    return affectsPlayer;
  }

  public String getSolution() {
    return solution;
  }

  public int getValue() {
    return value;
  }

  public String getEffects() {
    return effects;
  }

  public String getTarget() {
    return this.target;
  }

  public String getPicture() {
    return picture;
  }
}
