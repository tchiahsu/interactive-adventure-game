package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PicturePanel extends JPanel {
  private JPanel roomPanel;
  BufferedImage image;
  private JLabel pictureLabel;
  private static final Color MAIN_COLOR = new Color(40, 54, 24);

  public PicturePanel(String roomName, String picturePath) throws IOException {
//    this.setLayout(new BorderLayout());
//    BufferedImage image = ImageIO.read(new File(picturePath));
//    this.pictureLabel = new JLabel(new ImageIcon(image));
//    this.add(this.pictureLabel, BorderLayout.CENTER);
//
//    this.roomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//    this.roomPanel.setBackground(MAIN_COLOR);
//    this.add(this.roomPanel, BorderLayout.NORTH);

    this.setLayout(new GridBagLayout());
  }

  private void setPicture(String picturePath) throws IOException {
    BufferedImage image = ImageIO.read(new File(picturePath));
    pictureLabel.setIcon(new ImageIcon(image));
  }


  public void updatePicturePanel(String roomName, String picturePath) {

  }

  public static void main(String[] args) throws IOException {
    JFrame frame = new JFrame();
    frame.setLayout(new BorderLayout());
    PicturePanel pp = new PicturePanel("Hallway 1", "src/data/images/courtyard.png");
    frame.add(pp, BorderLayout.CENTER);
    frame.setVisible(true);
  }
}
