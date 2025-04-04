package view;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
  private JLabel healthStatusLabel;
  private JLabel healthLabel;
  private JLabel scoreLabel;

  private static final Color BACKGROUND_COLOR = new Color(240, 239, 235);

  private String healthStatus; //can be ENUM maybe??
  private int health = 100; //mutable
  private int score = 0; //mutable

  //health status bar to be added
  //update and display Rank based on score

  public StatusPanel() {
    setBackground(BACKGROUND_COLOR);

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    healthStatusLabel = new JLabel("STATUS: " + healthStatus);
    healthStatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
    healthStatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    healthStatusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 0));

    healthLabel = new JLabel("HEALTH: " + health);
    healthLabel.setFont(new Font("Arial", Font.BOLD, 16));
    healthLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    healthLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));

    scoreLabel = new JLabel("SCORE: " + score);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
    scoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    scoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 0));

    add(healthStatusLabel);
    add(healthLabel);
    add(scoreLabel);
  }

  //basic setters, can be removed if fields can be directly linked to player's data
  public void setHealthStatus(String healthStatus) {
    this.healthStatus = healthStatus;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
