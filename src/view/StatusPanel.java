package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import static view.ViewUtils.getMainColor;
import static view.ViewUtils.getPanelColor;
import static view.ViewUtils.getPanelFont;

public class StatusPanel extends JPanel {
  private JTextArea currentStatus;
  private JTextArea currentHealth;
  private JTextArea currentScore;

  /**
   * Constructor for StatusPanel.
   * Creates a description panel with the given description.
   */
  public StatusPanel() {
    this.setLayout(new GridLayout(3,2));
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(getPanelColor());

    Font font = getPanelFont().deriveFont(Font.BOLD, 20);

    // create status, health and score headers
    JLabel statusTitle = new JLabel("STATUS :");
    JLabel healthTitle = new JLabel("HEALTH :");
    JLabel scoreTitle = new JLabel("SCORE :");

    // Status
    statusTitle.setForeground(getMainColor());
    statusTitle.setFont(font);
    this.add(statusTitle);
    this.currentStatus = createTextArea(14);
    this.add(this.currentStatus);

    // Health
    healthTitle.setForeground(getMainColor());
    healthTitle.setFont(font);
    this.add(healthTitle);
    this.currentHealth = createTextArea(20);
    this.add(this.currentHealth);

    // Score
    scoreTitle.setForeground(getMainColor());
    scoreTitle.setFont(font);
    this.add(scoreTitle);
    this.currentScore = createTextArea(20);
    this.add(this.currentScore);

  }

  /**
   * Creates a JTextArea with the specified font size.
   *
   * @param fontSize The font size to be used for the JTextArea.
   * @return The configured JTextArea.
   */
  private JTextArea createTextArea(int fontSize) {
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setFocusable(false);
    textArea.setFont(getPanelFont().deriveFont(Font.PLAIN, fontSize));
    textArea.setBackground(getPanelColor());
    textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    return textArea;
  }


  /**
   * Displays the given status of player to the panel.
   *
   * @param status The current status to be displayed.
   */
  public void updateStatus(String status) {
    this.currentStatus.setText(status);
  }

  /**
   * Displays the given health of player to the panel.
   *
   * @param health The current health to be displayed.
   */
  public void updateHealth(String health) {
    this.currentHealth.setText(health);
  }

  /**
   * Displays the given score of player to the panel.
   *
   * @param score The current score to be displayed.
   */
  public void updateScore(String score) {
    this.currentScore.setText(score);
  }
}
