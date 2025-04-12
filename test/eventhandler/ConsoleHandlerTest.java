package eventhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

/**
 * This class has the unit tests for the ConsoleHandler class.
 */
class ConsoleHandlerTest {

  /**
   * This test verifies that the ConsoleHandler correctly reads input from the console.
   * @throws IOException if there is an error reading the file.
   */
  @Test
  void testRead() throws IOException {
    String inputString = "Welcome to the Adventure Game!";
    ByteArrayInputStream input = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(input);

    ConsoleHandler handler = new ConsoleHandler();
    String readInput = handler.read();
    assertEquals(inputString, readInput);
  }

  /**
   * This test verifies that the ConsoleHandler correctly writes output to the console.
   * @throws IOException if an error occurs during output.
   */
  @Test
  void testWrite() throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    ConsoleHandler handler = new ConsoleHandler();
    handler.write("Welcome to Align");

    String terminalOutput = output.toString();
    assertTrue(terminalOutput.contains("Welcome to Align"));
  }
}