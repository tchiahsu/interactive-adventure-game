package view;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Main JFrame container where all the game components will be added.
 */
public class GameBoard extends JFrame {
  private static final int DEFAULT_WIDTH = 850;
  private static final int DEFAULT_HEIGHT = 700;
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private final String logoPath = "src/data/Resources/adventure.png";

  /**
   * Constructs a gameboard instance.
   */
  public GameBoard() {
    ImageIcon logo = new ImageIcon(logoPath);

    this.setIconImage(logo.getImage());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(BACKGROUND_COLOR);
    this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    this.setResizable(false);
  }

  /**
   * Shows the game board window.
   */
  public void display() {
    this.setVisible(true);
  }
}
