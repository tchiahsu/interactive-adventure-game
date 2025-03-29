package model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents the health status of the player in the adventure game.
 */
public enum HealthStatus {
  SLEEP("Your health has dropped to the sleep zone. \nNighty-night\nGame Over!\n"),
  WOOZY("Your health is very low! And you're woozy\n"),
  FATIGUED("Adventuring has made you very tired! Your health is low!\n"),
  AWAKE("You are healthy and wide awake.\n");
  private final String healthStatus;

  /**
   * Creates a HealthStatus enum that represents the health status of the player in the adventure
   * game.
   * @param status : status of the player
   */
  HealthStatus(String status) {
    this.healthStatus = status;
  }

  /**
   * Get the description for the specific health status.
   * @return description of health status
   */
  public String getStatusMessage() {
    return this.healthStatus;
  }

  /**
   * Parses a string representation of the health status and returns
   * the HealthStatus enum.
   *
   * @param healthStatus The string representation of the health status.
   * @return The rank enum corresponding the string representation.
   * @throws IllegalArgumentException if the provided rank string does not match any valid rank.
   */
  @JsonCreator
  public static HealthStatus parseHealthStatus(String healthStatus) {
    for (HealthStatus h : HealthStatus.values()) {
      if (h.healthStatus.equalsIgnoreCase(healthStatus)) {
        return h;
      }
    }

    throw new IllegalArgumentException("Unknown health status: " + healthStatus);
  }
}
