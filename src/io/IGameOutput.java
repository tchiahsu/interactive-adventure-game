package io;

import java.io.IOException;

public interface IGameOutput {
  void append(String text) throws IOException;
}
