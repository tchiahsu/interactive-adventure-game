package eventhandler;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class BatchConsoleHandlerTest {
  private File sourceFile;
  private File targetFile;

  @Test
  void testRead() throws IOException {
    sourceFile = File.createTempFile("input", ".txt");
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
    targetFile = File.createTempFile("output",".txt");
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));

    BatchConsoleHandler handler = new BatchConsoleHandler(targetFile.getAbsolutePath());
    handler.write("Object Oriented Design");

    String fileContent = output.toString();
    assertTrue(fileContent.contains("Object Oriented Design"));
  }
}