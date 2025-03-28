package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class GameInputReader {
  private final Readable input;
  private final Appendable output;
  private final List<String> validCommands
      = List.of("N", "S", "W", "E", "I", "L", "U", "T", "D", "X", "A", "Q", "NORTH",
                  "SOUTH", "WEST", "EAST", "INVENTORY", "LOOK", "USE", "TAKE", "DROP", "EXAMINE",
                  "ANSWER", "QUIT");

  public GameInputReader() {
    this.input = new InputStreamReader(System.in);
    this.output = System.out;
  }

  public GameInputReader(Readable input, Appendable output) {
    this.input = input;
    this.output = output;
  }

  public String readInput() {
    String userCommand = "";
    try {
      boolean invalidInput = true;
      while (invalidInput) {
        Scanner scanner = new Scanner(this.input);
        this.output.append("\nTo move, enter: (N)orth, (S)outh, (E)ast or (W)est.\n"
          + "Other actions: (I)nventory, (L)ook around the location, (U)se an item\n"
          + "(T)ake an item, (D)rop an item, or e(X)amine something. \n"
          + "(A)nswer a question or provide a text solution. \n"
          + "To end the game, enter (Q)uit to quit and exit.\n"
          + "Your choice: ");
        String input = scanner.nextLine();
        userCommand = input;
        String trimInput = input.trim().toUpperCase();
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

  public boolean validateInput(String input) {
    String verb = input.split(" ", 2)[0];
    return this.validCommands.contains(verb);
  }
}