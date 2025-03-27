package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GameInputReader {
  private final Readable input;
  private final Appendable output;

  public GameInputReader() {
    this.input = new InputStreamReader(System.in);
    this.output = System.out;
  }

  public String readInput() {
    try {
      Scanner scanner = new Scanner(this.input);
      this.output.append("To move, enter: (N)orth, (S)outh, (E)ast or (W)est.\n"
        + "Other actions: (I)nventory, (L)ook around the location, (U)se an item\n"
        + "(T)ake an item, (D)rop an item, or e(X)amine something. \n"
        + "(A)nswer a question or provide a text solution. \n"
        + "To end the game, enter (Q)uit to quit and exit.\n"
        + "Your choice: ");
      return scanner.nextLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}