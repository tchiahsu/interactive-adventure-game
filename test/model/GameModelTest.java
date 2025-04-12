package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the methods of the GameModel class.
 */
public class GameModelTest {
  String testFilePath = "src/data/json_for_model_tests.json";
  GameModel model;
  String expectedString;

  /**
   * Method that sets up objects to run before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    model = new GameModel(testFilePath);
  }

  /**
   * Tests the getPlayer method.
   */
  @Test
  void testGetPlayer() {
    IPlayer p = model.getPlayer();
    assertEquals(100, p.getHealth());
    assertEquals(0, p.getScore());
    assertEquals(HealthStatus.AWAKE.getStatusMessage(), p.getHealthStatus());
  }

  /**
   * Tests the move method in the model when there is no path
   * in that direction.
   */
  @Test
  void testMoveToNoPath() {
    expectedString = "<<You cannot go in that direction>>\n";
    assertEquals(expectedString, model.move("SOUTH"));
  }

  /**
   * Tests the monster's active description and attack message is displayed
   * when the player enters a room with a monster.
   */
  @Test
  void testMoveToRoomWithMonster() {
    expectedString = "You are in the LIVING ROOM\nA monster Rabbit moves towards you! "
            + "He's blocking the way north. \nI think you might be dinner!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n"
            + "Items you see here: CARROT\n";

    assertEquals(expectedString, model.move("NORTH"));
  }

  /**
   * Tests the player cannot proceed to the next room if a monster is blocking the path.
   */
  @Test
  void testMoveToBlockedPathMonster() {
    // Enter room with monster
    model.move("NORTH");

    expectedString = "<<You are being blocked from moving that way!>>\n"
            + "A monster Rabbit moves towards you! He's blocking the way north. \n"
            + "I think you might be dinner!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n";

    // Attempt to move past the monster
    assertEquals(expectedString, model.move("NORTH"));
  }

  /**
   * Tests the player cannot proceed to the next room if a puzzle is blocking the path.
   */
  @Test
  void testMoveToBlockedPathPuzzle() {
    expectedString = "<<You are being blocked from moving that way!>>\n"
            + "You need a key to open the front door. Use the House Key to unlock it.\n";

    assertEquals(expectedString, model.move("EAST"));
  }

  /**
   * Tests the player can proceed to the next room after getting rid of an obstacle.
   */
  @Test
  void testMoveAfterDeactivatingObstacle() {
    // Enter room with monster
    model.move("NORTH");
    model.takeItem("CARROT");
    // Deactivate obstacle
    model.useItem("CARROT");

    expectedString = "You are in the KITCHEN\nThe kitchen is old-fashioned with a large stove "
            + "and a refrigerator filled with strange items.\n"
            + "Items you see here: \n";

    assertEquals(expectedString, model.move("NORTH"));
  }

  /**
   * Tests the puzzle description is displayed to the player when they enter a room with a puzzle.
   */
  @Test
  void testMoveToRoomWIthPuzzle() {
    model.move("NORTH");
    model.takeItem("CARROT");
    model.useItem("CARROT");
    model.move("NORTH");

    expectedString = "You are in the GARDEN\n"
            + "You need a key to open the front door. Use the House Key to unlock it.\n"
            + "Items you see here: \n";

    assertEquals(expectedString, model.move("EAST"));
  }

  /**
   * Tests the player can enter the next room if they are connected.
   */
  @Test
  void testMoveValidRoom() {
    expectedString = "You are in the KITCHEN\nThe kitchen is old-fashioned with a large stove "
            + "and a refrigerator filled with strange items.\n"
            + "Items you see here: \n";

    assertEquals(expectedString, model.move("WEST"));
  }

  /**
   * Tests looking in a normal room.
   */
  @Test
  void testLookInNormalRoom() {
    expectedString = "You are standing in the FRONT DOOR\n"
            + "You need a key to open the front door. Use the House Key to unlock it.\n"
            + "Items you see here: HOUSE KEY\n"
            + "You are healthy and wide awake.\n";

    assertEquals(expectedString, model.look());
  }

