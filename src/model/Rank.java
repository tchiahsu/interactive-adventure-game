package model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents the rank of a player in the adventure game.
 * The rank is determined by the amount of points collected by the player as they played
 * the game.
 */
public enum Rank {
  NOVICE("Novice Explorer"),
  SEASONED("Seasoned Pathfinder"),
  MASTER("Master Voyager"),
  LEGENDARY("Legendary Trailblazer");
  private final String rank;

  /**
   * Creates a rank enum that represents the rank of the player.
   * @param rank : rank of the player
   */
  Rank(String rank) {
    this.rank = rank;
  }

  /**
   * Get the rank title for the player.
   * @return rank title
   */
  public String getRankTitle() {
    return this.rank;
  }

  /**
   * Parses a string representation of a rank and returns the Rank enum.
   *
   * @param rank The string representation of a rank.
   * @return The rank enum corresponding the string representation.
   * @throws IllegalArgumentException if the provided rank string does not match any valid rank.
   */
  @JsonCreator
  public static Rank parseRank(String rank) {
    for (Rank r : Rank.values()) {
      if (r.rank.equalsIgnoreCase(rank)) {
        return r;
      }
    }

    throw new IllegalArgumentException("Unknown rank: " + rank);
  }
}
