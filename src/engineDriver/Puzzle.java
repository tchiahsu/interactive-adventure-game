package engineDriver;

public class Puzzle {
  private String name;
  private boolean active;
  private boolean affectsTarget;
  private boolean affectsPlayer;
  private String solution;
  private int value;
  private String description;
  private String effects;
  private Room target;
  private Picture picture;

  public Puzzle() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isAffectsTarget() {
    return affectsTarget;
  }

  public void setAffectsTarget(boolean affectsTarget) {
    this.affectsTarget = affectsTarget;
  }

  public boolean isAffectsPlayer() {
    return affectsPlayer;
  }

  public void setAffectsPlayer(boolean affectsPlayer) {
    this.affectsPlayer = affectsPlayer;
  }

  public String getSolution() {
    return solution;
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEffects() {
    return effects;
  }

  public void setEffects(String effects) {
    this.effects = effects;
  }

  public Room getTarget() {
    return this.target;
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
}
