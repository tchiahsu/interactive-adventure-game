package view;

import java.awt.*;

import javax.swing.*;

public class PicturePanel extends JPanel {
  private JLabel label;

  public PicturePanel() {
    this.label = new JLabel("Room Name");
    this.label.setFont(new Font("Comic Sans", Font.BOLD, 22));
  }
}