  /**
   * Tests looking with a monster in the room.
   */
  @Test
  void testLookWithMonsterInRoom() {
    model.move("NORTH");

    expectedString = "You are standing in the LIVING ROOM\n"
            + "A monster Rabbit moves towards you! He's blocking the way north. \n"
            + "I think you might be dinner!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n"
            + "Items you see here: CARROT\n"
            + "You are healthy and wide awake.\n";

    assertEquals(expectedString, model.look());
  }

  /**
   * Tests an empty inventory toString is returned.
   */
  @Test
  void testCheckEmptyInventory() {
    expectedString = "Items in inventory: No items found.\n";
    assertEquals(expectedString, model.checkInventory());
  }

  /**
   * Tests inventory toString is returned.
   */
  @Test
  void testCheckInventory() {
    model.takeItem("HOUSE KEY");
    expectedString = "Items in inventory: HOUSE KEY\n";
    assertEquals(expectedString, model.checkInventory());
  }

  /**
   * Tests examining an item in the room.
   */
  @Test
  void testExamineRoomItem() {
    expectedString = "From the FRONT DOOR you examine the HOUSE KEY: A simple old key that "
                     + "opens the front door of the house. It's engraved with the number '1'.\n";
    assertEquals(expectedString, model.examine("HOUSE KEY"));
  }

  /**
   * Tests examining an item in the player's inventory.
   */
  @Test
  void testExamineInventoryItem() {
    model.takeItem("HOUSE KEY");
    expectedString = "From your inventory, you examine the HOUSE KEY: A simple old key that "
                     + "opens the front door of the house. It's engraved with the number '1'.\n";
    assertEquals(expectedString, model.examine("HOUSE KEY"));
  }

  /**
   * Tests examining an active puzzle.
   */
  @Test
  void testExamineActivePuzzle() {
    expectedString = "From the FRONT DOOR you examine the LOCK MECHANISM: You need a key "
                     + "to open the front door. Use the House Key to unlock it.\n";
    assertEquals(expectedString, model.examine("LOCK MECHANISM"));
  }

  /**
   * Tests examining a deactivated puzzle.
   */
  @Test
  void testExamineDeactivatedPuzzle() {
    model.takeItem("HOUSE KEY");
    model.useItem("HOUSE KEY");
    expectedString = "From the FRONT DOOR you examine the LOCK MECHANISM: "
                     + "The front door is locked. You need a key to enter the house.\n";
    assertEquals(expectedString, model.examine("LOCK MECHANISM"));
  }

  /**
   * Tests examining an active monster.
   */
  @Test
  void testExamineActiveMonster() {
    model.move("NORTH");
    expectedString = "From the LIVING ROOM you examine the RABBIT: "
            + "A monster Rabbit moves towards you! He's blocking the way north. \n"
            + "I think you might be dinner!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n";

    assertEquals(expectedString, model.examine("RABBIT"));
  }

  /**
   * Tests examining a deactivated monster.
   */
  @Test
  void testExamineDeactivatedMonster() {
    model.move("NORTH");
    model.takeItem("CARROT");
    model.useItem("CARROT");
    expectedString = "From the LIVING ROOM you examine the RABBIT: "
            + "Awww. A furry rabbit twitching its nose and eating a carrot. "
            + "Makes you want to pet him.\n";

    assertEquals(expectedString, model.examine("RABBIT"));
  }

  /**
   * Tests examining a fixture.
   */
  @Test
  void testExamineFixture() {
    expectedString = "From the FRONT DOOR you examine the FIREPLACE: A grand stone "
            + "fireplace that looks like it hasn't been used in years. "
            + "There’s an odd symbol carved into the stone.\n";

    assertEquals(expectedString, model.examine("FIREPLACE"));
  }

  /**
   * Tests examining the player.
   */
  @Test
  void testExaminePlayer() {
    model.getPlayer().setName("Peter");
    expectedString = "Peter I know you! You're a fearless adventurer!\n";
    assertEquals(expectedString, model.examine("me"));
  }

  /**
   * Tests examining something invalid.
   */
  @Test
  void testExamineInvalid() {
    expectedString = "There is no APPLE to examine.\n";
    assertEquals(expectedString, model.examine("apple"));
  }

  /**
   * Tests using an invalid item.
   */
  @Test
  void testUseInvalidItem() {
    expectedString = "APPLE not found in inventory.\n";
    assertEquals(expectedString, model.useItem("apple"));
  }

