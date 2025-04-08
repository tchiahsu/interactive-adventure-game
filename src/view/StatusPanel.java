package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import static view.ViewUtils.getPanelFont;

public class StatusPanel extends JPanel {
  private JTextArea currentStatus;
  private JTextArea currentHealth;
  private JTextArea currentScore;
  private static final Color MAIN_COLOR = new Color(40, 54, 24);
  private static final Color PANEL_COLOR = new Color(236, 240, 235);

  /**
   * Constructor for StatusPanel.
   * Creates a description panel with the given description.
   */
  public StatusPanel() {
    this.setLayout(new GridLayout(3,2));
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(PANEL_COLOR);

    Font font = getPanelFont().deriveFont(Font.BOLD, 20);

    // create status, health and score headers
    JLabel statusTitle = new JLabel("STATUS :");
    JLabel healthTitle = new JLabel("HEALTH :");
    JLabel scoreTitle = new JLabel("SCORE :");

    //Status
    statusTitle.setForeground(MAIN_COLOR);
    statusTitle.setFont(font);
    this.add(statusTitle);

    this.currentStatus = new JTextArea();
    this.currentStatus.setEditable(false);
    this.currentStatus.setLineWrap(true);
    this.currentStatus.setWrapStyleWord(true);
    this.currentStatus.setFocusable(false);
    this.currentStatus.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    this.currentStatus.setBackground(PANEL_COLOR);
    this.currentStatus.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    this.add(this.currentStatus);

    //Health
    healthTitle.setForeground(MAIN_COLOR);
    healthTitle.setFont(font);
    this.add(healthTitle);

    this.currentHealth = new JTextArea();
    this.currentHealth.setEditable(false);
    this.currentHealth.setLineWrap(true);
    this.currentHealth.setWrapStyleWord(true);
    this.currentHealth.setFocusable(false);
    this.currentHealth.setFont(getPanelFont().deriveFont(Font.PLAIN, 20));
    this.currentHealth.setBackground(PANEL_COLOR);
    this.currentHealth.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    this.add(this.currentHealth);

    //Score
    scoreTitle.setForeground(MAIN_COLOR);
    scoreTitle.setFont(font);
    this.add(scoreTitle);

    this.currentScore = new JTextArea();
    this.currentScore.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    this.currentScore.setEditable(false);
    this.currentScore.setLineWrap(true);
    this.currentScore.setLineWrap(true);
    this.currentScore.setWrapStyleWord(true);
    this.currentScore.setFocusable(false);
    this.currentScore.setFont(getPanelFont().deriveFont(Font.PLAIN, 20));
    this.currentScore.setBackground(PANEL_COLOR);

    this.add(this.currentScore);

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
