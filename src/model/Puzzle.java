package model;

/**
 * Class that represents a Puzzle in the Room.
 */
public class Puzzle extends AbstractObstacle {

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
