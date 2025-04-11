package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import controller.testutils.DummyHandler;
import controller.testutils.DummyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for GameController.
 * It tests the controllers ability to process commands and update the game state.
 */
class GameControllerTest {
  private DummyHandler handler;
  private DummyModel model;
  private TextController controller;

  /**
   * Setup before running each JUnit test.
   */
  @BeforeEach
  public void setup() {
    handler = new DummyHandler();
    model = new DummyModel();
    controller = new TextController(model, handler);
  }

  /**
   * Test inserting an avatar name and instantly quitting.
   * @throws IOException if there is an I/O error.
   */
  @Test
  void testPlayerNameAndQuit() throws IOException {
    handler.addInput("Player 1");
    handler.addInput("Q");
    controller.go();

    assertEquals("PLAYER 1", model.getPlayer().getName());
  }

  /**
   * Test inserting an avatar name and running valid command before quitting.
   * @throws IOException if there is an I/O error.
   */
  @Test
  void testPlayerNameAndLook() throws IOException {
    handler.addInput("Aligners");
    handler.addInput("l");
    handler.addInput("q");

    controller.go();
    String output = handler.getOutput();

    assertEquals("ALIGNERS", model.getPlayer().getName());
    assertTrue(output.contains("You shalt now"));
    assertTrue(output.contains(model.look()));
    assertTrue(output.contains(model.look()));
  }

  /**
   * Test using a mix between invalid and valid commands.
   * @throws IOException if there is an I/O error.
   */
  @Test
  public void testMixInvalidAndValidCommand() throws IOException {
    handler.addInput("Aligner");
    handler.addInput("move north");
    handler.addInput("l");
    handler.addInput("q");

    controller.go();

    String output = handler.getOutput();
    assertTrue(output.contains("Invalid Command"));
    assertTrue(output.contains("Executed: Look"));
    assertTrue(output.contains("Game Over"));
  }

  /**
   * Test running multiple valid commands.
   * @throws IOException if there is an I/O error.
   */
  @Test
  public void testOnlyValidCommand() throws IOException {
    handler.addInput("Aligner");
    handler.addInput("north");
    handler.addInput("l");
    handler.addInput("i");
    handler.addInput("q");

    controller.go();

    String output = handler.getOutput();
    assertTrue(output.contains("Executed: Move"));
    assertTrue(output.contains("Executed: Look"));
    assertTrue(output.contains("Executed: Inventory"));
    assertTrue(output.contains("Game Over"));
  }
}