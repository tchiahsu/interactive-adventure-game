package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * A class representing the description panel in the graphical view of the game.
 */
public class DescriptionPanel extends JPanel {
  private final JTextArea descriptionText;
  private static final Color MAIN_COLOR = new Color(40, 54, 24);
  private static final Color PANEL_COLOR = new Color(236, 240, 235);

  /**
   * Constructor for DescriptionPanel.
   * Creates a description panel with the given description.
   *
   * @param description The description to be displayed in the panel.
   */
  public DescriptionPanel(String description) {
    // Set the title
    this.setLayout(new BorderLayout(10, 10));
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(PANEL_COLOR);

    // Create a title panel for the top
    JLabel title = new JLabel("Description");
    title.setForeground(MAIN_COLOR);
    Font font = getPanelFont().deriveFont(Font.BOLD, 30);
    title.setFont(font);
    this.add(title, BorderLayout.NORTH);

    // Generate the text area
    this.descriptionText = new JTextArea();
    this.descriptionText.setEditable(false);
    this.descriptionText.setLineWrap(true);
    this.descriptionText.setWrapStyleWord(true);
    this.descriptionText.setFocusable(false);
    this.descriptionText.setFont(getPanelFont().deriveFont(Font.PLAIN, 18));
    this.descriptionText.setBackground(PANEL_COLOR);
    this.descriptionText.setAlignmentX(Component.LEFT_ALIGNMENT);

    this.add(this.descriptionText, BorderLayout.CENTER);
  }

  /**
   * Displays the given description to the panel.
   *
   * @param description The description to be displayed.
   */
  public void setCurrentDescription(String description) {
    this.descriptionText.setText(description);
  }

  /**
   * Gets the Aharoni font for the panel.
   *
   * @return Aharoni font if the path to the file exists, Arial font otherwise.
   */
  private Font getPanelFont() {
    File fontFile = new File("src/data/ahronbd.ttf");
    Font font;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    } catch (Exception e) {
      font = new Font("arial", Font.PLAIN, 14);
    }

    return font;
  }
}
