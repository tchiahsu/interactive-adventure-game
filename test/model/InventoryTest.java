package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the methods of the Inventory class.
 */
public class InventoryTest {
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
   * Tests the addItem method of the inventory class.
   */
  @Test
  void testAddItem() {
    Item carrot = gameData.getItem("CARROT");
    i1.addItem(carrot);
    assertTrue(i1.hasItem(carrot));

  }

  /**
   * Tests the addItem method of the inventory class when
   * max capacity is reached.
   */
  @Test
  void testAddItemInValid() {
    i1.addItem(gameData.getItem("House Key"));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> {
              i1.addItem(gameData.getItem("Carrot"));
            });
  }

  /**
   * Tests the removeItem of the Inventory class.
   */
  @Test
  void testRemoveItem() {
    Item key =  gameData.getItem("House Key");
    i1.addItem(key);
    i1.removeItem(key);
    assertFalse(i1.hasItem(key));
    assertEquals(0, i1.getCurrentCapacity());
  }

  /**
   * Tests the getItem method of the Inventory class.
   */
  @Test
  void testGetItem() {
    Item bottle = gameData.getItem("Bottle");
    i1.addItem(bottle);

    assertEquals(bottle, i1.getItem(bottle));
  }

  /**
   * Tests the getItems method of the Inventory class.
   */
  @Test
  void testGetItems() {
    Item bottle = gameData.getItem("Bottle");
    Item carrot = gameData.getItem("Carrot");
    i1.addItem(bottle);
    i1.addItem(carrot);

    List<Item> expectedList = new ArrayList<>();
    expectedList.add(bottle);
    expectedList.add(carrot);
    assertEquals(expectedList, i1.getItems());
  }

  /**
   * Tests the setCurrentCapacity method of the Inventory class.
   */
  @Test
  void testSetCurrentCapacity() {
    i1.setCurrentCapacity(6);
    assertEquals(6, i1.getCurrentCapacity());
  }

  /**
   * Tests the getMaxCapacity method of the Inventory class.
   */
  @Test
  void testGetMaxCapacity() {
    assertEquals(13, i1.getMaxCapacity());
  }

  /**
   * Tests the toString method when the inventory is
   * empty.
   */
  @Test
  void testToStringEmpty() {
    assertEquals("Items in inventory: No items found.", i1.toString());
  }

  /**
   * Tests the toString method when the inventory has
   * single item.
   */
  @Test
  void testToString() {
    i1.addItem(gameData.getItem("Carrot"));
    assertEquals("Items in inventory: CARROT", i1.toString());
  }

  /**
   * Tests the toString method when the inventory has
   * multiple items.
   */
  @Test
  void testToStringMultiple() {
    i1.addItem(gameData.getItem("Carrot"));
    i1.addItem(gameData.getItem("Bottle"));
    assertEquals("Items in inventory: CARROT, BOTTLE", i1.toString());
  }
}
