package eventhandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BatchConsoleHandler implements IEventHandler {
  private final BufferedReader reader;
  private final Appendable output = System.out;

  public BatchConsoleHandler(String sourceFile) throws IOException {
    this.reader = new BufferedReader(new FileReader(sourceFile));
  }

  @Override
  public String read() throws IOException {
    return reader.readLine();
  }

  @Override
  public void write(String s) throws IOException {
    output.append(s);
  }

  @Override
  public void setCommandAction(String action) {

  }
}
