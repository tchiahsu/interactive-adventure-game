package view;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *  The {@code ViewUtils} class provides utility methods for the GameView.
 *  It offers functionalities such as creating a button, getting specific font.
 */
public final class ViewUtils {
  private final static Color MAIN_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);
  private final static Color BUTTON_COLOR = new Color(220, 220, 220);
  private static final int WIDTH_SCALE = 100;
  private static final int HEIGHT_SCALE = 100;

  /**
   * Method that creates a button object with the given title for the button.
   * @param title the title of the button
   * @return a button if it is successfully created
   */
  public static JButton createButton(String title) {
    JButton newBtn = new JButton();
    Dimension buttonSize = new Dimension(90, 30);

    newBtn.setBounds(100, 100, 250, 100);
    newBtn.setText(title);
    newBtn.setHorizontalTextPosition(JButton.CENTER);
    newBtn.setVerticalTextPosition(JButton.CENTER);
    newBtn.setFocusable(false);
    newBtn.setFont(getPanelFont().deriveFont(Font.BOLD, 14));
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);

    return newBtn;
  }

  /**
   * Method that scales the given image to set dimensions.
   * @param image original BufferedImage to scale.
   * @return Scaled Image instance.
   */
  public static Image getScaledImage(BufferedImage image) {
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

  /**
   * Gets the Aharoni font for the panel.
   * @return Aharoni font if the path to the file exists, Arial font otherwise.
   */
  public static Font getPanelFont() {
    File fontFile = new File("/data/Resources/ahronbd.ttf");
    Font font;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    } catch (Exception e) {
      font = new Font("arial", Font.PLAIN, 14);
    }
    return font;
  }

  /**
   * Gets the Aharoni font for the panel.
   * @return Aharoni font if the path to the file exists, Arial font otherwise.
   */
  public static Font getPopUpFont() {
    File fontFile = new File("/data/Resources/ahronbd.ttf");
    Font font;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    } catch (Exception e) {
      font = new Font("arial", Font.PLAIN, 12);
    }
    return font;
  }

  /**
   * Method that returns the main color of the view.
   */
  public static Color getMainColor() {
    return MAIN_COLOR;
  }

  /**
   * Method that returns the panel color of the view.
   */
  public static Color getPanelColor() {
    return PANEL_COLOR;
  }

  /**
   * Method that returns the background color for the buttons.
   */
  public static Color getButtonColor() {
    return BUTTON_COLOR;
  }
}
