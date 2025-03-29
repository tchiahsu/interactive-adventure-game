package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GameData class manages the data of the game.
 * It stores these entities in maps and provides methods to retrieve
 * them by their identifiers.
 */
public class GameData {

  private Map<String, Room> roomsMap = new HashMap<>();
  private Map<String, Item> itemsMap = new HashMap<>();
  private Map<String, Fixture> fixturesMap = new HashMap<>();
  private Map<String, Monster> monstersMap = new HashMap<>();
  private Map<String, Puzzle> puzzlesMap = new HashMap<>();

  /**
   * Constructs a GameData object by initializing the maps with data provided from the
   * GameInfo object.
   * @param gameInfo the GameInfo object that contains the game data.
   */
  public GameData(GameInfo gameInfo) {
    createRoomsMap(gameInfo.getRooms());
    createItemsMap(gameInfo.getItems());
    createFixturesMap(gameInfo.getFixtures());
    createMonstersMap(gameInfo.getMonsters());
    createPuzzlesMap(gameInfo.getPuzzles());
  }

  /**
   * Populates the rooms map with the provided list of rooms.
   * @param roomsList a list of Room objects to be added to the rooms map.
   */
  private void createRoomsMap(List<Room> roomsList) {
    if (roomsList == null) {
      return;
    }
    for (Room room : roomsList) {
      roomsMap.put(room.getRoomNumber(), room);
    }
  }

  /**
   * Populates the items map with the provided list of items.
   * @param itemsList a list of Item objects to be added to the items map.
   */
  private void createItemsMap(List<Item> itemsList) {
    if (itemsList == null) {
      return;
    }
    for (Item item : itemsList) {
      itemsMap.put(item.getName(), item);
    }
  }

  /**
   * Populates the fixtures map with the provided list of fixtures in jSON.
   * @param fixturesList a list of Fixture objects to be added to the fixtures map.
   */
  private void createFixturesMap(List<Fixture> fixturesList) {
    if (fixturesList == null) {
      return;
    }
    for (Fixture fixture : fixturesList) {
      fixturesMap.put(fixture.getName(), fixture);
    }
  }

  /**
   * Populates the monsters map with the provided list of monsters.
   * @param monstersList a list of Monster objects to be added to the monsters map.
   */
  private void createMonstersMap(List<Monster> monstersList) {
    if (monstersList == null) {
      return;
    }
    for (Monster monster : monstersList) {
      monstersMap.put(monster.getName(), monster);
    }
  }

  /**
   * Populates the puzzles map with the provided list of puzzles.
   * @param puzzlesList a list of Puzzle objects to be added to the puzzles map.
   */
  private void createPuzzlesMap(List<Puzzle> puzzlesList) {
    if (puzzlesList == null) {
      return;
    }
    for (Puzzle puzzle : puzzlesList) {
      puzzlesMap.put(puzzle.getName(), puzzle);
    }
  }

  /**
   * Retrieves the room corresponding to the given room number.
   * @param roomNumber the room number to search for.
   * @return the Room associated with the given room number, or null if not found.
   */
  public Room getRoom(String roomNumber) {
    return roomsMap.get(roomNumber);
  }

  /**
   * Retrieves the item corresponding to the given item name.
   * @param itemName the name of the item to search for.
   * @return the Item associated with the given item name, or null if not found.
   */
  public Item getItem(String itemName) {
    return itemsMap.get(itemName.toUpperCase());
  }

  /**
   * Retrieves the fixture corresponding to the given fixture name.
   * @param fixtureName the name of the fixture to search for.
   * @return the Fixture associated with the given fixture name, or null if not found.
   */
  public Fixture getFixture(String fixtureName) {
    return fixturesMap.get(fixtureName.toUpperCase());
  }

  /**
   * Retrieves the monster corresponding to the given monster name.
   * @param monsterName the name of the monster to search for.
   * @return the Monster associated with the given monster name, or null if not found.
   */
  public Monster getMonster(String monsterName) {
    return monstersMap.get(monsterName.toUpperCase());
  }

  /**
   * Retrieves the puzzle corresponding to the given puzzle name.
   * @param puzzleName the name of the puzzle to search for.
   * @return the Puzzle associated with the given puzzle name, or null if not found.
   */
  public Puzzle getPuzzle(String puzzleName) {
    return puzzlesMap.get(puzzleName.toUpperCase());
  }
}
