package enginedriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for GameEngineApp.
 */
class GameEngineAppTest {
  private String gameFile;
  private Readable source;
  private Appendable output;

  /**
   * Set up before each test.
   * Initializes the game file path and I/O objects.
   */
  @BeforeEach
  void setup() {
    this.gameFile = "src/data/json_for_tests.json";
    this.source = new StringReader("test input");
    this.output = new StringWriter();
  }


  /**
   * Test that the constructor throws IOException when given a null game file.
   * It also verifies the error message is correct.
   */
  @Test
  void testInvalidGameFile() {
    // Exception raised when game file is invalid
    Exception message = assertThrows(IOException.class, ()
      -> new GameEngineApp(null, source, output));
    assertEquals("Game File could not be found!", message.getMessage());
  }

  /**
   * Test that the constructor throws a IOException when given a null input source.
   * It also verifies the error message is correct.
   */
  @Test
  void testInvalidSource() {
    // Exception raised when game file is invalid
    Exception message = assertThrows(IOException.class, ()
      -> new GameEngineApp(gameFile, null, output));
    assertEquals("Source data could not be found!", message.getMessage());
  }

  /**
   * Test that the constructor throws an IOException when given a null output destination.
   * It also verifies the error message is correct.
   */
  @Test
  void testInvalidOutput() {
    // Exception raised when game file is invalid
    Exception message = assertThrows(IOException.class, ()
      -> new GameEngineApp(gameFile, source, null));
    assertEquals("Output destination could not be found!", message.getMessage());
  }

  /**
   * Test that valid parameters create an instance without an exception.
   * @throws IOException if an I/O error occurs while working with input or output
   */
  @Test
  void testValidConstructorCreatesInstance() throws IOException {
    GameEngineApp engine = new GameEngineApp(this.gameFile, this.source, this.output);
    assertNotNull(engine);
  }

  /**
   * Test the start method of GameEngineApp.
   * This test verifies that the game engine correctly initializes the model and controller.
   * It runs through the scenarios where the user is asked to provide an avatar name.
   * @throws IOException if I/0 error occurs during game execution.
   */
  @Test
  void testStartMethod() throws IOException {
    StringReader newInput =  new StringReader("Aligners\nQ");
    StringWriter gameOutput = new StringWriter();

    GameEngineApp engine = new GameEngineApp(this.gameFile, newInput, gameOutput);
    engine.start();
    String outputText = gameOutput.toString();
    assertTrue(outputText.contains("ALIGNERS"));
  }
 }