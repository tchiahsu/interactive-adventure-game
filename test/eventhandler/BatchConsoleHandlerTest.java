package eventhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;


class BatchConsoleHandlerTest {

  @Test
  void testRead() throws IOException {
    File sourceFile = File.createTempFile("input", ".txt");
    Files.write(sourceFile.toPath(), "align\nnorth\nlook\ninventory\nquit".getBytes());

    BatchConsoleHandler handler = new BatchConsoleHandler(sourceFile.getAbsolutePath());

    assertEquals("align", handler.read());
    assertEquals("north", handler.read());
    assertEquals("look", handler.read());
    assertEquals("inventory", handler.read());
    assertEquals("quit", handler.read());
  }

  @Test
  void testWrite() throws IOException {
    File targetFile = File.createTempFile("output", ".txt");
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));

    BatchConsoleHandler handler = new BatchConsoleHandler(targetFile.getAbsolutePath());
    handler.write("Object Oriented Design");

    String fileContent = output.toString();
    assertTrue(fileContent.contains("Object Oriented Design"));
  }
}