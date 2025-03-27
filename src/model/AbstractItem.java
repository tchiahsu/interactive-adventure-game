package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractItem implements IItem {

  @JsonProperty("name")
  protected String name;
  @JsonProperty("description")
  protected String description;
  @JsonProperty("weight")
  protected int weight;
  @JsonProperty("picture")
  protected String picture;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getWeight() {
    return weight;
  }

  public String getPicture() {
    return picture;
  }
}
