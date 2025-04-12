package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.text.WordUtils;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import static view.ViewUtils.getPanelColor;
import static view.ViewUtils.getPanelFont;

/**
 * A class representing the description panel in the graphical view of the game.
 */
public class PicturePanel extends JPanel {
  private static final int WIDTH_SCALE = 450;
  private static final int HEIGHT_SCALE = 400;
  private static final Color TEXT_COLOR = new Color(40, 54, 24);

  private JLabel roomLabel;
  private JLabel pictureLabel;
  BufferedImage image;

  /**
   * Constructs a PicturePanel that shows the current Room name and image.
   */
  public PicturePanel() {
    this.setLayout(new BorderLayout());
    this.setBackground(getPanelColor());
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.roomLabel = new JLabel();
    this.roomLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 20));
    this.roomLabel.setForeground(TEXT_COLOR);
    this.add(roomLabel, BorderLayout.NORTH);

    this.pictureLabel = new JLabel("No picture found", SwingConstants.CENTER);
    this.pictureLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 20));
    this.pictureLabel.setBorder(BorderFactory.createEmptyBorder(75, 0,0,0));
    this.add(pictureLabel, BorderLayout.CENTER);
  }

  /**
   * Updates the roomName and picture based on the parameters.
   * @param roomName Room name to show
   * @param picturePath Image to show
   */
  public void updatePicturePanel(String roomName, String picturePath) {
    roomName = WordUtils.capitalizeFully(roomName);
    roomLabel.setText(roomName);
    try {
      this.pictureLabel.setText(null);
      image = ImageIO.read(getClass().getResource(picturePath));
      Image scaledImage = getScaledImage(image);
      this.pictureLabel.setIcon(new ImageIcon(scaledImage));
    } catch (IOException e) {
      this.pictureLabel.setIcon(null);
      this.pictureLabel.setText("No picture found");
      this.pictureLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 12));
    }
  }

  /**
   * Private method that scales the given image to set dimensions.
   * @param image original BufferedImage to scale.
   * @return Scaled Image instance.
   */
  private Image getScaledImage(BufferedImage image) {
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    if (imageWidth > WIDTH_SCALE && imageHeight > HEIGHT_SCALE) {
      return image.getScaledInstance(WIDTH_SCALE, HEIGHT_SCALE, Image.SCALE_SMOOTH);
    } else if (imageWidth > WIDTH_SCALE) {
      return image.getScaledInstance(WIDTH_SCALE, imageHeight, Image.SCALE_SMOOTH);
    } else {
      return image.getScaledInstance(imageWidth, HEIGHT_SCALE, Image.SCALE_SMOOTH);
    }
  }
}
