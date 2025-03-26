package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameCommandReader {
  private Readable input;
  private Appendable output;
  private String[] data;

  public GameCommandReader() {
    this.input = new InputStreamReader(System.in);
    this.output = System.out;
  }

  public GameCommandReader(Readable input, Appendable output) {
    this.input = input;
    this.output = output;
  }

  public void getUserInput() {
    try {
      Scanner scanner = new Scanner(this.input);
      this.output.append(">>> ");
      String scannerInput = scanner.nextLine();
      List<String> wordList = this.convertStringToList(scannerInput);
      for (String word : wordList) {
        System.out.println(word);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<String> convertStringToList(String command) {
    String[] splitCommands = command.split(" ", 2);
    List<String> wordList = new ArrayList<>();

    for (String word: splitCommands) {
      wordList.add(word.trim());
    }
    return wordList;
  }

  public static void main(String [] args) throws IOException {
    GameCommandReader reader = new GameCommandReader();
    String input = "";
    while (!input.equalsIgnoreCase("Q")) {
      reader.getUserInput();
    }
  }
}

