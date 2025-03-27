package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {

  private Map<String, Room> roomsMap = new HashMap<>();
  private Map<String, Item> itemsMap = new HashMap<>();
  private Map<String, Fixture> fixturesMap = new HashMap<>();
  private Map<String, Monster> monstersMap = new HashMap<>();
  private Map<String, Puzzle> puzzlesMap = new HashMap<>();

  public GameData (GameInfo gameInfo) {
    createRoomsMap(gameInfo.getRooms());
    createItemsMap(gameInfo.getItems());
    createFixturesMap(gameInfo.getFixtures());
    createMonstersMap(gameInfo.getMonsters());
    createPuzzlesMap(gameInfo.getPuzzles());
  }

  private void createRoomsMap(List<Room> roomsList) {
    for (Room room : roomsList) {
      roomsMap.put(room.getRoomNumber(), room);
    }
  }

  private void createItemsMap(List<Item> itemsList) {
    for (Item item : itemsList) {
      itemsMap.put(item.getName(), item);
    }
  }

  private void createFixturesMap(List<Fixture> fixturesList) {
    for (Fixture fixture : fixturesList) {
      fixturesMap.put(fixture.getName(), fixture);
    }
  }

  private void createMonstersMap(List<Monster> monstersList) {
    for (Monster monster : monstersList) {
      monstersMap.put(monster.getName(), monster);
    }
  }

  private void createPuzzlesMap(List<Puzzle> puzzlesList) {
    for (Puzzle puzzle : puzzlesList) {
      puzzlesMap.put(puzzle.getName(), puzzle);
    }
  }

  public Room getRoom(String roomNumber) {
    return roomsMap.get(roomNumber);
  }

  public Item getItem(String itemName) {
    return itemsMap.get(itemName);
  }

  public Fixture getFixture(String fixtureName) {
    return fixturesMap.get(fixtureName);
  }

  public Monster getMonster(String monsterName) {
    return monstersMap.get(monsterName);
  }

  public Puzzle getPuzzle(String puzzleName) {
    return puzzlesMap.get(puzzleName);
  }
}
