//package controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.io.IOException;
//import java.io.StringReader;
//import java.io.StringWriter;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
///**
// * Test class for GameInputReader class.
// * The class handles input validation and processing.
// */
//class GameInputReaderTest {
//  private GameInputReader inputReader;
//  private StringWriter output;
//  private StringReader input;
//
//  /**
//   * Set up environment before each test.
//   * Initializes a new StringWritten as output.
//   */
//  @BeforeEach
//  void setUp() {
//    this.output = new StringWriter();
//  }
//
//  /**
//   * Tests that the default contractor creates a GameInputReader object.
//   */
//  @Test
//  void testDefaultConstructor() {
//    GameInputReader reader = new GameInputReader();
//    assertNotNull(reader);
//  }
//
//  /**
//   * Test that the overloaded constructor accepts the input and output parameters.
//   */
//  @Test
//  void testOverloadedConstructor() {
//    input = new StringReader("Hello");
//    inputReader = new GameInputReader(input, this.output);
//    assertNotNull(inputReader);
//  }
//
//  /**
//   * Test validation of a single-word and full-word commands.
//   * It also makes sure the command are recognized as valid commands.
//   */
//  @Test
//  public void testValidateSingleWordCommands() {
//    input = new StringReader("");
//    inputReader = new GameInputReader(input, output);
//
//    // Validate single letter
//    assertTrue(inputReader.validateInput("N"));
//    assertTrue(inputReader.validateInput("S"));
//    assertTrue(inputReader.validateInput("E"));
//    assertTrue(inputReader.validateInput("W"));
//    assertTrue(inputReader.validateInput("I"));
//    assertTrue(inputReader.validateInput("L"));
//    assertTrue(inputReader.validateInput("Q"));
//
//    // Validate entire word
//    assertTrue(inputReader.validateInput("NORTH"));
//    assertTrue(inputReader.validateInput("SOUTH"));
//    assertTrue(inputReader.validateInput("EAST"));
//    assertTrue(inputReader.validateInput("WEST"));
//    assertTrue(inputReader.validateInput("INVENTORY"));
//    assertTrue(inputReader.validateInput("LOOK"));
//    assertTrue(inputReader.validateInput("QUIT"));
//    assertTrue(inputReader.validateInput("SAVE"));
//    assertTrue(inputReader.validateInput("RESTORE"));
//  }
//
//  /**
//   * Test validation method for invalid commands.
//   * It checks for invalid single-word and full-word commands.
//   */
//  @Test
//  void testValidateInvalidCommands() {
//    input = new StringReader("");
//    inputReader = new GameInputReader(input, this.output);
//
//    // Test random invalid letters
//    assertFalse(inputReader.validateInput("Z"));
//    assertFalse(inputReader.validateInput("H"));
//    assertFalse(inputReader.validateInput("M"));
//    assertFalse(inputReader.validateInput("P"));
//    assertFalse(inputReader.validateInput("G"));
//
//    // Test random invalid words
//    assertFalse(inputReader.validateInput("PICK"));
//    assertFalse(inputReader.validateInput("THROW"));
//    assertFalse(inputReader.validateInput("STORE"));
//    assertFalse(inputReader.validateInput("SEE"));
//    assertFalse(inputReader.validateInput("MOVE"));
//  }
//
//  /**
//   * Test reading various valid input commands.
//   * It verifies that the input reader correctly reads the input and normalizes it to the format
//   * that we use throughout our implementation.
//   */
//  @Test
//  void testReadValidInput() {
//    // Answer input
//    inputReader = new GameInputReader(new StringReader("a monalisa\n"), this.output);
//    String command = inputReader.readInput();
//    assertEquals("A MONALISA", command);
//
//    inputReader = new GameInputReader(new StringReader("answer align\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("ANSWER ALIGN", command);
//
//    // Drop input
//    inputReader = new GameInputReader(new StringReader("d key\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("D KEY", command);
//
//    inputReader = new GameInputReader(new StringReader("DROP KEY\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("DROP KEY", command);
//
//    // Examine input
//    inputReader = new GameInputReader(new StringReader("x painting\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("X PAINTING", command);
//
//    inputReader = new GameInputReader(new StringReader("examine painting\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("EXAMINE PAINTING", command);
//
//    // Inventory input
//    inputReader = new GameInputReader(new StringReader("i\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("I", command);
//
//    inputReader = new GameInputReader(new StringReader("inventory\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("INVENTORY", command);
//
//    // Look input
//    inputReader = new GameInputReader(new StringReader("l\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("L", command);
//
//    inputReader = new GameInputReader(new StringReader("look\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("LOOK", command);
//
//    // Move input
//    inputReader = new GameInputReader(new StringReader("north\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("NORTH", command);
//
//    inputReader = new GameInputReader(new StringReader("s\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("S", command);
//
//    // Restore input
//    inputReader = new GameInputReader(new StringReader("restore\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("RESTORE", command);
//
//    // Save input
//    inputReader = new GameInputReader(new StringReader("save\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("SAVE", command);
//
//    // Take input
//    inputReader = new GameInputReader(new StringReader("t bottle\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("T BOTTLE", command);
//
//    inputReader = new GameInputReader(new StringReader("take bottle\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("TAKE BOTTLE", command);
//
//    // Use input
//    inputReader = new GameInputReader(new StringReader("use mouse\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("USE MOUSE", command);
//
//    inputReader = new GameInputReader(new StringReader("u mouse\n"), this.output);
//    command = inputReader.readInput();
//    assertEquals("U MOUSE", command);
//  }
//
//  /**
//   * Test reading invalid input.
//   * Verifies that when invalid input is provided, the input reader displays an error message
//   * and prompts the user to try again.
//   */
//  @Test
//  void testReadInvalidInput() {
//    inputReader = new GameInputReader(new StringReader("\nlook\n"), this.output);
//    String command = inputReader.readInput();
//    assertTrue(output.toString().contains("Invalid Command. Please try again."));
//  }
//
//  /**
//   * Test providing a valid avatar name.
//   * @throws IOException if an I/O error occurs while reading the input
//   */
//  @Test
//  void testGetValidAvatarName() throws IOException {
//    input = new StringReader("Aligner");
//    inputReader = new GameInputReader(input, this.output);
//
//    String result = inputReader.getAvatarName();
//    assertEquals("ALIGNER", result);
//  }
//
//  /**
//   * Test providing an invalid avatar name.
//   * Verifies that the appropriate error message is raised.
//   * @throws IOException if an I/O error occurs while reading input.
//   */
//  @Test
//  void testGetInvalidAvatarName() throws IOException {
//    // Passing in an empty name;
//    input = new StringReader("\nName\n");
//    inputReader = new GameInputReader(input, this.output);
//
//    String result = inputReader.getAvatarName();
//    assertTrue(output.toString().contains("Invalid Avatar Name"));
//  }
//
//}