package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileInput implements IGameInput {
  private final BufferedReader reader;

  public FileInput(String file) throws IOException {
    this.reader = new BufferedReader(new FileReader(file));
  }

  @Override
  public String readInput() throws IOException {
    return this.reader.readLine().trim().toUpperCase();
  }

  @Override
  public String getAvatarName() throws IOException {
    return this.reader.readLine().trim().toUpperCase();
  }
}
