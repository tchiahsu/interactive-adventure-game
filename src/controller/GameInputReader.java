package controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code GameInputReader} class is responsible for reading and validating user input.
 * It ensures that only valid commands can be processed.
 */
public class GameInputReader {
  private final Readable input;
  private final Appendable output;
  private final List<String> validCommands = List.of(
      "N", "S", "W", "E", "I", "L", "U", "T", "D", "X", "A", "Q",
                "NORTH", "SOUTH", "WEST", "EAST", "INVENTORY", "LOOK", "USE",
                "TAKE", "DROP", "EXAMINE", "ANSWER", "QUIT", "SAVE", "RESTORE");

  /**
   * Construct a  {@code GameInputReader} with the specified input source and output destination.
   * @param input : input source.
   * @param output : output destination.
   */
  public GameInputReader(Readable input, Appendable output) {
    this.input = input;
    this.output = output;
  }

  /**
   * Reads and processes user input. It ensures that only valid commands are accepted.
   * If a command is invalid, it prompts the user to try again.
   * @return the valid user command as a {@code String}.
   */
  public String readInput() {
    String userCommand = "";
    try {
      boolean invalidInput = true; // Makes loop continue until a valid command is used
      Scanner scanner = new Scanner(this.input);

      while (invalidInput) {
        // Display available commands to user
        this.output.append("""
          ==========\s
          To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
          Other actions: (I)nventory, (L)ook around the location, (U)se an item
          (T)ake an item, (D)rop an item, or e(X)amine something.\s
          (A)nswer a question or provide a text solution.\s
          To end the game, enter (Q)uit to quit and exit.
          Your choice:\s""");
        userCommand = scanner.nextLine();
        String trimInput = userCommand.trim().toUpperCase(); // normalize input for validation

        // validate user input
        if (!this.validateInput(trimInput)) {
          this.output.append("Invalid Command. Please try again.\n\n");
        } else {
          invalidInput = false;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userCommand;
  }

  /**
   * Validates if the given input command is a valid command for the adventure game.
   * @param input : user string input.
   * @return true if command is valid, false otherwise.
   */
  public boolean validateInput(String input) {
    String verb = input.split(" ", 2)[0];
    return this.validCommands.contains(verb);
  }
}