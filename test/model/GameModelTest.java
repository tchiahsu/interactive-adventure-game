package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameModelTest {
  IPlayer p1;
  Room r1;
  Room r2;
  Room r3;
  Room r4;
  GameInfo gameInfo;
  GameData gameData;
  GameModel model;
  String expectedString;

  /**
   * Method that sets up objects to run before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    gameInfo = objectMapper.readValue(new File("src/data/json_for_model_tests.json"), GameInfo.class);
    gameData = new GameData(gameInfo);
    model = new GameModel("src/data/json_for_model_tests.json");
    r1 = gameData.getRoom("1");
    r2 = gameData.getRoom("2");
    r3 = gameData.getRoom("3");
    r4 = gameData.getRoom("4");
    p1 = model.getPlayer();
  }

  /**
   * Tests the getPlayer method.
   */
  @Test
  void testGetPlayer() {
    assertEquals(p1, model.getPlayer());
  }

  /**
   * Tests the move method in the model when there is no path
   * in that direction.
   */
  @Test
  void testMoveToNoPath() {
    expectedString = "<<You cannot go in that direction>>\n\n";
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

    expectedString = "A monster Rabbit moves towards you! He's blocking the way north. \n"
            + "I think you might be dinner!\n"
            + "RABBIT licks you with a giant tongue!\n"
            + "You took -15 damage!\n"
            + "Items you see here: CARROT\n";

    // Attempt to move past the monster
    assertEquals(expectedString, model.move("NORTH"));
  }

  /**
   * Tests the player cannot proceed to the next room if a puzzle is blocking the path.
   */
  @Test
  void testMoveToBlockedPathPuzzle() {
    expectedString = "You need a key to open the front door. Use the House Key to unlock it.\n"
            + "Items you see here: HOUSE KEY\n";

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
   * Tests an empty inventory is returned.
   */
  @Test
  void testCheckEmptyInventory() {
    expectedString = "Items in inventory: No items found.\n";
    assertEquals(expectedString, model.checkInventory());
  }

  /**
   * Tests inventory is returned.
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
    expectedString = "From the FRONT DOOR you examine the FIREPLACE: A grand stone " +
            "fireplace that looks like it hasn't been used in years. " +
            "There’s an odd symbol carved into the stone.\n";

    assertEquals(expectedString, model.examine("FIREPLACE"));
  }

  /**
   * Tests examining something invalid.
   */
  @Test
  void testExamineInvalid() {
    expectedString = "There is no APPLE to examine\n";
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

    expectedString = "Oh no! HOUSE KEY is either empty or cannot be used again!";
    assertEquals(expectedString, model.useItem("HOUSE KEY"));
  }

//  /**
//   * Tests using wrong item on monster.
//   */
//  @Test
//  void testUseItemWrongMonster() {
//    model.takeItem("HOUSE KEY");
//    model.move("NORTH");
//
//    expectedString = "";
//    assertEquals(expectedString, model.useItem("HOUSE KEY"));
//  }
//
//  /**
//   * Tests using an item with no current use.
//   */
//  @Test
//  void testUseItemWrongUse() {
//    model.takeItem("HOUSE KEY");
//    model.useItem("HOUSE KEY");
//    model.dropItem("HOUSE KEY");
//    model.move("NORTH");
//    model.takeItem("CARROT");
//    model.move("SOUTH");
//
//    expectedString = "";
//    assertEquals(expectedString, model.useItem("CARROT"));
//  }
//

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
    expectedString = "You don't have APPLE.\n";
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
    expectedString = "Game saved successfully!\n";
    assertEquals(expectedString, model.saveGame());
  }

  @Test
  void testGetEndingMessage() {
    expectedString = "Thank you for playing!\n"
            + "Your score is 0\n"
            + "Your rank: Novice Explorer\n";
    assertEquals(expectedString, model.getEndingMessage());
  }

}
