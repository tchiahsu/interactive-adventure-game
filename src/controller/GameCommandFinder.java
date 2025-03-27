package controller;

import java.util.ArrayList;
import java.util.List;

import commands.ICommand;
import commands.*;

public class GameCommandFinder {

  public ICommand getCommand(String command) throws IllegalArgumentException {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty!");
    }

    List<String> commandTokens = splitCommand(command);
    String verb = commandTokens.getFirst().toUpperCase();
    String noun = commandTokens.size() > 1 ? commandTokens.get(1) : null;

    return switch (verb) {
      case "N", "S", "E", "W" -> new MoveCommand(verb);
      case "I" -> new InventoryCommand();
      case "L" -> new LookCommand();
      case "U" -> new UseCommand(noun);
      case "T" -> new TakeCommand(noun);
      case "D" -> new DropCommand(noun);
      case "X" -> new ExamineCommand(noun);
      case "A" -> new AnswerCommand(noun);
      default -> null; // Should never reach this point
    };
  }

  private List<String> splitCommand(String command) {
    String[] splitCommand = command.split(" ", 2);
    List<String> tokenList = new ArrayList<>();

    for (String token: splitCommand) {
      tokenList.add(token.trim());
    }
    return tokenList;
  }
}
