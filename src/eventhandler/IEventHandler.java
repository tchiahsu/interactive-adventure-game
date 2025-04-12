package eventhandler;

import java.io.IOException;

/**
 * The interface defines the core functionalities of an event handler for our
 * adventure game.
 */
public interface IEventHandler {
  /**
   * Reads the next line of input from the specified source.
   * @return the line with the user input.
   * @throws IOException if an error occurs while reading input.
   */
  String read() throws IOException;

  /**
   * Writes the given string in the specified target.
   * @param s : the string that need to be outputted.
   * @throws IOException if an error occurs during output.
   */
  void write(String s) throws IOException;
}
