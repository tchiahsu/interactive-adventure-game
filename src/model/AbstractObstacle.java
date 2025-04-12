package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an abstract obstacle in the game.
 * This class defines common properties and methods for Puzzles and Monsters.
 */
public abstract class AbstractObstacle implements IObstacle {

  @JsonProperty("name")
  protected String name;
  @JsonProperty("description")
  protected String description;
  @JsonProperty("active")
  protected boolean activeStatus;
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

  /**
   * Gets the name of the obstacle.
   * @return the name of the obstacle.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the obstacle. The name is converted to uppercase when set.
   * @param name the name to assign to the obstacle. If null, the name will be set to null.
   */
  public void setName(String name) {
    this.name = (name != null) ? name.toUpperCase() : null;
  }

  /**
   * Gets the description of the obstacle.
   * @return  the description of the obstacle.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Checks if the obstacle is active.
   * @return true if the obstacle is active, false otherwise.
   */
  public boolean isActive() {
    return activeStatus;
  }

  /**
   * Checks if the obstacle affects the target.
   * @return true if the obstacle affects the target, false otherwise.
   */
  public boolean affectsTarget() {
    return affectsTarget;
  }

  /**
   * Checks if the obstacle affects the player.
   * @return true if the obstacle affects the player, false otherwise.
   */
  public boolean affectsPlayer() {
    return affectsPlayer;
  }

  /**
   * Gets the solution required to overcome the obstacle.
   * @return the solution string for overcoming the obstacle.
   */
  public String getSolution() {
    return solution;
  }

  /**
   * Gets the value of the obstacle.
   * @return the value of the obstacle.
   */
  public int getValue() {
    return value;
  }

  /**
   * Gets the description of the effects of the obstacle.
   * @return a description of what happens when the obstacle is active.
   */
  public String getActiveDescription() {
    return activeDescription;
  }

  /**
   * Gets the target that the obstacle affects.
   * @return the target that the obstacle influences.
   */
  public String getTarget() {
    return target;
  }

  /**
   * Deactivates the obstacle, making it inactive.
   * This method sets the active status of the obstacle to false.
   */
  public void deactivate() {
    activeStatus = false;
  }

  /**
   * Gets the picture associated with the obstacle.
   * @return the picture URL or file path for the obstacle.
   */
  public String getPicture() {
    return picture;
  }

  /**
   * Removes the path to the picture for serializing the image file to a JSON.
   * @return The picture file with the path removed.
   */
  @JsonGetter("picture")
  public String getPictureFileName() {
    // Remove the path for serialization
    return picture == null ? null : picture.replace("/data/Resources/", "");
  }
}
