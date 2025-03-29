package controller;

import java.util.List;

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

  Commands(List<String> aliases, boolean requiresArgument) {
    this.aliases = aliases;
    this.requiresArgument = requiresArgument;
  }

  public boolean checkMatch(String input) {
    return this.aliases.contains(input);
  }

  public boolean getRequiresArgument() {
    return this.requiresArgument;
  }

  public static Commands getEnum(String input) {
    for (Commands command : Commands.values()) {
      if (command.checkMatch(input)) {
        return command;
      }
    }
    return null;
  }

}
