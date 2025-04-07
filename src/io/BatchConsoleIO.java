package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BatchConsoleIO implements IOHandler {
  private final BufferedReader reader;
  private final Appendable output = System.out;

  public BatchConsoleIO(String sourceFile) throws IOException {
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
}