  /**
   * Tests using an item with no more uses.
   */
  @Test
  void testUseSpentItem() {
    model.takeItem("HOUSE KEY");
    model.useItem("HOUSE KEY");

    expectedString = "Oh no! HOUSE KEY is either empty or cannot be used again!\n";
    assertEquals(expectedString, model.useItem("HOUSE KEY"));
  }

  /**
   * Tests using an item with no current use for it.
   */
  @Test
  void testItemNoCurrentUse() {
    // Get to a room without monsters or puzzles
    model.move("NORTH");
    model.takeItem("CARROT");
    model.useItem("CARROT");
    model.dropItem("CARROT");
    model.move("SOUTH");
    model.takeItem("HOUSE KEY");
    model.move("NORTH");
    model.move("NORTH");

    expectedString = "Using HOUSE KEY did nothing.\n";
    assertEquals(expectedString, model.useItem("HOUSE KEY"));
  }

  /**
   * Tests using the wrong item on a monster.
   */
  @Test
  void testItemWrongMonster() {
    model.takeItem("HOUSE KEY");
    model.move("NORTH");

    expectedString = "Using HOUSE KEY did nothing.\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n";
    assertEquals(expectedString, model.useItem("HOUSE KEY"));
  }

  /**
   * Tests using the wrong item on a puzzle.
   */
  @Test
  void testItemWrongPuzzle() {
    model.move("NORTH");
    model.takeItem("CARROT");
    model.move("SOUTH");

    expectedString = "Using CARROT did nothing.\n";
    assertEquals(expectedString, model.useItem("CARROT"));
  }

  /**
   * Tests taking a fixture in a room.
   */
  @Test
  void testTakeFixture() {
    expectedString = "You can't take the FIREPLACE!\n";
    assertEquals(expectedString, model.takeItem("FIREPLACE"));
  }

  /**
   * Tests taking a puzzle in a room.
   */
  @Test
  void testTakePuzzle() {
    expectedString = "You can't take the LOCK MECHANISM!\n";
    assertEquals(expectedString, model.takeItem("LOCK MECHANISM"));
  }

  /**
   * Tests taking a monster in a room.
   */
  @Test
  void testTakeMonster() {
    model.move("NORTH");

    expectedString = "You can't take the RABBIT!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n";
    assertEquals(expectedString, model.takeItem("RABBIT"));
  }

  /**
   * Tests taking an invalid item in a room.
   */
  @Test
  void testTakeInvalidItem() {
    expectedString = "APPLE not found in FRONT DOOR\n";
    assertEquals(expectedString, model.takeItem("APPLE"));
  }

  /**
   * Tests taking an item with a full inventory.
   */
  @Test
  void testTakeItemFullInventory() {
    model.takeItem("HOUSE KEY");
    model.useItem("HOUSE KEY");
    model.move("NORTH");

    expectedString = "Your inventory is too full!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n";
    assertEquals(expectedString, model.takeItem("CARROT"));
  }

  /**
   * Tests dropping an invalid item.
   */
  @Test
  void testDropInvalidItem() {
    expectedString = "You don't have APPLE\n";
    assertEquals(expectedString, model.dropItem("APPLE"));
  }

  /**
   * Tests dropping a valid item from inventory.
   */
  @Test
  void testDropItem() {
    model.takeItem("HOUSE KEY");

    expectedString = "HOUSE KEY removed from inventory.\n";
    assertEquals(expectedString, model.dropItem("HOUSE KEY"));
  }

  /**
   * Tests answer on a puzzle.
   */
  @Test
  void testAnswer() {
    model.move("NORTH");
    model.takeItem("CARROT");
    model.useItem("CARROT");
    model.move("NORTH");
    model.move("EAST");
    model.move("EAST");

    expectedString = "SUCCESS! You solved the puzzle with 'Library'\n";
    assertEquals(expectedString, model.answer("'Library'"));
  }

  /**
   * Tests wrong answer on a puzzle.
   */
  @Test
  void testAnswerIncorrect() {
    model.move("NORTH");
    model.takeItem("CARROT");
    model.useItem("CARROT");
    model.move("NORTH");
    model.move("EAST");
    model.move("EAST");

    expectedString = "Your answer APPLE did nothing.\n";
    assertEquals(expectedString, model.answer("APPLE"));
  }

