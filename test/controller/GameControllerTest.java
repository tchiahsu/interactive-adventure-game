package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import model.GameModel;
import model.IGameModel;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
  private String gameFile;
  private IGameModel model;
  private StringWriter output;

  @BeforeEach
  void setUp() throws IOException {
    gameFile = "src/data/json_for_tests.json";
    model = new GameModel(gameFile);
    output = new StringWriter();
  }

  @Test
  void testPlayerNameAndQuit() throws IOException {
    StringBuilder testOutput = new StringBuilder();
    StringReader input = new StringReader("Player1\nQ\n");
    GameController controller = new GameController(model, input, testOutput);

    controller.go();

    assertEquals("PLAYER1", model.getPlayer().getName());
  }

  @Test
  void testPlayerNameAndLook() throws IOException {
    StringBuilder testOutput = new StringBuilder();
    StringReader input = new StringReader("Aligners\nL\nQ");
    GameController controller = new GameController(model, input, testOutput);

    controller.go();

    String lookOutput = model.look();
    assertEquals("ALIGNERS", model.getPlayer().getName());
    assertTrue(testOutput.toString().contains(lookOutput),
      "Output should contain the look command result");
  }

}