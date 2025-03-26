package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Monster extends AbstractObstacle {

  @JsonProperty("damage")
  private int damage;
  @JsonProperty("can_attack")
  private boolean canAttack;
  @JsonProperty("attack")
  private String attack;

  public int getDamage() {
    return damage;
  }

  public boolean canAttack() {
    return canAttack;
  }

  public String attack() {
    return attack;
  }
}
