package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveTest {

  ObjectMapper objectMapper;
  GameInfo gameInfo;
  GameData gameData;
  Player player;
  Room room;

  @BeforeEach
  void setUp() throws IOException {
    String testJson = "src/data/museum.json";
    objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File(testJson), GameInfo.class);
    gameData = new GameData(gameInfo);
    player = new Player();
    room = new Room();
  }

  @Test
  void testSave() throws IOException {
    Puzzle puzzle = gameData.getPuzzle("turnstile");
    puzzle.deactivate();

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/testdatasave.json"), gameInfo);

    // Test saving player data
    player.decreaseHealth(-15);
    player.getInventory().addItem(gameData.getItem("Ticket"));
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/testplayersave.json"), player);

    // Test saving room data
    room.setItemNames("TICKET, HAIR CLIPPERS");
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/data/testroomsave.json"), room);

  }


}