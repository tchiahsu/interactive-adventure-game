package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutput implements IGameOutput {
  private final BufferedWriter output;

  public FileOutput(String file) throws IOException {
    this.output = new BufferedWriter(new FileWriter(file, true));
  }

  @Override
  public void append(String description) throws IOException {
    this.output.write(description + "\n");
    this.output.flush();
  }
}