  /**
   * Tests answering on nothing.
   */
  @Test
  void testAnswerOnNothing() {
    model.move("NORTH");
    expectedString = "You answered, but no one heard you.\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n";

    assertEquals(expectedString, model.answer("APPLE"));
  }

  /**
   * Tests saving the game.
   */
  @Test
  void testSaveGame() throws IOException {
    String gameFile = Paths.get(testFilePath).getFileName().toString();
    expectedString = "Game saved successfully!\n";
    assertEquals(expectedString, model.saveGame());

    assertTrue(Files.exists(Paths.get("src/data/savegamedata_" + gameFile)));
    assertTrue(Files.exists(Paths.get("src/data/saveroomdata_" + gameFile)));
    assertTrue(Files.exists(Paths.get("src/data/saveplayerdata_" + gameFile)));
  }

  /**
   * Tests restoring a previous saved game.
   */
  @Test
  void testRestoreGame() throws IOException {
    String gameFile = Paths.get(testFilePath).getFileName().toString();
    model.takeItem("HOUSE KEY");
    model.saveGame();
    model.dropItem("HOUSE KEY");

    assertEquals("Game loaded successfully!\n", model.restoreGame());
    assertEquals("Items in inventory: HOUSE KEY\n", model.checkInventory());
  }

  /**
   * Tests restoring from no saved file.
   */
  @Test
  void testRestoreInvalidGame() throws IOException {
    String gameFile = Paths.get(testFilePath).getFileName().toString();
    Files.deleteIfExists(Paths.get("src/data/savegamedata_" + gameFile));
    Files.deleteIfExists(Paths.get("src/data/saveroomdata_" + gameFile));
    Files.deleteIfExists(Paths.get("src/data/saveplayerdata_" + gameFile));

    expectedString = "No game file to load.\n";
    assertEquals(expectedString, model.restoreGame());
  }

  /**
   * Tests the ending message is displayed to the player.
   */
  @Test
  void testGetEndingMessage() {
    this.model.getPlayer().setName("Peter");

    expectedString = "Thank you for playing Peter!\n"
            + "Your score is 0\n"
            + "Your rank: Novice Explorer\n";
    assertEquals(expectedString, model.getEndingMessage());
  }

  /**
   * Tests getting the name of the game.
   */
  @Test
  void testGetGameName() {
    expectedString = "House of Hidden Secrets";
    assertEquals(expectedString, model.getGameName());
  }

  /**
   * Tests getting the examinable objects in a room with a monster.
   */
  @Test
  void testGetExaminableObjectsMonster() {
    model.move("NORTH");

    expectedString = "CARROT";
    assertEquals(expectedString, model.getExaminableObjects()[0]);

    expectedString = "RABBIT";
    assertEquals(expectedString, model.getExaminableObjects()[1]);

    expectedString = "FIREPLACE";
    assertEquals(expectedString, model.getExaminableObjects()[2]);

    expectedString = "ME";
    assertEquals(expectedString, model.getExaminableObjects()[3]);
  }

  /**
   * Tests getting the examinable objects in a room with a puzzle.
   */
  @Test
  void testGetExaminableObjectsPuzzle() {
    expectedString = "HOUSE KEY";
    assertEquals(expectedString, model.getExaminableObjects()[0]);

    expectedString = "LOCK MECHANISM";
    assertEquals(expectedString, model.getExaminableObjects()[1]);

    expectedString = "ME";
    assertEquals(expectedString, model.getExaminableObjects()[2]);
  }

  /**
   * Tests deactivating a puzzle makes the fixtures in the room examinable.
   */
  @Test
  void testGetExaminableObjectsInactivePuzzle() {
    model.takeItem("HOUSE KEY");
    model.useItem("HOUSE KEY");

    expectedString = "LOCK MECHANISM";
    assertEquals(expectedString, model.getExaminableObjects()[0]);

    expectedString = "FIREPLACE";
    assertEquals(expectedString, model.getExaminableObjects()[1]);

    expectedString = "ME";
    assertEquals(expectedString, model.getExaminableObjects()[2]);
  }

