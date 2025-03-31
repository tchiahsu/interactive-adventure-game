package controller;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import commands.AnswerCommand;
import commands.DropCommand;
import commands.ExamineCommand;
import commands.InventoryCommand;
import commands.LookCommand;
import commands.MoveCommand;
import commands.RestoreCommand;
import commands.SaveCommand;
import commands.TakeCommand;
import commands.UseCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameCommandFinderTest {
  private GameCommandFinder commandFinder;
  private StringBuilder output;

  @BeforeEach
  void setUp() {
    this.output = new StringBuilder();
    this.commandFinder = new GameCommandFinder(this.output);
  }

  @Test
  void testGetInvalidCommand() {
    // Null command
    assertThrows(IllegalArgumentException.class, () -> commandFinder.getCommand(null));
    // Empty string command
    assertThrows(IllegalArgumentException.class, () -> commandFinder.getCommand(""));
    // Invalid commands
    assertNull(commandFinder.getCommand("UP"));
    assertNull(commandFinder.getCommand("DOWN"));
    assertNull(commandFinder.getCommand("FLY"));
    assertNull(commandFinder.getCommand("PICK"));
  }

  @Test
  void testGetValidMoveCommand() {
    // All variations of north command
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("n"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("north"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("N"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("NORTH"));

    // All variations of south command
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("s"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("south"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("S"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("SOUTH"));

    // All variations of east command
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("e"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("east"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("E"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("EAST"));

    // All variations of west command
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("w"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("west"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("W"));
    assertInstanceOf(MoveCommand.class, commandFinder.getCommand("WEST"));
  }

  @Test
  void testGetValidInventoryCommand() {
    assertInstanceOf(InventoryCommand.class, commandFinder.getCommand("i"));
    assertInstanceOf(InventoryCommand.class, commandFinder.getCommand("I"));
    assertInstanceOf(InventoryCommand.class, commandFinder.getCommand("inventory"));
    assertInstanceOf(InventoryCommand.class, commandFinder.getCommand("INVENTORY"));
  }

  @Test
  void testGetValidLookCommand() {
    assertInstanceOf(LookCommand.class, commandFinder.getCommand("l"));
    assertInstanceOf(LookCommand.class, commandFinder.getCommand("L"));
    assertInstanceOf(LookCommand.class, commandFinder.getCommand("look"));
    assertInstanceOf(LookCommand.class, commandFinder.getCommand("LOOK"));
  }

  @Test
  void testGetValidUseCommand() {
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("u"));
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("U"));
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("use"));
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("USE"));
  }

  @Test
  void testGetValidTakeCommand() {
    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("t"));
    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("T"));
    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("take"));
    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("TAKE"));
  }

  @Test
  void testGetValidDropCommand() {
    assertInstanceOf(DropCommand.class, commandFinder.getCommand("d"));
    assertInstanceOf(DropCommand.class, commandFinder.getCommand("D"));
    assertInstanceOf(DropCommand.class, commandFinder.getCommand("drop"));
    assertInstanceOf(DropCommand.class, commandFinder.getCommand("DROP"));
  }

  @Test
  void testGetValidExamineCommand() {
    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("x"));
    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("X"));
    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("examine"));
    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("EXAMINE"));
  }

  @Test
  void testGetValidAnswerCommand() {
    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("a"));
    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("A"));
    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("answer"));
    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("ANSWER"));
  }

  @Test
  void testGetValidSaveCommand() {
    assertInstanceOf(SaveCommand.class, commandFinder.getCommand("save"));
    assertInstanceOf(SaveCommand.class, commandFinder.getCommand("SAVE"));
  }

  @Test
  void testGetValidRestoreCommand() {
    assertInstanceOf(RestoreCommand.class, commandFinder.getCommand("restore"));
    assertInstanceOf(RestoreCommand.class, commandFinder.getCommand("RESTORE"));
  }

  @Test
  void testGetQuitCommand() {
    assertNull(commandFinder.getCommand("q"));
    assertNull(commandFinder.getCommand("Q"));
    assertNull(commandFinder.getCommand("quit"));
    assertNull(commandFinder.getCommand("QUIT"));
  }

  @Test
  void testGetCommandWithArguments() {
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("u key"));
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("U KEY"));
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("use key"));
    assertInstanceOf(UseCommand.class, commandFinder.getCommand("USE KEY"));

    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("t towel"));
    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("T TOWEL"));
    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("take towel"));
    assertInstanceOf(TakeCommand.class, commandFinder.getCommand("TAKE TOWEL"));

    assertInstanceOf(DropCommand.class, commandFinder.getCommand("d book"));
    assertInstanceOf(DropCommand.class, commandFinder.getCommand("D BOOK"));
    assertInstanceOf(DropCommand.class, commandFinder.getCommand("drop book"));
    assertInstanceOf(DropCommand.class, commandFinder.getCommand("DROP BOOK"));

    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("x door"));
    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("X DOOR"));
    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("examine door"));
    assertInstanceOf(ExamineCommand.class, commandFinder.getCommand("EXAMINE DOOR"));

    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("a monalisa"));
    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("A MONALISA"));
    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("answer monalisa"));
    assertInstanceOf(AnswerCommand.class, commandFinder.getCommand("ANSWER MONALISA"));
  }
}