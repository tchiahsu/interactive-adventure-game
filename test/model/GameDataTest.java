package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the methods of the GameData class.
 */
class GameDataTest {

  private GameData gd;

  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    GameInfo gameInfo = objectMapper.readValue(
            new File("src/data/align_quest_game_elements.json"), GameInfo.class);
    gd = new GameData(gameInfo);
  }

  /**
   * Ensures that a valid room is retrieved correctly and that a room without a monster returns
   * null for the mosnter name.
   */
  @Test
  void testGetRoom() {
    assertEquals("COURTYARD", gd.getRoom("1").getName());
    assertNull(gd.getRoom("1").getMonsterName());
  }

  /**
   * Ensures that an item is retrieved correctly.
   */
  @Test
  void testGetItem() {
    assertEquals(100, gd.getItem("Lamp").getMaxUses());
  }

  /**
   * Ensures that the monster's solution/weakness is retrieved correctly.
   */
  @Test
  void testGetMonster() {
    assertEquals("Carrot", gd.getMonster("Rabbit").getSolution());
  }
}