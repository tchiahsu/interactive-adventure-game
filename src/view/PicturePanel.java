package view;

import javax.swing.*;
import java.awt.*;

/**
 * PicturePanel class displays the current room that the player is in.
 */
public class PicturePanel extends JPanel {

  private JLabel RoomName;

  private static final Color BACKGROUND_COLOR = new Color(240, 239, 235);
  private static final Color HEADER_COLOR = new Color(183, 187, 168);

  /**
   * Constructor for the class PicturePanel that initializes the panel.
   */
  public PicturePanel() {
    setBackground(BACKGROUND_COLOR);
    setLayout(null);

    JPanel roomNamePanel = new JPanel();
    roomNamePanel.setBackground(HEADER_COLOR);
    roomNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    RoomName = new JLabel("Room Name");
    RoomName.setFont(new Font("Arial", Font.BOLD, 22));
    RoomName.setForeground(new Color(50, 50, 50));

    roomNamePanel.add(RoomName);

    roomNamePanel.setBounds(10, 10, 140, 40);

    add(roomNamePanel);

    JPanel contentPanel = new JPanel();
    contentPanel.setBackground(BACKGROUND_COLOR);
    add(contentPanel, BorderLayout.CENTER);
  }

  /**
   * Sets the Room name and updates the display
   * @param roomName The name of the current Room
   */
  public void setRoomName(String roomName) {
    RoomName.setText(roomName);
    repaint();
  }
}