package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the methods of the Player class.
 */
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
   * is greater than 700.
   */
  @Test
  void testGetRankLegendary() {
    p1.increaseScore(800);
    assertEquals("Legendary Trailblazer", p1.getRank());
  }

  /**
   * Tests the getRank method of the Player when score is
   * less than 150.
   */
  @Test
  void testGetRankMaster() {
    p1.increaseScore(500);
    assertEquals("Master Voyager", p1.getRank());
  }

  /**
   * Tests the getRank method of the Player when score is
   * less and player's Rank is Novice.
   */
  @Test
  void testGetRankSeasoned() {
    p1.increaseScore(260);
    assertEquals("Seasoned Pathfinder", p1.getRank());
  }

  /**
   * Tests the getRank method of the Player when score is
   * less than .
   */
  @Test
  void testGetRankNovice() {
    p1.increaseScore(150);
    assertEquals("Novice Explorer", p1.getRank());
  }

  /**
   * Tests the healthStatus method when player health is less
   * than zero.
   */
  @Test
  void testGetHealthStatusSleepEqual() {
    p1.decreaseHealth(-100);
    assertEquals("Your health has dropped to the sleep zone. "
           + "\nNighty-night\nGame Over!\n", p1.getHealthStatus());
  }

  /**
   * Tests the healthStatus method when player health is less
   * than zero.
   */
  @Test
  void testGetHealthStatusSleep() {
    p1.decreaseHealth(-150);
    assertEquals("Your health has dropped to the sleep zone. "
            + "\nNighty-night\nGame Over!\n", p1.getHealthStatus());
  }


  /**
   * Tests the healthStatus method when player health status is WOOZY.
   */
  @Test
  void testGetHealthStatusWoozy() {
    p1.decreaseHealth(-70);
    assertEquals("Your health is very low! And you're woozy\n", p1.getHealthStatus());
  }

  /**
   * Tests the healthStatus method when player health status is WOOZY.
   */
  @Test
  void testGetHealthStatusFatigue() {
    p1.decreaseHealth(-40);
    assertEquals("Adventuring has made you very tired! "
            + "Your health is low!\n", p1.getHealthStatus());
  }

  /**
   * Tests the healthStatus method when player health status is awake.
   */
  @Test
  void testGetHealthStatusAwake() {
    p1.decreaseHealth(-10);
    assertEquals("You are healthy and wide awake.\n", p1.getHealthStatus());
  }


}
