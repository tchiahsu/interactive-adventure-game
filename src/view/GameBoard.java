package view;

import javax.swing.*;
import java.awt.*;

/**
 * Main JFrame container where all the game components will be added.
 */
public class GameBoard extends JFrame {
  private static final int DEFAULT_WIDTH = 850;
  private static final int DEFAULT_HEIGHT = 700;
  JFrame board;

  public GameBoard() {
    this.board = new JFrame();

    this.setTitle("Adventure Game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to be deleted - quit option to be added in menu bar
    this.setBackground(Color.WHITE);
    //this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  /**
   * Shows the game board window
   */
  public void display() {
    setVisible(true);
  }
}
