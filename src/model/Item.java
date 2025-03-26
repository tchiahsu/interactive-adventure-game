package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item extends AbstractItem {

  @JsonProperty("max_uses")
  private int maxUses;
  @JsonProperty("uses_remaining")
  private int usesRemaining;
  @JsonProperty("value")
  private int value;
  @JsonProperty("when_used")
  private String whenUsed;

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
}