  /**
   * Tests getting the current state of the game with a puzzle in the room.
   */
  @Test
  void testGetCurrentStatePuzzle() {
    expectedString = "FRONT DOOR";
    assertEquals(expectedString, model.getCurrentState().get(0));

    expectedString = "/data/Resources/generic_puzzle.png";
    assertEquals(expectedString, model.getCurrentState().get(1));

    expectedString = "You need a key to open the front door. "
            + "Use the House Key to unlock it.\n"
            + "Items you see here: HOUSE KEY\n";
    assertEquals(expectedString, model.getCurrentState().get(2));

    expectedString = "You are healthy and wide awake.";
    assertEquals(expectedString, model.getCurrentState().get(3));

    expectedString = "100";
    assertEquals(expectedString, model.getCurrentState().get(4));

    expectedString = "0";
    assertEquals(expectedString, model.getCurrentState().get(5));

    expectedString = "";
    assertEquals(expectedString, model.getCurrentState().get(6));
  }

  /**
   * Tests getting the current state of the game with a monster in the room.
   */
  @Test
  void testGetCurrentStateMonster() {
    model.move("NORTH");

    expectedString = "/data/Resources/generic-monster.png";
    assertEquals(expectedString, model.getCurrentState().get(1));

    expectedString = "A monster Rabbit moves towards you! He's blocking the way north. \n"
            + "I think you might be dinner!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n"
            + "Items you see here: CARROT\n";
    assertEquals(expectedString, model.getCurrentState().get(2));
  }

  /**
   * Tests getting the current state of the game in a room without obstacles.
   */
  @Test
  void testGetCurrentStateNormalRoom() {
    // Get to a room without monsters or puzzles
    model.move("NORTH");
    model.takeItem("CARROT");
    model.useItem("CARROT");
    model.dropItem("CARROT");
    model.move("SOUTH");
    model.takeItem("HOUSE KEY");
    model.move("NORTH");
    model.move("NORTH");

    expectedString = "/data/Resources/generic_location.png";
    assertEquals(expectedString, model.getCurrentState().get(1));

    expectedString = "The kitchen is old-fashioned with a large stove "
            + "and a refrigerator filled with strange items.\n"
            + "Items you see here: \n";
    assertEquals(expectedString, model.getCurrentState().get(2));
  }

  /**
   * Tests getting the image path of an item.
   */
  @Test
  void testGetImagePathItem() {
    expectedString = "/data/Resources/generic_item.png";
    assertEquals(expectedString, model.getImagePath("HOUSE KEY"));
  }

  /**
   * Tests getting the image path of a fixture.
   */
  @Test
  void testGetImagePathFixture() {
    expectedString = "/data/Resources/generic_item.png";
    assertEquals(expectedString, model.getImagePath("FIREPLACE"));
  }

  /**
   * Tests getting the image path of a monster.
   */
  @Test
  void testGetImagePathMonster() {
    expectedString = "/data/Resources/generic-monster.png";
    assertEquals(expectedString, model.getImagePath("RABBIT"));
  }

  /**
   * Tests getting the image path of a puzzle.
   */
  @Test
  void testGetImagePathPuzzle() {
    expectedString = "/data/Resources/generic_puzzle.png";
    assertEquals(expectedString, model.getImagePath("LOCK MECHANISM"));
  }

  /**
   * Tests getting the image path of the player.
   */
  @Test
  void testGetImagePathPlayer() {
    expectedString = "/data/Resources/epic_adventurer.png";
    assertEquals(expectedString, model.getImagePath("ME"));
  }

  /**
   * Tests getting the items the player has in their inventory.
   */
  @Test
  void testGetInventoryItems() {
    model.takeItem("HOUSE KEY");

    expectedString = "HOUSE KEY";
    assertEquals(expectedString, model.getInventoryItems()[0]);
  }

  /**
   * Tests getting the items in a room with no items.
   */
  @Test
  void testGetRoomItemsNoItems() {
    // Remove items in current room
    model.takeItem("HOUSE KEY");
    model.move("NORTH");
    model.dropItem("HOUSE KEY");
    model.move("SOUTH");

    assertEquals(0, model.getRoomItems().length);
  }

  /**
   * Tests getting the items in a room with items.
   */
  @Test
  void testGetRoomItemsWithItems() {
    expectedString = "HOUSE KEY";
    assertEquals(expectedString, model.getRoomItems()[0]);
  }
}
