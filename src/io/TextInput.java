package io;

import java.io.IOException;
import java.util.Scanner;

public class TextInput implements IGameInput {
  private final Scanner scanner;

  public TextInput() {
    this.scanner = new Scanner(System.in);
  }

  @Override
  public String readInput() throws IOException {
    return scanner.nextLine().trim().toUpperCase();
  }

  @Override
  public String getAvatarName() throws IOException {
    System.out.println("Enter a name for your player avatar: ");
    return scanner.nextLine().trim().toUpperCase();
  }
}
