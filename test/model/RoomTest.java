package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {
  Room r1;
  GameInfo gameInfo;
  GameData gameData;

  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File("src/data/align_quest_game_elements.json"), GameInfo.class);
    gameData = new GameData(gameInfo);
    r1 = gameData.getRoom("2");
  }

  @Test
  void testGetName() {
    assertEquals("MANSION ENTRANCE", r1.getName());
  }

  @Test
  void testGetItemNames() {
    assertEquals("THUMB DRIVE, MODULO 2", r1.getItemNames());
  }
}