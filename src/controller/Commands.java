package controller;

import java.util.List;

/**
 * Represents the Command that the user can input to play the adventure game.
 */
public enum Commands {
  MOVE(List.of("N", "S", "E", "W", "NORTH", "SOUTH", "EAST", "WEST"), false),
  INVENTORY(List.of("I", "INVENTORY"), false),
  LOOK(List.of("L", "LOOK"), false),
  USE(List.of("U", "USE"), true),
  TAKE(List.of("T", "TAKE"), true),
  DROP(List.of("D", "DROP"), true),
  EXAMINE(List.of("X", "EXAMINE"), true),
  ANSWER(List.of("A", "ANSWER"), true),
  SAVE(List.of("S", "SAVE"), false),
  RESTORE(List.of("R", "RESTORE"), false),
  QUIT(List.of("Q", "QUIT"), false);

  private final List<String> aliases;
  private final boolean requiresArgument;

  /**
   * Creates a Command enum that represents the commands the player can use during the game.
   * It takes into consideration all the possible aliases for the commands and whether they need
   * to be inputted with an argument or not.
   * @param aliases : all possible commands
   * @param requiresArgument : true if it needs an argument, false otherwise
   */
  Commands(List<String> aliases, boolean requiresArgument) {
    this.aliases = aliases;
    this.requiresArgument = requiresArgument;
  }

  /**
   * Checks if the command matches any of the specified aliases for the commands.
   * @param input : string command
   * @return true if command is valid, false otherwise
   */
  public boolean checkMatch(String input) {
    return this.aliases.contains(input);
  }

  /**
   * Determines if the command needs another argument or not.
   * @return true if it does, false otherwise
   */
  public boolean getRequiresArgument() {
    return this.requiresArgument;
  }

  /**
   * Static method for the enum class. It gets the enum that is associated with the alias that is
   * being passed. It checks that the action inputted by the user matches any of hte aliases and
   * returns the enum with that alias.
   * @param input : user input command
   * @return enum with given alias
   */
  public static Commands getEnum(String input) {
    for (Commands command : Commands.values()) {
      if (command.checkMatch(input)) {
        return command;
      }
    }
    return null;
  }
}
