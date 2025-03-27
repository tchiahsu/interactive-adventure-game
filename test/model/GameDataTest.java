package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameDataTest {

  private GameData gd;

  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    GameInfo gameInfo = objectMapper.readValue(new File("src/data/align_quest_game_elements.json"), GameInfo.class);
    gd = new GameData(gameInfo);
  }

  @Test
  void testGetRoom() {
    assertEquals("Courtyard", gd.getRoom("1").getName());
    assertNull(gd.getRoom("1").getMonster());
  }

  @Test
  void testGetItem() {
    assertEquals(100, gd.getItem("Lamp").getMaxUses());
  }

  @Test
  void testGetMonster() {
    assertEquals("Carrot", gd.getMonster("Rabbit").getSolution());
  }
}