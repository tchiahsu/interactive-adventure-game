package enginedriver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineAppTest {
  private String gameFile;
  private Readable source;
  private Appendable output;

  @BeforeEach
  void setup() {
    this.gameFile = "src/data/json_for_tests.json";
    this.source = new StringReader("test input");
    this.output = new StringWriter();
  }

  @Test
  void testInvalidGameFile() {
    // Exception raised when game file is invalid
    Exception message = assertThrows(IOException.class, ()
      -> new GameEngineApp(null, source, output));
    assertEquals("Game File could not be found!", message.getMessage());
  }

  @Test
  void testInvalidSource() {
    // Exception raised when game file is invalid
    Exception message = assertThrows(IOException.class, ()
      -> new GameEngineApp(gameFile, null, output));
    assertEquals("Source data could not be found!", message.getMessage());
  }

  @Test
  void testInvalidOutput() {
    // Exception raised when game file is invalid
    Exception message = assertThrows(IOException.class, ()
      -> new GameEngineApp(gameFile, source, null));
    assertEquals("Output destination could not be found!", message.getMessage());
  }
}