package eventhandler;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleHandler implements IEventHandler {
  private final Scanner scanner = new Scanner(System.in);
  private final Appendable output = System.out;

  @Override
  public String read() throws IOException {
    return scanner.nextLine();
  }

  @Override
  public void write(String s) throws IOException {
    output.append(s);
  }
}
