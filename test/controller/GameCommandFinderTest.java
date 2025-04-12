package controller;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import commands.AnswerCommand;
import commands.DropCommand;
import commands.ExamineCommand;
import commands.ICommand;
import commands.InventoryCommand;
import commands.LookCommand;
import commands.MoveCommand;
import commands.RestoreCommand;
import commands.SaveCommand;
import commands.TakeCommand;
import commands.UseCommand;
import eventhandler.IEventHandler;
import org.junit.jupiter.api.Test;

/**
 * Test class for the GameCommandFinder.
 */
class GameCommandFinderTest {

  /**
   * Create a dummy implementation for IEventHandler for testing purposes.
   * It doesn't read or write during each test.
   */
  static class DummyHandler implements IEventHandler {
    @Override
    public String read() throws IOException {
      return "";
    }

    @Override
    public void write(String s) throws IOException {
      // No operations
    }
  }

  private final IEventHandler dummyHandler = new DummyHandler();
  private final GameCommandFinder finder = new GameCommandFinder(dummyHandler);

  /**
   * Test the command finder correctly returns an instance of the move command.
   */
  @Test
  public void testMoveCommand() {
    ICommand command = finder.getCommand("north");
    assertNotNull(command);
    assertInstanceOf(MoveCommand.class, command);

    ICommand command2 = finder.getCommand("n");
    assertNotNull(command2);
    assertInstanceOf(MoveCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the inventory command.
   */
  @Test
  public void testInventoryCommand() {
    ICommand command = finder.getCommand("inventory");
    assertNotNull(command);
    assertInstanceOf(InventoryCommand.class, command);

    ICommand command2 = finder.getCommand("i");
    assertNotNull(command2);
    assertInstanceOf(InventoryCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the look command.
   */
  @Test
  public void testLookCommand() {
    ICommand command = finder.getCommand("look");
    assertNotNull(command);
    assertInstanceOf(LookCommand.class, command);

    ICommand command2 = finder.getCommand("l");
    assertNotNull(command2);
    assertInstanceOf(LookCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the use command.
   */
  @Test
  public void testUseCommand() {
    ICommand command = finder.getCommand("use key");
    assertNotNull(command);
    assertInstanceOf(UseCommand.class, command);

    ICommand command2 = finder.getCommand("u key");
    assertNotNull(command2);
    assertInstanceOf(UseCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the take command.
   */
  @Test
  public void testTakeCommand() {
    ICommand command = finder.getCommand("take key");
    assertNotNull(command);
    assertInstanceOf(TakeCommand.class, command);

    ICommand command2 = finder.getCommand("t key");
    assertNotNull(command2);
    assertInstanceOf(TakeCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the drop command.
   */
  @Test
  public void testDropCommand() {
    ICommand command = finder.getCommand("drop key");
    assertNotNull(command);
    assertInstanceOf(DropCommand.class, command);

    ICommand command2 = finder.getCommand("d key");
    assertNotNull(command2);
    assertInstanceOf(DropCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the examine command.
   */
  @Test
  public void testExamineCommand() {
    ICommand command = finder.getCommand("examine key");
    assertNotNull(command);
    assertInstanceOf(ExamineCommand.class, command);

    ICommand command2 = finder.getCommand("x key");
    assertNotNull(command2);
    assertInstanceOf(ExamineCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the answer command.
   */
  @Test
  public void testAnswerCommand() {
    ICommand command = finder.getCommand("answer puzzle");
    assertNotNull(command);
    assertInstanceOf(AnswerCommand.class, command);

    ICommand command2 = finder.getCommand("a puzzle");
    assertNotNull(command2);
    assertInstanceOf(AnswerCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the save command.
   */
  @Test
  public void testSaveCommand() {
    ICommand command = finder.getCommand("save");
    assertNotNull(command);
    assertInstanceOf(SaveCommand.class, command);
  }

  /**
   * Test the command finder correctly returns an instance of the restore command.
   */
  @Test
  public void testRestoreCommand() {
    ICommand command = finder.getCommand("restore");
    assertNotNull(command);
    assertInstanceOf(RestoreCommand.class, command);
  }

  /**
   * Test the command finder correctly returns null for a quit command.
   */
  @Test
  public void testQuitReturnsNull() {
    ICommand command = finder.getCommand("quit");
    assertNull(command);

    ICommand command2 = finder.getCommand("q");
    assertNull(command2);
  }

  /**
   * Test the command finder correctly returns null for an invalid command.
   */
  @Test
  public void testInvalidCommand() {
    ICommand command = finder.getCommand("move north");
    assertNull(command);
  }

  /**
   * Test the command finder correctly returns an instance of the move command for command string
   * in uppercase and a combination of uppercase and lowercase.
   */
  @Test
  public void testCommandIsCaseSensitive() {
    ICommand command = finder.getCommand("South");
    assertNotNull(command);
    assertInstanceOf(MoveCommand.class, command);

    ICommand command2 = finder.getCommand("SOUTH");
    assertNotNull(command2);
    assertInstanceOf(MoveCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an instance of the use command even when there is
   * no noun attached. The validation gets done elsewhere.
   */
  @Test
  public void testCommandNoNoun() {
    ICommand command = finder.getCommand("use");
    assertNotNull(command);
    assertInstanceOf(UseCommand.class, command);

    ICommand command2 = finder.getCommand("u");
    assertNotNull(command2);
    assertInstanceOf(UseCommand.class, command2);
  }

  /**
   * Test the command finder correctly returns an exception if the command string is null.
   */
  @Test
    public void testNullCommand() {
    assertThrows(IllegalArgumentException.class, () -> {
      finder.getCommand(null);
    });
  }

  /**
   * Test the command finder correctly returns an exception if the command string is empty.
   */
  @Test
  public void testEmptyCommand() {
    assertThrows(IllegalArgumentException.class, () -> {
      finder.getCommand("");
    });
  }

}