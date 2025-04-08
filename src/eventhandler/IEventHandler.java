package eventhandler;

import java.io.IOException;

public interface IEventHandler {
  String read() throws IOException;
  void write(String s) throws IOException;
}
