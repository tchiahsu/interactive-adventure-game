package io;

import java.io.IOException;

public class TextOutput implements IGameOutput {

  @Override
  public void append(String text) throws IOException {
    System.out.println(text);
  }
}
