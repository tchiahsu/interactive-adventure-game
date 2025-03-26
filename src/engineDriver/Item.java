package engineDriver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
  @JsonProperty("name")
  private String name;
  @JsonProperty("description")
  private String description;
  @JsonProperty("weight")
  private int weight;
  @JsonProperty("max_uses")
  private int maxUses;
  @JsonProperty("uses_remaining")
  private int usesRemaining;
  @JsonProperty("value")
  private int value;
  @JsonProperty("when_used")
  private String whenUsed;
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

  public int getMaxUses() {
    return maxUses;
  }

  public int getUsesRemaining() {
    return usesRemaining;
  }

  public int getValue() {
    return value;
  }

  public String getWhenUsed() {
    return whenUsed;
  }

  public Picture getPicture() {
    return picture;
  }
}
