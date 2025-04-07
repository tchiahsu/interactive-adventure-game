package io;

import java.io.IOException;

import view.GameView;
import view.IGameView;

public class GuiIO implements IOHandler {
  private IGameView view = new GameView();

  // THIS NEEDS TO READ THE INPUT AS A STRING!
  @Override
  public String read() throws IOException {

    // Get command from GUI
    // Get the first word to know where its going?
    // Store it somewhere for future use?
    // Return the string for processing
    System.out.println("Command: ");
    return "";
  }

  // THIS NEEDS TO DETERMINE WHERE TO SEND THE INPUT I GUESS IT BASED ON WHAT THE INPUT IS?
  @Override
  public void write(String s) throws IOException {
    // Use the first word that was saved
    // Determine where it is supposed to go?

  }
}
