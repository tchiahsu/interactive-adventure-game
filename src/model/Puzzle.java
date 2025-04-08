package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents a Puzzle in the Room.
 */
public class Puzzle extends AbstractObstacle {
  @JsonProperty("picture")
  protected String picture;

  /**
   * Gets the picture associated with the obstacle.
   * @return the picture URL or file path for the obstacle.
   */
  public String getPicture() {
    return picture;
  }

  /**
   * Sets the picture file for the room with the path to the file.
   *
   * @param picture The picture file.
   */
  public void setPicture(String picture) {
    if (picture == null) {
      this.picture = "/data/Resources/generic_puzzle.png";
    } else {
      this.picture = "/data/Resources/" + picture;
    }
  }
}
