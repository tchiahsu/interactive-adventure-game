package engineDriver;

public class Monster {
  private String name;
  private String description;
  private int damage;
  private boolean canAttack;
  private String attack;
  private boolean active;
  private int value;
  private String solution;
  private Room target;
  private Picture picture;
  private String effects;

  public Monster() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public boolean isCanAttack() {
    return canAttack;
  }

  public void setCanAttack(boolean canAttack) {
    this.canAttack = canAttack;
  }

  public String getAttack() {
    return attack;
  }

  public void setAttack(String attack) {
    this.attack = attack;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getSolution() {
    return solution;
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public Room getTarget() {
    return target;
  }

  public void setTarget(Room target) {
    this.target = target;
  }

  public Picture getPicture() {
    return picture;
  }

  public void setPicture(Picture picture) {
    this.picture = picture;
  }

  public String getEffects() {
    return effects;
  }

  public void setEffects(String effects) {
    this.effects = effects;
  }
}
