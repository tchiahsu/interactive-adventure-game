package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

  Item i1;
  GameInfo gameInfo;
  GameData gameData;

  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File("src/data/align_quest_game_elements.json"), GameInfo.class);
    gameData = new GameData(gameInfo);
    i1 = gameInfo.getItems().get(0);
  }

  @Test
  void testGetName() {
    assertEquals("LAMP", i1.getName());
  }
}