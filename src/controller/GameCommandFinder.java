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
import io.IOHandler;

/**
 * The {@code GameCommandFinder} class is responsible for parsing and retrieving the correct
 * commands based on the user input.
 */
public class GameCommandFinder {
  private final IOHandler io;

  /**
   * Constructs a {@code GameCommandFinder} class with the given output destination.
   *
   * @param output the {@code Appendable} object where command outputs will be written.
   */
  public GameCommandFinder(IOHandler output) {
      this.io = output;
    }

    /**
     * Splits the user command into an action and a noun, and matches the action to the corresponding
     * {@code Command} enum and uses the enum to trigger the corresponding {@link ICommand} instance.
     *
     * @param command : the user input string.
     * @return an instance of {@code ICommand} corresponding to the user input.
     * @throws IllegalArgumentException if the input is null or empty.
     */
    public ICommand getCommand(String command) throws IllegalArgumentException {
      if (command == null || command.isEmpty()) {
        throw new IllegalArgumentException("Input cannot be null or empty!");
      }

      List<String> commandTokens = splitCommand(command);
      String action = commandTokens.getFirst().toUpperCase();
      String noun = commandTokens.size() > 1 ? commandTokens.get(1) : null;

      Commands commandType = Commands.getEnum(action);
      if (commandType == null) {
        return null;
      }

      return switch (commandType) {
        case MOVE -> new MoveCommand(action, this.io);
        case INVENTORY -> new InventoryCommand(this.io);
        case LOOK -> new LookCommand(this.io);
        case USE -> new UseCommand(noun, this.io);
        case TAKE -> new TakeCommand(noun, this.io);
        case DROP -> new DropCommand(noun, this.io);
        case EXAMINE -> new ExamineCommand(noun, this.io);
        case ANSWER -> new AnswerCommand(noun, this.io);
        case SAVE -> new SaveCommand(this.io);
        case RESTORE -> new RestoreCommand(this.io);
        case QUIT -> null;
      };
    }

    /**
     * Helper functions that splits the user input string into a verb and a noun.
     *
     * @param command user input string.
     * @return a list of string with two elements.
     *          - first element is the action.
     *          - second element is the noun.
     *         If the command only has one word then the array will only have one element.
     */
    private List<String> splitCommand(String command) {
      String[] splitCommand = command.split(" ", 2);
      return Arrays.asList(splitCommand);
    }
  }