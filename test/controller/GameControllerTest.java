package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import model.GameModel;
import model.IGameModel;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameController.
 * It tests the controllers ability to process commands and update the game state.
 */
class GameControllerTest {
  private String gameFile;
  private IGameModel model;
  private StringWriter output;

  /**
   * Set up the tests environment before each test.
   * Initialize information that will be used for each test.
   * @throws IOException if an error occurs loading the file
   */
  @BeforeEach
  void setUp() throws IOException {
    gameFile = "src/data/json_for_tests.json";
    model = new GameModel(gameFile);
    output = new StringWriter();
  }

  /**
   * Test the controller correctly stores the avatar name and then quits the game.
   * @throws IOException if an I/O error occurs during the test
   */
  @Test
  void testPlayerNameAndQuit() throws IOException {
    StringBuilder testOutput = new StringBuilder();
    StringReader input = new StringReader("Player1\nQ\n");
    GameController controller = new GameController(model, input, testOutput);

    controller.go();

    assertEquals("PLAYER1", model.getPlayer().getName());
  }

  /**
   * Test that the controller correctly processes the player name and the look command returns the
   * correct description of the current room.
   * @throws IOException if an I/O error occurs
   */
  @Test
  void testPlayerNameAndLook() throws IOException {
    StringBuilder testOutput = new StringBuilder();
    StringReader input = new StringReader("Aligners\nl\nQ");
    GameController controller = new GameController(model, input, testOutput);

    controller.go();

    String lookOutput = model.look();
    assertEquals("ALIGNERS", model.getPlayer().getName());
    assertTrue(testOutput.toString().contains(lookOutput));
  }
}