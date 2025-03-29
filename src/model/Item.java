package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an item in the game.
 */
public class Item extends AbstractItem {

  @JsonProperty("max_uses")
  private int maxUses;
  @JsonProperty("uses_remaining")
  private int usesRemaining;
  @JsonProperty("value")
  private int value;
  @JsonProperty("when_used")
  private String whenUsedDescription;

  /**
   * Gets the maximum number of times the item can be used.
   * @return the maximum uses allowed for the item.
   */
  public int getMaxUses() {
    return maxUses;
  }

  /**
   * Gets the remaining number of uses for the item.
   * @return the number of uses left before the item is depleted.
   */
  public int getUsesRemaining() {
    return usesRemaining;
  }

  /**
   * Gets the value of the item.
   * @return the item's value, which may indicate rarity or effectiveness.
   */
  public int getValue() {
    return value;
  }

  /**
   * Gets the description of the item when used.
   * @return a string describing what happens when the item is used.
   */
  public String getWhenUsedDescription() {
    return whenUsedDescription;
  }

  /**
   * Reduces the number of uses remaining by one.
   * This method should be called each time the item is used.
   */
  public void reduceUse() {
    usesRemaining--;
  }
}
