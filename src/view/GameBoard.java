package view;

import javax.swing.*;
import java.awt.*;

/**
 * Main JFrame container where all the game components will be added.
 */
public class GameBoard extends JFrame {
  private static final int DEFAULT_WIDTH = 850;
  private static final int DEFAULT_HEIGHT = 700;
  JFrame board; // SHOULDN'T THIS BE PRIVATE?

  /**
   * Constructs a gameboard instance.
   */
  public GameBoard() {
    this.board = new JFrame();

    this.board.setTitle("Adventure Game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(Color.WHITE);
    this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    this.setResizable(false);
  }

  /**
   * Shows the game board window
   */
  public void display() {
    setVisible(true);
  }
}
