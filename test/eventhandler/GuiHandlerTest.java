package eventhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class has all the test to verify the GuiHandler works as expected.
 */
class GuiHandlerTest {
  private DummyGameView view;
  private GuiHandler handler;

  /**
   * Setup before each test ir ran.
   */
  @BeforeEach
  void setUp() {
    view = new DummyGameView();
    handler = new GuiHandler(view);
  }

  /**
   * Test that the popup for items works as expected.
   * It verifies the output string and title are correct.
   * @throws IOException if there is an I/O error.
   */
  @Test
  void testItemUsePopUp() throws IOException {
    handler.setCommandAction("USE");
    handler.write("USE KEY");

    assertEquals("USE KEY", view.popUpText);
    assertEquals("Item Used!", view.popUpTitle);
    assertTrue(view.updateViewCalled);
  }

  /**
   * Test that the popup for a blocked path works as expected.
   * It verifies that the output string and title are correct.
   * @throws IOException if there is an I/O error.
   */
  @Test
  void testBlockedPopUp() throws IOException {
    handler.setCommandAction("N");
    handler.write("You can't go there");

    assertEquals("You can't go there", view.popUpText);
    assertEquals("Blocked", view.popUpTitle);
  }

  /**
   * Test that the popup for an answer works as expected.
   * It verifies that the output string and title are correct.
   * @throws IOException if there is an I/O error.
   */
  @Test
  void testAnswerPopUp() throws IOException {
    handler.setCommandAction("ANSWER");
    handler.write("Correct!");

    assertEquals("Correct!", view.popUpText);
    assertEquals("Answer!", view.popUpTitle);
  }

  /**
   * Test that the popup for when the inventory is full works as expected.
   * It verifies that the output string is correct.
   * @throws IOException if there is an output error.
   */
  @Test
  void testFullInventoryPopUp() throws IOException {
    handler.setCommandAction("TAKE");
    handler.write("Your inventory is too full!");

    assertEquals("Your inventory is too full!", view.popUpText);
  }

  /**
   * Test that the popup method to check is a player is asleep works correctly.
   * @throws IOException if there is an output error.
   */
  @Test
  void testPlayerAsleep() throws IOException {
    view.playerAsleep = true;
    handler.setCommandAction("I");
    handler.write("Checking Inventory");

    assertTrue(view.updateViewCalled);
    assertTrue(view.gameOverPopUp);
  }

  /**
   * Test that invalid commands are handled correctly.
   * It checks that the message and title are accurate.
   * @throws IOException if there is an output error.
   */
  @Test
  void testInvalidCommand() throws IOException {
    handler.setCommandAction("");
    handler.write("Unknown Command");

    assertEquals("", view.popUpTitle);
    assertTrue(view.updateViewCalled);
  }
}