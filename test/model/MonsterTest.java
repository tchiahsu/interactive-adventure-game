package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cLass to test the methods of the Monster class.
 */
public class MonsterTest {

  Monster m1;
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
    m1 = gameData.getMonster("Rabbit");
  }

  /**
   * Tests the getName method of the Monster class.
   */
  @Test
  void testGetName() {
    assertEquals("RABBIT", m1.getName());
  }

  /**
   * Tests the getDescription method of the Monster class.
   */
  @Test
  void testGetDescription() {
    assertEquals("Awww. A furry rabbit twitching its nose and eating a carrot."
            + " Makes you want to pet him.", m1.getDescription());
  }

  /**
   * Tests the setName method of the Monster class.
   */
  @Test
  void testSetName() {
    m1.setName("WHITE MONSTER");
    assertEquals("WHITE MONSTER", m1.getName());
  }

  /**
   * Tests the isActive method of the Monster class.
   */
  @Test
  void testIsActive() {
    assertTrue(m1.isActive());
  }

  /**
   * Tests the affectsTarget method of the Monster class.
   */
  @Test
  void testAffectsTarget() {
    assertTrue(m1.affectsTarget());
  }

  /**
   * Tests the affectsTarget method of the Monster class.
   */
  @Test
  void testAffectsPlayer() {
    assertTrue(m1.affectsPlayer());
  }

  /**
   * Tests the getSolution method of the Monster class.
   */
  @Test
  void testGetSolution() {
    assertEquals("Carrot", m1.getSolution());
  }

  /**
   * Tests the getValue method of the Monster class.
   */
  @Test
  void testGetValue() {
    assertEquals(100, m1.getValue());
  }

  /**
   * Tests the getActiveDescription method of the Monster class.
   */
  @Test
  void testGetActiveDescription() {
    assertEquals("A monster Rabbit moves towards you! He's blocking "
            + "the way north. \nI think you might be dinner!", m1.getActiveDescription());
  }

  /**
   * Tests the getTarget method of the Monster class.
   */
  @Test
  void testGetTarget() {
    assertEquals("1:Front Door", m1.getTarget());
  }

  /**
   * Tests the getPicture method of the Monster class.
   */
  @Test
  void testPicture() {
    assertEquals(null, m1.getPicture());
  }

  /**
   * Tests the deactivate method of the Monster class.
   */
  @Test
  void testDeactivate() {
    m1.deactivate();
    assertFalse(m1.isActive());
  }

  /**
   * Tests the getDamage method of the Monster class.
   */
  @Test
  void testGetDamage() {
    assertEquals(-15, m1.getDamage());
  }

  /**
   * Tests the canAttack method of the Monster class.
   */
  @Test
  void testCanAttack() {
    assertTrue(m1.canAttack());
  }

  /**
   * Tests the getAttackMessage method of the Monster class.
   */
  @Test
  void testGetAttackMessage() {
    assertEquals("licks you with a giant tongue!", m1.getAttackMessage());
  }









}
