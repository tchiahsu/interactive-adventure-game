package view;

import java.awt.*;
import java.io.File;

import javax.swing.*;

public class NavigationPanel extends JPanel {
  private final static Color MAIN_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);
  private final static Color BUTTON_COLOR = new Color(220, 220, 220);
  private final JButton examineBtn;
  private final JButton takeBtn;
  private final JButton answerBtn;
  private final JButton northBtn;
  private final JButton westBtn;
  private final JButton eastBtn;
  private final JButton southBtn;

  public NavigationPanel() {
    // Set the title
    this.setLayout(new BorderLayout(10, 10));
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(PANEL_COLOR);

    // Create a title panel for the top
    JLabel title = new JLabel("Inventory");
    title.setForeground(MAIN_COLOR);
    Font font = getPanelFont().deriveFont(Font.BOLD, 30);
    title.setFont(font);
    this.add(title, BorderLayout.NORTH);

    // Create buttons for movement


    // Create buttons for actions
  }

  private JButton createButton(String title) {
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
   *
   * @return Aharoni font if the path to the file exists, Arial font otherwise.
   */
  private Font getPanelFont() {
    File fontFile = new File("src/data/ahronbd.ttf");
    Font font;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    } catch (Exception e) {
      font = new Font("arial", Font.PLAIN, 20);
    }

    return font;
  }
}
