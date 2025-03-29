package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FixtureTest {
  Fixture f2;
  GameInfo gameInfo;
  GameData gameData;

  /**
   * Method that sets up objects to run before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File("src/data/json_for_tests.json"), GameInfo.class);
    gameData = new GameData(gameInfo);
    f2 = gameData.getFixture("Fireplace");
  }

  /**
   * Tests the getPuzzle method of the Fixture class.
   */
  @Test
  void testGetPuzzle() {
    assertEquals("Bookshelf Code", f2.getPuzzle());
  }

  /**
   * Tests the getStates method of the Fixture class.
   */
  @Test
  void testGetStates() {
    assertEquals("active", f2.getStates());
  }
}
