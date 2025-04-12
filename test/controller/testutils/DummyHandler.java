package controller.testutils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import eventhandler.IEventHandler;

/**
 * A dummy IEventHandler for testing purposes.
 */
public class DummyHandler implements IEventHandler {
  private final Queue<String> inputs = new LinkedList<>();
  private final StringBuilder outputs = new StringBuilder();

  @Override
  public String read() throws IOException {
    return inputs.isEmpty() ? "" : inputs.poll();
  }

  @Override
  public void write(String s) throws IOException {
    outputs.append(s);
  }

  /**
   * Add inputs to the input queue for processing.
   * @param command : user input.
   */
  public void addInput(String command) {
    inputs.add(command);
  }

  /**
   * Gets the output from the executed commands.
   * @return output from executed commands.
   */
  public String getOutput() {
    return outputs.toString();
  }
}
