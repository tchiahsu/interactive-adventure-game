package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 *The {@code ViewUtils} class provides utility methods for the GameView.
 *  It offers functionalities such as creating a button, getting specific font.
 */
public final class ViewUtils {

  /**
   * Method to create a button.
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
    newBtn.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);

    return newBtn;
  }

  /**
   * Gets the Aharoni font for the panel.
   * @return Aharoni font if the path to the file exists, Arial font otherwise.
   */
  public static Font getPanelFont() {
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
