import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import engineDriver.GameInfo;

public class DataReader {

  public static void main(String[] args) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    String hallwayjson = "src/data/simple_hallway.json";
    GameInfo game = objectMapper.readValue(new File(hallwayjson), GameInfo.class);
    System.out.println(game.getName());

  }
}
