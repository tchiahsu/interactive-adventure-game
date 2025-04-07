package io;

import java.io.IOException;

public interface IOHandler {
  String read() throws IOException;
  void write(String s) throws IOException;
}
