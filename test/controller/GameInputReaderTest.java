package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import eventhandler.IEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for GameInputReader class.
 * The class handles input validation and processing.
 */
class GameInputReaderTest {
  /**
   * Dummy implementation of IEventHandler for testing.
   * Uses queues to simulate input and capture output messages.
   */
  static class DummyHandler implements IEventHandler {
    private final Queue<String> inputQueue = new LinkedList<>();
    private final StringBuilder outputLog = new StringBuilder();

    public void addInput(String input) {
      inputQueue.add(input);
    }

    public String getOutput() {
      return outputLog.toString();
    }

    @Override
    public String read() {
      return inputQueue.isEmpty() ? "" : inputQueue.poll();
    }

    @Override
    public void write(String message) {
      outputLog.append(message);
    }
  }

  private DummyHandler handler;
  private GameInputReader reader;

  @BeforeEach
  public void setup() {
    handler = new DummyHandler();
    reader = new GameInputReader(handler);
  }


  /**
   * Check that valid commands are validated correctly for commands made up of
   * one word.
   */
  @Test
  public void testValidCommandsWithOneWord() {
    assertTrue(reader.validateInput("N"));
    assertTrue(reader.validateInput("S"));
    assertTrue(reader.validateInput("E"));
    assertTrue(reader.validateInput("W"));
    assertTrue(reader.validateInput("I"));
    assertTrue(reader.validateInput("L"));

    assertTrue(reader.validateInput("NORTH"));
    assertTrue(reader.validateInput("SOUTH"));
    assertTrue(reader.validateInput("EAST"));
    assertTrue(reader.validateInput("WEST"));
    assertTrue(reader.validateInput("INVENTORY"));
    assertTrue(reader.validateInput("LOOK"));
  }

  /**
   * Check that valid commands are validated correctly for commands made up of
   * two words.
   */
  @Test
  public void testValidCommandsWithTwoWordCommand() {
    assertTrue(reader.validateInput("U KEY"));
    assertTrue(reader.validateInput("T KEY"));
    assertTrue(reader.validateInput("X KEY"));
    assertTrue(reader.validateInput("D KEY"));
    assertTrue(reader.validateInput("A KEY"));

    assertTrue(reader.validateInput("USE KEY"));
    assertTrue(reader.validateInput("TAKE KEY"));
    assertTrue(reader.validateInput("EXAMINE KEY"));
    assertTrue(reader.validateInput("DROP KEY"));
    assertTrue(reader.validateInput("ANSWER KEY"));
  }

  /**
   * Check that invalid commands are caught. Including the commands that are valid but
   * are missing arguments.
   */
  @Test
  public void testInvalidCommandReturnsFalse() {
    assertFalse(reader.validateInput("JUMP"));
    assertFalse(reader.validateInput("MOVE NORTH"));

    // Require an argument and should therefore return false
    assertFalse(reader.validateInput("ANSWER"));
    assertFalse(reader.validateInput("USE"));
    assertFalse(reader.validateInput("TAKE"));
    assertFalse(reader.validateInput("EXAMINE"));
    assertFalse(reader.validateInput("DROP"));
  }

  /**
   * Check that an empty argument command returns false.
   */
  @Test
  public void testEmptyArgumentCommand() {
    assertFalse(reader.validateInput("ANSWER    "));
    assertFalse(reader.validateInput("USE    "));
    assertFalse(reader.validateInput("TAKE   "));
    assertFalse(reader.validateInput("EXAMINE    "));
    assertFalse(reader.validateInput("DROP    "));
  }

  /**
   * Check that input read accepts valid commands.
   */
  @Test
  public void testReadInputValidCommand() {
    handler.addInput("LOOK"); // Valid command

    String input = reader.readInput();
    assertEquals("LOOK", input);
    assertTrue(handler.getOutput().contains("To move, enter"));
  }

  /**
   * Check that the read input method continues to prompt for an input if the given
   * inputs are invalid.
   */
  @Test
  public void testReadInputLoopsUntilValid() {
    handler.addInput("FLY");  // Invalid command
    handler.addInput("move north");  // Invalid command
    handler.addInput("X book"); // Valid command

    String input = reader.readInput();
    assertEquals("X BOOK", input);
    assertTrue(handler.getOutput().contains("Invalid Command"));
  }

  /**
   * Test getting the avatar name at the start of the game.
   * @throws IOException if there is an I/O error
   */
  @Test
  public void testGetAvatarNameAcceptsValidInput() throws IOException {
    handler.addInput("Hero");

    String name = reader.getAvatarName();
    assertEquals("HERO", name);
    assertTrue(handler.getOutput().contains("Enter a name for your player avatar:"));
  }

  /**
   * Test inputting invalid avatar names until you add a valid one.
   * @throws IOException if there is an I/O error
   */
  @Test
  public void testGetAvatarNameRejectsEmptyInput() throws IOException {
    handler.addInput("");        // Invalid
    handler.addInput(" ");       // Invalid
    handler.addInput("  adventurer  "); // Valid

    String name = reader.getAvatarName();
    assertEquals("ADVENTURER", name);
    assertTrue(handler.getOutput().contains("Invalid Avatar Name"));
  }
}