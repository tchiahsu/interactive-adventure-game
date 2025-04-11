package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import static view.ViewUtils.getPanelFont;

public class StatusPanel extends JPanel {
  private JTextArea currentStatus;
  private JLabel currentHealth;
  private JLabel currentScore;
  private static final Color MAIN_COLOR = new Color(40, 54, 24);
  private static final Color PANEL_COLOR = new Color(236, 240, 235);

  /**
   * Constructor for StatusPanel.
   * Creates a description panel with the given description.
   */
  public StatusPanel() {
    this.setLayout(new GridBagLayout());
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(PANEL_COLOR);

    Font boldFont = getPanelFont().deriveFont(Font.BOLD, 16);
    Font plainFont = getPanelFont().deriveFont(Font.PLAIN, 16);

    // Headers
    JLabel statusTitle = new JLabel("Status:");
    statusTitle.setFont(boldFont);
    statusTitle.setForeground(MAIN_COLOR);

    JLabel healthTitle = new JLabel("Health:");
    healthTitle.setFont(boldFont);
    healthTitle.setForeground(MAIN_COLOR);

    JLabel scoreTitle = new JLabel("Score:");
    scoreTitle.setFont(boldFont);
    scoreTitle.setForeground(MAIN_COLOR);

    this.currentStatus = createTextArea(14);
    this.currentHealth = new JLabel();
    this.currentScore = new JLabel();

    this.currentHealth.setPreferredSize(new Dimension(50, 20));
    this.currentHealth.setFont(plainFont);
    this.currentHealth.setAlignmentX(LEFT_ALIGNMENT);

    currentScore.setPreferredSize(new Dimension(50, 20));
    currentScore.setAlignmentX(LEFT_ALIGNMENT);
    this.currentScore.setFont(plainFont);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // HEALTH
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.25;
    this.add(healthTitle, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0.5;
    this.add(this.currentHealth, gbc);

    // SCORE
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.25;
    this.add(scoreTitle, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 0.5;
    this.add(this.currentScore, gbc);

    //STATUS
    gbc.gridx = 0;
    gbc.gridy = 2;
    this.add(statusTitle, gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    this.add(this.currentStatus, gbc);
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
    textArea.setBackground(PANEL_COLOR);
    textArea.setAlignmentX(LEFT_ALIGNMENT);
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
