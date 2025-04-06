package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;

/**
 * A class representing the description panel in the graphical view of the game.
 */
public class DescriptionPanel extends JPanel {
  // Data fields
  // The text to be displayed
  private JTextArea descriptionText;
  // The color of the panel
  private static final Color BACKGROUND_COLOR = Color.decode("#F0EFEB");

  /**
   * Constructor for DescriptionPanel.
   * Creates a description panel with the given description.
   *
   * @param description The description to be displayed in the panel.
   */
  public DescriptionPanel(String description) {
    // Set the title
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    JLabel title = new JLabel("Description");
    Font font = getPanelFont().deriveFont(Font.BOLD, 30);
    title.setFont(font);
    title.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.add(title);
    this.add(Box.createRigidArea(new Dimension(0, 25)));

    // Set the description
    descriptionText = new JTextArea();
    setCurrentDescription(description);
  }

  /**
   * Displays the given description to the panel.
   *
   * @param description The description to be displayed.
   */
  public void setCurrentDescription(String description) {
    descriptionText.setText(description);
    descriptionText.setBackground(BACKGROUND_COLOR);
    Font font = getPanelFont().deriveFont(Font.PLAIN, 20);
    descriptionText.setFont(font);
    // Wraps the description if it's too long
    descriptionText.setLineWrap(true);
    // Wraps the word to a new line rather than splitting it by characters
    descriptionText.setWrapStyleWord(true);
    // Prevent the user from clicking the description
    descriptionText.setFocusable(false);
    descriptionText.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.add(descriptionText);
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

  /**
   * For testing.
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(750, 300);

    String test = "This is a very long string to test that the placement wraps around when " +
            "it reaches the ends of the panel. Try resizing the panel. nfdksanfdjksanfjdsajfkdn" +
            "jasvnerlavnrianjfbaufilehauffdjskafdjsahfuiaefndbvuialbuaehufriahfurialfhuidsnvaulr" +
            "awnfjriaghuiodsufaidsafnejwafnfjdisoafjdiaofjdsiaofdisoafndsaofdbsaifdbsafdhusaifhds" +
            "uiafdhsauifhdsaenarjaklgnrkangr";

    DescriptionPanel descriptionPanel = new DescriptionPanel(test);
    frame.add(descriptionPanel);
    frame.setVisible(true);

    // Test that description is replaced
//    String test2 = "Replace";
//    descriptionPanel.setCurrentDescription(test2);
  }
}
