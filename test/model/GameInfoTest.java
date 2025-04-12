package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Class that tests the GameInfo class.
 */
class GameInfoTest {

  GameInfo gameInfo;

  /**
   * Method that sets up objects to run before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    String testJson = "src/data/museum.json";
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File(testJson), GameInfo.class);
  }

  /**
   * Tests the getRooms method.
   */
  @Test
  void testGetRooms() {
    List<Room> rooms = gameInfo.getRooms();
    Room entrance = rooms.getFirst();
    assertEquals("1", entrance.getRoomNumber());
    assertEquals("MUSEUM ENTRANCE", entrance.getName());
    assertEquals("TURNSTILE", entrance.getPuzzleName());
  }

  /**
   * Tests the getItems method.
   */
  @Test
  void testGetItems() {
    List<Item> items = gameInfo.getItems();
    Item ticket = items.getFirst();
    assertEquals("TICKET", ticket.getName());
    assertEquals(1, ticket.getWeight());
    assertEquals("A complimentary museum ticket. It says ADMIT ONE, pwd = Align.",
                 ticket.getDescription());
  }

  /**
   * Tests the getFixtures method.
   */
  @Test
  void testGetFixtures() {
    List<Fixture> fixtures = gameInfo.getFixtures();
    Fixture computer = fixtures.getFirst();
    assertEquals("COMPUTER", computer.getName());
    assertEquals(1000, computer.getWeight());
  }

  /**
   * Tests the getPuzzles method.
   */
  @Test
  void testGetPuzzles() {
    List<Puzzle> puzzles = gameInfo.getPuzzles();
    Puzzle password = puzzles.get(1);
    assertEquals("PASSWORD", password.getName());
    assertTrue(password.isActive());
    assertTrue(password.affectsTarget());
    assertFalse(password.affectsPlayer());
  }

  /**
   * Tests the getMonsters method.
   */
  @Test
  void testGetMonsters() {
    assertNull(gameInfo.getMonsters());
  }

}