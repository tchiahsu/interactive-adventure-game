package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import commands.*;

public class GameCommandReader {
  private final Readable input;
  private final Appendable output;
  private final String[] data;

  public GameCommandReader() {
    this.input = new InputStreamReader(System.in);
    this.output = System.out;
    this.data = new String[2];
  }

  public boolean getUserInput() {
    try {
      Scanner scanner = new Scanner(this.input);
      this.output.append("To move, enter: (N)orth, (S)outh, (E)ast or (W)est.\n"
          + "Other actions: (I)nventory, (L)ook around the location, (U)se an item\n"
          + "(T)ake an item, (D)rop an item, or e(X)amine something. \n"
          + "(A)nswer a question or provide a text solution. \n"
          + "To end the game, enter (Q)uit to quit and exit.\n"
          + "Your choice: ");
      String scannerInput = scanner.nextLine();
      List<String> data = this.convertStringToList(scannerInput);
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).equalsIgnoreCase("Q")) {
          return false;
        } else {
          this.data[i] = data.get(i);
        }
      }
      return true;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public ICommand getCommand() {
    String verb = this.getVerb();
    if (verb.equalsIgnoreCase("N")
        || verb.equalsIgnoreCase("S")
        || verb.equalsIgnoreCase("E")
        || verb.equalsIgnoreCase("W")) {
      return new MoveCommand(this.getVerb());
    } else if (verb.equalsIgnoreCase("I")) {
      return new InventoryCommand();
    } else if (verb.equalsIgnoreCase("L")) {
      return new LookCommand();
    } else if (verb.equalsIgnoreCase("U")) {
      return new UseCommand(this.getNoun());
    } else if (verb.equalsIgnoreCase("T")) {
      return new TakeCommand(this.getNoun());
    } else if (verb.equalsIgnoreCase("D")) {
      return new DropCommand(this.getNoun());
    } else if (verb.equalsIgnoreCase("X")) {
      return new ExamineCommand(this.getNoun());
    } else if (verb.equalsIgnoreCase("A")) {
      return new AnswerCommand(this.getNoun());
    }
    return null;
  }

  public List<String> convertStringToList(String command) {
    String[] splitCommands = command.split(" ", 2);
    List<String> wordList = new ArrayList<>();

    for (String word: splitCommands) {
      wordList.add(word.trim());
    }
    return wordList;
  }

  public String getVerb() {
    return this.data[0];
  }

  public String getNoun() {
    return this.data[1];
  }
}

