package view;

import javax.swing.*;
import java.awt.*;

public class DescriptionPanel extends JPanel {
  private JTextArea descriptionTextArea;
  private static final Color BACKGROUND_COLOR = new Color(240, 239, 235);

  /**
   * Constructor to initializes the DescriptionPanel.
   */
  public DescriptionPanel() {
    setBackground(BACKGROUND_COLOR);
    //setBorder(BorderFactory.createLineBorder(Color.GRAY));
    setLayout(new BorderLayout());

    //header
    JLabel descriptionLabel = new JLabel("Description");
    descriptionLabel.setFont(new Font("Dialog", Font.BOLD, 22));
    descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

    this.descriptionTextArea = new JTextArea();
    this.descriptionTextArea.setText("<<INITIAL ROOM DESCRIPTION>>.\n"
            + "Items you see here: <<Items in Room>>");
    this.descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
    this.descriptionTextArea.setEditable(false);
    this.descriptionTextArea.setBackground(BACKGROUND_COLOR);
    this.descriptionTextArea.setLineWrap(true);
    this.descriptionTextArea.setWrapStyleWord(true);
    this.descriptionTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(descriptionLabel, BorderLayout.NORTH);
    add(this.descriptionTextArea, BorderLayout.CENTER);
  }

  /**
   * Sets the description text.
   * @param description new room description
   */
  public void setDescription(String description) {
    descriptionTextArea.setText(description);
  }
}
