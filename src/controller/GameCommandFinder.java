package controller;

import java.util.Arrays;
import java.util.List;

import commands.AnswerCommand;
import commands.DropCommand;
import commands.ExamineCommand;
import commands.ICommand;
import commands.InventoryCommand;
import commands.LookCommand;
import commands.MoveCommand;
import commands.RestoreCommand;
import commands.SaveCommand;
import commands.TakeCommand;
import commands.UseCommand;

/**
 * The {@code GameCommandFinder} class is responsible for parsing and retrieving the correct
 * commands based on the user input.
 */
public class GameCommandFinder {
  private final Appendable output;

  /**
   * Constructs a {@code GameCommandFinder} class with the given output destination.
   *
   * @param output the {@code Appendable} object where command outputs will be written.
   */
  public GameCommandFinder(Appendable output) {
    this.output = output;
  }

  /**
   * Splits the user command into an action and a noun, and matches the action to the corresponding
   * {@code ICommand} instance.
   *
   * @param command : the user input string
   * @return an instance of {@code ICommand} corresponding to the user input
   * @throws IllegalArgumentException if the input is null or empty
   */
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
      case "SAVE" -> new SaveCommand(this.output);
      case "RESTORE" -> new RestoreCommand(this.output);
      default -> null; // Should never reach this point
    };
  }

  /**
   * Helper functions that splits the user input string into a verb and a noun.
   *
   * @param command user input string
   * @return a list of string with two elements
   *          - first element is the action
   *          - second element is the noun
   *         If the command only has one word then the array will only have one element.
   */
  private List<String> splitCommand(String command) {
    String[] splitCommand = command.split(" ", 2);
    return Arrays.asList(splitCommand);
  }
}
