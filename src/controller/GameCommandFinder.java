package controller;

import java.util.Arrays;
import java.util.List;

import commands.ICommand;
import commands.*;

public class GameCommandFinder {
  private final Appendable output;

  public GameCommandFinder(Appendable output) {
    this.output = output;
  }

  public ICommand getCommand(String command) throws IllegalArgumentException {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty!");
    }

    List<String> commandTokens = splitCommand(command);
    String action = commandTokens.getFirst().toUpperCase();
    String noun = commandTokens.size() > 1 ? commandTokens.get(1) : null;

    return switch (action) {
      case "N", "S", "E", "W", "NORTH", "SOUTH", "EAST", "WEST"
          -> new MoveCommand(action, this.output);
      case "I", "INVENTORY" -> new InventoryCommand(this.output);
      case "L", "LOOK" -> new LookCommand(this.output);
      case "U", "USE" -> new UseCommand(noun, this.output);
      case "T", "TAKE" -> new TakeCommand(noun, this.output);
      case "D", "DROP" -> new DropCommand(noun, this.output);
      case "X", "EXAMINE" -> new ExamineCommand(noun, this.output);
      case "A", "ANSWER" -> new AnswerCommand(noun, this.output);
      default -> null; // Should never reach this point
    };
  }

  private List<String> splitCommand(String command) {
    String[] splitCommand = command.split(" ", 2);
    return Arrays.asList(splitCommand);
  }
}
