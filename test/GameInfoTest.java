import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.Fixture;
import model.GameInfo;
import model.Item;
import model.Puzzle;
import model.Room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameInfoTest {

  GameInfo gameInfo;

  @BeforeEach
  void setUp() throws IOException {
    String testJson = "src/data/museum.json";
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File(testJson), GameInfo.class);
  }

  @Test
  void testGetRooms() {
    List<Room> rooms = gameInfo.getRooms();
    Room entrance = rooms.getFirst();
    assertEquals(1, entrance.getRoomNumber());
    assertEquals("Museum Entrance", entrance.getName());
    assertEquals("Turnstile", entrance.getPuzzle());
    assertEquals(-2, entrance.getNorth());
    assertNull(entrance.getMonster());
  }

  @Test
  void testGetItems() {
    List<Item> items = gameInfo.getItems();
    Item ticket = items.getFirst();
    assertEquals("Ticket", ticket.getName());
    assertEquals(1, ticket.getWeight());
    assertEquals("A complimentary museum ticket. It says ADMIT ONE, pwd = Align.",
                 ticket.getDescription());
  }

  @Test
  void testGetFixtures() {
    List<Fixture> fixtures = gameInfo.getFixtures();
    Fixture computer = fixtures.getFirst();
    assertEquals("Computer", computer.getName());
    assertEquals(1000, computer.getWeight());
  }

  @Test
  void testGetPuzzle() {
    List<Puzzle> puzzles = gameInfo.getPuzzles();
    Puzzle password = puzzles.get(1);
    assertEquals("PASSWORD", password.getName());
    assertTrue(password.isActive());
    assertTrue(password.affectsTarget());
    assertFalse(password.affectsPlayer());
  }
}