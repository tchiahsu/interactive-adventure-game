package controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.List;

import controller.testutils.DummyGUIHandler;
import controller.testutils.DummyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class represents the test that check the functionality and behavior of the
 * ViewController class match our expectations.
 */
public class ViewControllerTest {
  private DummyGUIHandler handler;
  private DummyModel model;
  private ViewController controller;

  /**
   * Setup that is done before each test.
   */
  @BeforeEach
  void setUp() {
    model = new DummyModel();
    handler = new DummyGUIHandler();
    controller = new ViewController(model, handler);
  }

  /**
   * Test that the correct command is passed to the model and controller when the given
   * command is valid.
   * @throws IOException if there is an I/O error.
   */
  @Test
  void testExecuteValidCommand() throws IOException {
    controller.executeCommand("LOOK");
    assertEquals("LOOK", handler.getAction());
    assertEquals("LOOK", model.getLastCommand());

    controller.executeCommand("USE KEY");
    assertEquals("USE", handler.getAction());
    assertEquals("USE KEY", model.getLastCommand());

    controller.executeCommand("N");
    assertEquals("N", handler.getAction());
    assertEquals("NORTH", model.getLastCommand());
  }

  /**
   * Test that the command remains empty if the passed command to the model and controller
   * is invalid and no valid command has been passed yet.
   * @throws IOException if there is an I/0 error.
   */
  @Test
  void testExecuteInvalidCommand() throws IOException {
    controller.executeCommand("UP");
    assertNull(handler.getAction());
    assertEquals("", model.getLastCommand());

    controller.executeCommand("DOWN");
    assertNull(handler.getAction());
    assertEquals("", model.getLastCommand());

    controller.executeCommand("JUMP");
    assertNull(handler.getAction());
    assertEquals("", model.getLastCommand());
  }

  /**
   * Test that the command doesn't update when the given command is invalid. If a valid command
   * was given prior, that the one that should be returned.
   * @throws IOException if there is an I/O error.
   */
  @Test
  void testMixOfInvalidAndValidCommand() throws IOException {
    controller.executeCommand("UP");
    assertNull(handler.getAction());
    assertEquals("", model.getLastCommand());

    controller.executeCommand("LOOK");
    assertEquals("LOOK", handler.getAction());
    assertEquals("LOOK", model.getLastCommand());

    controller.executeCommand("JUMP");
    assertEquals("LOOK", handler.getAction());
    assertEquals("LOOK", model.getLastCommand());
  }

  /**
   * Test that the name of the player is set correctly.
   */
  @Test
  void testSetPlayerName() {
    controller.setPlayerName("Aligner 2");
    assertEquals("Aligner 2", model.getPlayer().getName());
  }

  /**
   * Test that we can retrieve the correct information from our game model through the
   * controller.
   */
  @Test
  void testGetGameInfo() {
    assertEquals("Adventure Game", controller.getGameName());
    assertEquals("Game Over", controller.getGameSummary());
    assertEquals("Executed: Image Path", controller.getImagePath("Image"));
  }

  /**
   * Test that we can retrieve the correct string of data related to items or objects
   * in the game.
   */
  @Test
  void testGetItemsMethods() {
    assertArrayEquals(new String[0], controller.getInventoryItems());
    assertArrayEquals(new String[0], controller.getCurrentRoomItems());
    assertArrayEquals(new String[0], controller.getExaminableObjects());
  }

  /**
   * Test we correctly retrieve the currents state of the game.
   */
  @Test
  void testGetGameState() {
    assertEquals(List.of(), controller.getCurrentState());
  }
}
