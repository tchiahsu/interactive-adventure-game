package eventhandler;

import java.io.IOException;
import java.util.Scanner;

/**
 * The class implements the IEventHandler class. It serves as the event handler
 * for a console-based game.
 */
public class ConsoleHandler implements IEventHandler {
  private final Scanner scanner = new Scanner(System.in);
  private final Appendable output = System.out;

  /**
   * Reads the next line of input from the specified source.
   * @return the line with the user input.
   * @throws IOException if an error occurs while reading input.
   */
  @Override
  public String read() throws IOException {
    return scanner.nextLine();
  }

  /**
   * Writes the given string in the specified target.
   * @param s : the string that need to be outputted.
   * @throws IOException if an error occurs during output.
   */
  @Override
  public void write(String s) throws IOException {
    output.append(s);
  }
}
