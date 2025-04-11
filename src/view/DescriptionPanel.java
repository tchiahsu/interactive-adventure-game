package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import static view.ViewUtils.getButtonColor;
import static view.ViewUtils.getMainColor;
import static view.ViewUtils.getButtonColor;
import static view.ViewUtils.getPanelColor;
import static view.ViewUtils.getPanelFont;

/**
 * A class representing the description panel in the graphical view of the game.
 */
public class DescriptionPanel extends JPanel {
  private final JTextArea descriptionText;

  /**
   * Constructor for DescriptionPanel.
   * Creates a description panel with the given description.
   */
  public DescriptionPanel() {
    // Set the title
    this.setLayout(new BorderLayout(10, 10));
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(getPanelColor());

    // Create a title panel for the top
    JLabel title = new JLabel("Description");
    title.setForeground(getMainColor());
    Font font = getPanelFont().deriveFont(Font.BOLD, 20);
    title.setFont(font);
    this.add(title, BorderLayout.NORTH);

    // Generate the text area
    this.descriptionText = new JTextArea();
    this.descriptionText.setEditable(false);
    this.descriptionText.setLineWrap(true);
    this.descriptionText.setWrapStyleWord(true);
    this.descriptionText.setFocusable(false);
    this.descriptionText.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    this.descriptionText.setBackground(getPanelColor());
    this.descriptionText.setAlignmentX(Component.LEFT_ALIGNMENT);

    this.add(this.descriptionText, BorderLayout.CENTER);
  }

  /**
   * Displays the given description to the panel.
   *
   * @param description The description to be displayed.
   */
  public void updateDescriptionPanel(String description) {
    this.descriptionText.setText(description);
  }
}
