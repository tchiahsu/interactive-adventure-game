package io;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class GuiIO implements IOHandler {
  private final Queue<String> commandQueue = new LinkedList<>();
  private final StringBuilder guiOutput = new StringBuilder();

  public void addInput(String input) {
    commandQueue.offer(input);
  }

  public String getOutput() {
    return guiOutput.toString();
  }

  @Override
  public String read() throws IOException {
    return commandQueue.poll();
  }

  @Override
  public void write(String s) throws IOException {
    guiOutput.append(s).append("\n");
  }
}
