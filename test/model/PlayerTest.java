package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class PlayerTest {
  GameInfo gameInfo;
  GameData gameData;
  Player p1;
  Inventory i1;

  /**
   * Method that sets up objects to run before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File("src/data/json_for_tests.json"), GameInfo.class);
    gameData = new GameData(gameInfo);
    p1 = new Player();
    i1 = p1.getInventory();
  }

  /**
   * Tests the getHealth method of the Player class.
   */
  @Test
  void testGetHealth() {
    assertEquals(100, p1.getHealth());
  }

  /**
   * Tests the getScore method of the Player class.
   */
  @Test
  void testGetScore() {
    assertEquals(0, p1.getScore());
  }

  /**
   * Tests the getInventory of the Player class.
   */
  @Test
  void testGetInventory() {
    i1.addItem(gameData.getItem("House Key"));
    assertEquals(i1, p1.getInventory());
  }

  /**
   * Tests the IncreaseScore method of the Player class.
   */
  @Test
  void testIncreaseScore() {
    // Test increasing score
    p1.increaseScore(10);
    assertEquals(10, p1.getScore());

    // Increase again
    p1.increaseScore(20);
    assertEquals(30, p1.getScore());
  }

  /**
   * Tests the decreaseScore method of the Player class.
   */
  @Test
  void testDecreaseScore() {
    // Test decreasing score
    p1.increaseScore(20);
    p1.decreaseScore(10);
    assertEquals(10, p1.getScore());

  }

  /**
   * Tests the increaseHealth method of the Player class.
   */
  @Test
  void testDecreaseHealth() {
    p1.decreaseHealth(-20);
    assertEquals(80, p1.getHealth());
  }

  /**
   * Tests the setName method of the Player.
   */
  @Test
  void testSetName() {
    p1.setName("Spiderman");
    assertEquals("Spiderman", p1.getName());
  }

  /**
   * Tests the getRank method of the Player when the score
   * is less than 50.
   */
  @Test
  void testGetRankLow() {
    assertEquals("Novice Explorer", p1.getRank());
  }

  /**
   * Tests the getRank method of the Player when score is
   * less than 150.
   */
  @Test
  void testGetRankAverage() {
    p1.increaseScore(140);
    assertEquals("Seasoned Pathfinder", p1.getRank());
  }

  /**
   * Tests the getRank method of the Player when score is
   * less than 200.
   */
  @Test
  void testGetRankHigh() {
    p1.increaseScore(180);
    assertEquals("Master Voyager", p1.getRank());
  }

  /**
   * Tests the getRank method of the Player when score is
   * less than 200.
   */
  @Test
  void testGetRankMaximum() {
    p1.increaseScore(250);
    assertEquals("Legendary Trailblazer", p1.getRank());
  }

}
