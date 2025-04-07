package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A class representing the description panel in the graphical view of the game.
 */
public class PicturePanel extends JPanel {
  // Data fields
  // Add extra spaces to the room name as padding
  private static final String NAME_PADDING = "   ";
  private static final Color TEXT_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);
  private static final Color NAME_PANEL_COLOR = new Color(212, 212, 212);

  private JPanel roomPanel;
  private JLabel roomLabel;
  private JLabel pictureLabel;
  BufferedImage image;

  public PicturePanel(String roomName, String picturePath) {
    this.setLayout(new GridBagLayout());
    this.setBackground(PANEL_COLOR);
    GridBagConstraints gbc = new GridBagConstraints();

    this.roomPanel = new JPanel();
    this.roomPanel.setBackground(NAME_PANEL_COLOR);
    this.roomLabel = new JLabel(NAME_PADDING + roomName + NAME_PADDING);
    Font font = getPanelFont().deriveFont(Font.BOLD, 30);
    this.roomLabel.setFont(font);
    this.roomLabel.setForeground(TEXT_COLOR);
    roomPanel.add(roomLabel);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    this.add(roomPanel, gbc);

    try {
      image = ImageIO.read(new File(picturePath));
      this.pictureLabel = new JLabel(new ImageIcon(image));
    } catch (IOException e) {
      this.pictureLabel = new JLabel("No picture found", SwingConstants.CENTER);
      this.pictureLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 20));
    }
    gbc.gridy = 1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    this.add(pictureLabel, gbc);
  }

  public void updatePicturePanel(String roomName, String picturePath) {
    roomLabel.setText(NAME_PADDING + roomName + NAME_PADDING);

    try {
      this.pictureLabel.setText(null);
      image = ImageIO.read(new File(picturePath));
      this.pictureLabel.setIcon(new ImageIcon(image));
    } catch (IOException e) {
      this.pictureLabel.setIcon(null);
      this.pictureLabel.setText("No picture found");
      this.pictureLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 20));
    }
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
