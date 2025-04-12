package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomTest {
  Room r1;
  Room r2;
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
    r1 = gameData.getRoom("1");
    r2 = gameData.getRoom("2");
  }

  /**
   * Tests the getName method of the Room class.
   */
  @Test
  void testGetName() {
    assertEquals("FRONT DOOR", r1.getName());
  }

  /**
   * Tests the getItemNames method of the Room class.
   */
  @Test
  void testGetItemNames() {
    assertEquals("HOUSE KEY", r1.getItemNames());
  }

  /**
   * Tests the getName method of the Room class.
   */
  @Test
  void testDescription() {
    String description = "You're standing at the front door of a large, old house."
            + " The door is slightly ajar, revealing a dimly lit hallway.";
    assertEquals(description, r1.getDescription());
  }

  /**
   *  Tests the getRoomNumber method of the Room class.
   */
  @Test
  void testGetRoomNumber() {
    assertEquals("1", r1.getRoomNumber());
  }

  /**
   * Tests the getPuzzleName method of the Room class.
   */
  @Test
  void testGetPuzzleName() {
    assertEquals("Lock Mechanism", r1.getPuzzleName());
  }

  /**
   * Tests the getMonsterName method of the Room class.
   */
  @Test
  void testGetMonsterName() {
    assertEquals(null, r1.getMonsterName());
  }

  /**
   * Tests the getItemsName method of the Room class.
   */
  @Test
  void testGetItemsName() {
    assertEquals("HOUSE KEY", r1.getItemNames());
  }

  /**
   * Tests the setItemsName method of the Room class.
   */
  @Test
  void testSetItemsName() {
    r1.setItemNames("Car Key");
    assertEquals("CAR KEY", r1.getItemNames());
  }

  /**
   * Tests the getFixtureNames method of the Room class.
   */
  @Test
  void testGetFixtureNames() {
    assertEquals("FIREPLACE", r2.getFixtureNames());
  }

  /**
   * Tests the getPicture method of the Room class.
   */
  @Test
  void testGetPicture() {
    assertEquals("/data/Resources/generic_location.png", r1.getPicture());
  }

  /**
   * Tests the getPaths method for the Room class.
   */
  @Test
  void testGetPaths() {
    Map<String, String> result = new HashMap<>();
    result.put("NORTH","-2");
    result.put("SOUTH","0");
    result.put("EAST","0");
    result.put("WEST","0");
    assertEquals(result, r1.getPaths());
  }

  /**
   * Tests the getPath method in the Room class.
   */
  @Test
  void testGetPath() {
    assertEquals("-2",r1.getPath("north"));
  }
}