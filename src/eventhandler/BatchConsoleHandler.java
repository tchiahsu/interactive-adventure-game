package eventhandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class implements the IEventHandler class. It serves as the event handler
 * for a batch-console game.
 */
public class BatchConsoleHandler implements IEventHandler {
  private final BufferedReader reader;
  private final Appendable output = System.out;

  /**
   * Contructs a batch-console event handler for the game.
   * @param sourceFile : the file where the commands come from.
   * @throws IOException : if there is an error reading the commands.
   */
  public BatchConsoleHandler(String sourceFile) throws IOException {
    this.reader = new BufferedReader(new FileReader(sourceFile));
  }

  /**
   * Reads the next line of input from the specified source.
   * @return the line with the user input.
   * @throws IOException if an error occurs while reading input.
   */
  @Override
  public String read() throws IOException {
    return reader.readLine();
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
