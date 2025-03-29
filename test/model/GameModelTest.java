package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameModelTest {
  IPlayer p1;
  Room r1;
  Room r2;
  Room r3;
  Room r4;
  GameInfo gameInfo;
  GameData gameData;
  GameModel model;

  /**
   * Method that sets up objects to run before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File("src/data/json_for_model_tests.json"), GameInfo.class);
    gameData = new GameData(gameInfo);
    model = new GameModel("src/data/json_for_model_tests.json");
    r1 = gameData.getRoom("1");
    r2 = gameData.getRoom("2");
    r3 = gameData.getRoom("3");
    r4 = gameData.getRoom("4");
    p1 = model.getPlayer();
  }

  /**
   * Tests the getPlayer method.
   */
  @Test
  void testGetPlayer() {
    assertEquals(p1, model.getPlayer());
  }

  /**
   * Tests the move method in the model when there is no path
   * in that direction.
   */
  @Test
  void testMoveZero() {
    //TODO
  }

}
