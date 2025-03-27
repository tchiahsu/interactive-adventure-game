package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractObstacle implements IObstacle {

  @JsonProperty("name")
  protected String name;
  @JsonProperty("description")
  protected String description;
  @JsonProperty("active")
  protected boolean active;
  @JsonProperty("affects_target")
  protected boolean affectsTarget;
  @JsonProperty("affects_player")
  protected boolean affectsPlayer;
  @JsonProperty("solution")
  protected String solution;
  @JsonProperty("value")
  protected int value;
  @JsonProperty("effects")
  protected String activeDescription;
  @JsonProperty("target")
  protected String target;
  @JsonProperty("picture")
  protected String picture;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isActive() {
    return active;
  }

  public boolean affectsTarget() {
    return affectsTarget;
  }

  public boolean affectsPlayer() {
    return affectsPlayer;
  }

  public String getSolution() {
    return solution;
  }

  public int getValue() {
    return value;
  }

  public String getActiveDescription() {
    return activeDescription;
  }

  public String getTarget() {
    return target;
  }

  public String getPicture() {
    return picture;
  }

  public void deactivate() {
    active = false;
  };
}
