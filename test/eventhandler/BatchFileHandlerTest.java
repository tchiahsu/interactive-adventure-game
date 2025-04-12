package eventhandler;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchFileHandlerTest {

  @Test
  void testRead() throws IOException {
    File sourceFile = File.createTempFile("input", ".txt");
    Files.write(sourceFile.toPath(), "test1\ngoing\nto\nNORTHEASTERN\nuniversity".getBytes());
    File targetFile = File.createTempFile("output",".txt");

    BatchFileHandler handler = new BatchFileHandler(sourceFile.getAbsolutePath(),
      targetFile.getAbsolutePath());

    assertEquals("test1", handler.read());
    assertEquals("going", handler.read());
    assertEquals("to", handler.read());
    assertEquals("NORTHEASTERN", handler.read());
    assertEquals("university", handler.read());
  }

  @Test
  void testWrite() throws IOException {
    File sourceFile = File.createTempFile("input", ".txt");
    File targetFile = File.createTempFile("output",".txt");

    BatchFileHandler handler = new BatchFileHandler(sourceFile.getAbsolutePath(),
      targetFile.getAbsolutePath());
    handler.write("align\nnorth\nlook\ninventory\nquit");

    List<String> writtenContent = Files.readAllLines(targetFile.toPath());

    assertEquals("align", writtenContent.getFirst());
    assertEquals("north", writtenContent.get(1));
    assertEquals("look", writtenContent.get(2));
    assertEquals("inventory", writtenContent.get(3));
    assertEquals("quit", writtenContent.get(4));
  }
}