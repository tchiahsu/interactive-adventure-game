package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a monster in the game, which acts as an obstacle with attack capabilities.
 */
public class Monster extends AbstractObstacle {

  @JsonProperty("damage")
  private int damage;
  @JsonProperty("can_attack")
  private boolean canAttack;
  @JsonProperty("attack")
  private String attackMessage;

  /**
   * Gets the damage that the Monster can cause.
   */
  public int getDamage() {
    return damage;
  }

  /**
   * Checks if the monster can attack.
   * @return true if the monster can attack, false otherwise.
   */
  public boolean canAttack() {
    return canAttack;
  }

  /**
   * Gets the attack message associated with the monster.
   * @return the attack message displayed when the monster attacks.
   */
  public String getAttackMessage() {
    return attackMessage;
  }

  /**
   * Sets the picture file for the room with the path to the file.
   *
   * @param picture The picture file.
   */
  public void setPicture(String picture) {
    if (picture == null) {
      this.picture = "/data/Resources/generic-monster.png";
    } else {
      this.picture = "/data/Resources/" + picture;
    }
  }
}
