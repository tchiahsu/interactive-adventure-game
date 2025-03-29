package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class that tests the method of the Item class.
 */
class ItemTest {

  Item i1;
  GameInfo gameInfo;
  GameData gameData;

  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File("src/data/json_for_tests.json"), GameInfo.class);
    gameData = new GameData(gameInfo);
    i1 = gameData.getItem("House Key");
  }

  /**
   * Tests the getName method of the Item class.
   */
  @Test
  void testGetName() {
    assertEquals("HOUSE KEY", i1.getName());
  }

  /**
   * Tests the setName method of the Item class.
   */
  @Test
  void testSetName() {
    i1.setName("CAR KEY");
    assertEquals("CAR KEY", i1.getName());
  }

  /**
   * Tests the getName method of the Item class.
   */
  @Test
  void testGetDescription() {
    assertEquals("A simple old key that opens the front door of the house. "
            + "It's engraved with the number '1'.", i1.getDescription());
  }

  /**
   * Tests the getName method of the Item class.
   */
  @Test
  void testGetPicture() {
    assertEquals(null, i1.getPicture());
  }

  /**
   * Tests the getWeight method of the Item class.
   */
  @Test
  void testGetWeight() {
    assertEquals(10, i1.getWeight());
  }

  /**
   * Test the getMaxUses method of the Item class.
   */
  @Test
  void testGetMaxUses() {
    assertEquals(1,i1.getMaxUses());
  }

  /**
   * Test the getUsesRemaining method of the Item class.
   */
  @Test
  void testGetUsesRemaining() {
    assertEquals(1,i1.getUsesRemaining());
  }

  /**
   * Test the getValue method of the Item class.
   */
  @Test
  void testGetValue() {
    assertEquals(10,i1.getValue());
  }

  /**
   * Test the getWhenUsedDescription method of the Item class.
   */
  @Test
  void testGetWhenUsedDescription() {
    assertEquals("You unlock the front door with the house key. It creaks as it opens.",i1.getWhenUsedDescription());
  }

  /**
   * Tests the reduceUse method of the Item class.
   */
  @Test
  void testReduceUse() {
    i1.reduceUse();
    assertEquals(0,i1.getUsesRemaining());
  }

}