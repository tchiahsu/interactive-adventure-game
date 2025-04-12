package eventhandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The class implements the IEventHandler class. It serves as the event handler
 * for a batch-file game.
 */
public class BatchFileHandler implements IEventHandler {
  private final BufferedReader reader;
  private final BufferedWriter writer;

  /**
   * Constructs a batch-file event handler object.
   * @param sourceFile : the file where input comes from.
   * @param targetFile : the file where output go to.
   * @throws IOException : if there is an error reading or writing.
   */
  public BatchFileHandler(String sourceFile, String targetFile) throws IOException {
    this.reader = new BufferedReader(new FileReader(sourceFile));
    this.writer = new BufferedWriter(new FileWriter(targetFile));
  }

  /**
   * Reads the next line of input from the specified source.
   * @return the line with the user input.
   * @throws IOException if an error occurs while reading input.
   */
  @Override
  public String read() throws IOException {
    return reader.readLine();
  }

  /**
   * Writes the given string in the specified target.
   * @param s : the string that need to be outputted.
   * @throws IOException if an error occurs during output.
   */
  @Override
  public void write(String s) throws IOException {
    writer.write(s);
    writer.flush();
  }
}
