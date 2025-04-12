//package enginedriver;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
///**
// * Test class for GameEngineApp.
// */
//class GameEngineAppTest {
//
//  // Adjust this to wherever your test game file is located
//  private final String GAME_FILE_PATH =
//    Paths.get("test", "enginedriver", "resources", "museum.json").toString();
//
//  private final String SOURCE_FILE_PATH =
//    Paths.get("test", "enginedriver", "resources", "input.txt").toString();
//
//  private final String TARGET_FILE_PATH =
//    Paths.get("test", "enginedriver", "resources", "output.txt").toString();
//
//  @Test
//  void testConsoleModeRunsSuccessfully() {
//    assertDoesNotThrow(() -> {
//      GameEngineApp engine = new GameEngineApp(GAME_FILE_PATH, GameMode.CONSOLE);
//      engine.start();
//    });
//  }
//
//  @Test
//  void testGraphicsModeRunsSuccessfully() {
//    assertDoesNotThrow(() -> {
//      GameEngineApp engine = new GameEngineApp(GAME_FILE_PATH, GameMode.GRAPHICS);
//      engine.start();
//    });
//  }
//
//  @Test
//  void testBatchConsoleModeRunsSuccessfully() {
//    assertDoesNotThrow(() -> {
//      GameEngineApp engine = new GameEngineApp(GAME_FILE_PATH, GameMode.BATCH_CONSOLE, SOURCE_FILE_PATH);
//      engine.start();
//    });
//  }
//
//  @Test
//  void testBatchFileModeRunsSuccessfully() {
//    assertDoesNotThrow(() -> {
//      GameEngineApp engine = new GameEngineApp(GAME_FILE_PATH, GameMode.BATCH_FILE, SOURCE_FILE_PATH, TARGET_FILE_PATH);
//      engine.start();
//    });
//  }
//
//  @Test
//  void testConstructorThrowsWhenGameFileIsNull() {
//    assertThrows(IOException.class, () -> {
//      new GameEngineApp(null, GameMode.CONSOLE);
//    });
//  }
//
// }