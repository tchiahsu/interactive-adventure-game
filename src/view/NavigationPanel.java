package view;

import static view.ViewUtils.getButtonColor;
import static view.ViewUtils.getMainColor;
import static view.ViewUtils.getPanelColor;
import static view.ViewUtils.getPanelFont;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that represents a NavigationPanel and extends {@code JPanel}.
 */
public class NavigationPanel extends JPanel {
  private final JButton examineBtn;
  private final JButton takeBtn;
  private final JButton answerBtn;
  private final JButton northBtn;
  private final JButton westBtn;
  private final JButton eastBtn;
  private final JButton southBtn;


  /**
   * Constructs a NavigationPanel with movement controls and
   * action buttons (Take, Examine, Answer).
   */
  public NavigationPanel() {
    this.setLayout(new BorderLayout(5, 5));
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(getPanelColor());

    JLabel title = new JLabel("Navigation");
    title.setForeground(getMainColor());
    Font font = getPanelFont().deriveFont(Font.BOLD, 20);
    title.setFont(font);
    this.add(title, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

    this.takeBtn = createButton("Take");
    this.examineBtn = createButton("Examine");
    this.answerBtn = createButton("Answer");

    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(takeBtn);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    buttonPanel.add(examineBtn);
    buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    buttonPanel.add(answerBtn);
    buttonPanel.add(Box.createVerticalGlue());

    this.add(buttonPanel, BorderLayout.CENTER);

    // Create buttons for movement
    this.northBtn = createMoveButton("/data/Resources/north.png");
    this.westBtn = createMoveButton("/data/Resources/west.png");
    this.eastBtn = createMoveButton("/data/Resources/east.png");
    this.southBtn = createMoveButton("/data/Resources/south.png");

    JPanel directionPanel = new JPanel();
    directionPanel.setLayout(new GridBagLayout());
    directionPanel.setBackground(getPanelColor());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.BOTH;
    directionPanel.add(new JLabel(), gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    directionPanel.add(northBtn, gbc); // North button

    gbc.gridx = 0;
    gbc.gridy = 2;
    directionPanel.add(westBtn, gbc); // West button

    gbc.gridx = 2;
    gbc.gridy = 2;
    directionPanel.add(eastBtn, gbc); // East button

    gbc.gridx = 1;
    gbc.gridy = 3;
    directionPanel.add(southBtn, gbc); // South button

    gbc.gridx = 1;
    gbc.gridy = 4;
    directionPanel.add(new JLabel(), gbc);

    int size = 150;
    directionPanel.setPreferredSize(new Dimension(size, size));
    directionPanel.setMaximumSize(new Dimension(size, size));

    this.add(directionPanel, BorderLayout.EAST);

  }

  /**
   * Returns the Examine button that examines the elements
   * in the room.
   */
  public JButton getExamineBtn() {
    return examineBtn;
  }

  /**
   * Returns the Take button that takes an item from Room
   * and adds to the Player's inventory.
   */
  public JButton getTakeBtn() {
    return takeBtn;
  }

  /**
   * Returns the Answer button that takes input from user
   * to answer.
   */
  public JButton getAnswerBtn() {
    return answerBtn;
  }

  /**
   * Creates and returns a directional button with an image.
   * @param image Image of the move button
   */
  private JButton createMoveButton(String image) {
    JButton newBtn = new JButton();

    try {
      Image img = ImageIO.read(getClass().getResource(image));
      int width = 30;
      int height = 30;
      Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

      newBtn.setIcon(new ImageIcon(resizedImg));
    } catch (Exception ex) {
      System.out.println("Image not found");
    }
    Dimension buttonSize = new Dimension(28, 28);

    newBtn.setBounds(20, 20, 20, 20);
    newBtn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    newBtn.setFocusable(false);
    newBtn.setBorderPainted(false);
    newBtn.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    newBtn.setBackground(getPanelColor());
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);
    return newBtn;
  }

  /**
   * Method that creates a button.
   * @param title Title of the button
   */
  private JButton createButton(String title) {
    JButton newBtn = new JButton();
    Dimension buttonSize = new Dimension(100, 30);

    newBtn.setBounds(100, 100, 250, 100);
    newBtn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    newBtn.setText(title);
    newBtn.setHorizontalTextPosition(JButton.CENTER);
    newBtn.setVerticalTextPosition(JButton.CENTER);
    newBtn.setFocusable(false);
    newBtn.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    newBtn.setBackground(getButtonColor());
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);
    return newBtn;
  }

  /**
   * Returns the button that moves North.
   */
  public JButton getNorthBtn() {
    return this.northBtn;
  }

  /**
   * Returns the button that moves South.
   */
  public JButton getSouthBtn() {
    return this.southBtn;
  }

  /**
   * Returns the button that moves West.
   */
  public JButton getWestBtn() {
    return this.westBtn;
  }

  /**
   * Returns the button that moves East.
   */
  public JButton getEastBtn() {
    return this.eastBtn;
  }
}
