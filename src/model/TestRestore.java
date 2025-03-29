package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestRestore {

  private ObjectMapper objectMapper;
  private GameInfo gameInfo;
  private GameData gameData;
  private Room currentRoom;
  private Player player;

  public static void main(String[] args) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    GameInfo gameInfo = objectMapper.readValue(new File("src/data/savegamedatasimple_hallway.json"), GameInfo.class);
    GameData gameData = new GameData(gameInfo);

    Room room = gameData.getRoom("1");
    System.out.println(room.getItemNames());
  }
}
