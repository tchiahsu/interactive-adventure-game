package eventhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuiHandlerTest {
  private DummyGameView view;
  private GuiHandler handler;

  @BeforeEach
  void setUp() {
    view = new DummyGameView();
    handler = new GuiHandler(view);
  }

  @Test
  void testItemUsePopUp() throws IOException {
    handler.setCommandAction("USE");
    handler.write("USE KEY");

    assertEquals("USE KEY", view.popUpText);
    assertEquals("Item Used!", view.popUpTitle);
    assertTrue(view.updateViewCalled);
  }

  @Test
  void testBlockedPopUp() throws IOException {
    handler.setCommandAction("N");
    handler.write("You can't go there");

    assertEquals("You can't go there", view.popUpText);
    assertEquals("Blocked", view.popUpTitle);
  }

  @Test
  void testAnswerPopUp() throws IOException {
    handler.setCommandAction("ANSWER");
    handler.write("Correct!");

    assertEquals("Correct!", view.popUpText);
    assertEquals("Answer!", view.popUpTitle);
  }

  @Test
  void testFullInventoryPopUp() throws IOException {
    handler.setCommandAction("TAKE");
    handler.write("Your inventory is too full!");

    assertEquals("Your inventory is too full!", view.popUpText);
  }

  @Test
  void testPlayerAsleep() throws IOException {
    view.playerAsleep = true;
    handler.setCommandAction("I");
    handler.write("Checking Inventory");

    assertTrue(view.updateViewCalled);
    assertTrue(view.gameOverPopUp);
  }

  @Test
  void testInvalidCommand() throws IOException {
    handler.setCommandAction("");
    handler.write("Unknown Command");

    assertEquals("", view.popUpTitle);
    assertTrue(view.updateViewCalled);
  }
}