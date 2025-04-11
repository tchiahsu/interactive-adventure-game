package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import static view.ViewUtils.getMainColor;
import static view.ViewUtils.getPanelColor;
import static view.ViewUtils.getPanelFont;

public class NavigationPanel extends JPanel {
  private final JButton examineBtn;
  private final JButton takeBtn;
  private final JButton answerBtn;
  private final JButton northBtn;
  private final JButton westBtn;
  private final JButton eastBtn;
  private final JButton southBtn;
  private int itemIndex;


  public NavigationPanel() {
    // Set the title
    this.setLayout(new BorderLayout(5, 5));
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.setBackground(getPanelColor());

    // Create a title panel for the top
    JLabel title = new JLabel("Navigation");
    title.setForeground(getMainColor());
    Font font = getPanelFont().deriveFont(Font.BOLD, 25);
    title.setFont(font);
    this.add(title, BorderLayout.NORTH);

    // Create buttons for actions
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
    gbc.gridx = 1; // Center column
    gbc.gridy = 0;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.BOTH;
    directionPanel.add(new JLabel(), gbc); // Empty space at the top

    gbc.gridx = 1; // Center column
    gbc.gridy = 1;
    directionPanel.add(northBtn, gbc); // North button

    gbc.gridx = 0; // Left column
    gbc.gridy = 2;
    directionPanel.add(westBtn, gbc); // West button

    gbc.gridx = 2; // Right column
    gbc.gridy = 2;
    directionPanel.add(eastBtn, gbc); // East button

    gbc.gridx = 1; // Center column
    gbc.gridy = 3;
    directionPanel.add(southBtn, gbc); // South button

    // Empty space at the bottom
    gbc.gridx = 1;
    gbc.gridy = 4;
    directionPanel.add(new JLabel(), gbc);

    int size = 150;
    directionPanel.setPreferredSize(new Dimension(size, size));
    directionPanel.setMaximumSize(new Dimension(size, size));

    this.add(directionPanel, BorderLayout.EAST);

  }

  public JButton getExamineBtn() {
    return examineBtn;
  }

  public JButton getTakeBtn() {
    return takeBtn;
  }

  public JButton getAnswerBtn() {
    return answerBtn;
  }

//  // Method to add action listeners to buttons
//  public void getOptionsBox(JButton button, String title, String[] options) {
//    button.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        try {
//          showSelectionDialog(title, options);
//        } catch (IOException ex) {
//          throw new RuntimeException(ex);
//        }
//      }
//    });
//  }
//
//  /**
//   * Method to show a JDialog with a given description.
//   * @param title : The text to display in the dialog.
//   */
//  public void showSelectionDialog(String title, String[] items) throws IOException {
//    this.itemIndex = JOptionPane.showOptionDialog(null,
//            "Select", title, JOptionPane.DEFAULT_OPTION,
//            JOptionPane.INFORMATION_MESSAGE, null, items, null);
//  }
//
//  public int getItemIndex() {
//    return this.itemIndex;
//  }

  private JButton createMoveButton(String image) {
    JButton newBtn = new JButton();

    try {
      Image img = ImageIO.read(getClass().getResource(image));
      //resize image
      int width = 30;
      int height = 30;
      Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

      newBtn.setIcon(new ImageIcon(resizedImg));
    } catch (Exception ex) {
      System.out.println("Image not found");
    }

    Dimension buttonSize = new Dimension(28, 28);

    newBtn.setBounds(20, 20, 20, 20);
    newBtn.setFocusable(false);
    newBtn.setBorderPainted(false);
    newBtn.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);

    return newBtn;
  }

  private JButton createButton(String title) {
    JButton newBtn = new JButton();
    Dimension buttonSize = new Dimension(100, 30);

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

  public JButton getNorthBtn() {
    return this.northBtn;
  }

  public JButton getSouthBtn() {
    return this.southBtn;
  }

  public JButton getWestBtn() {
    return this.westBtn;
  }

  public JButton getEastBtn() {
    return this.eastBtn;
  }

  //  /**
//   * Method to show a pop-up box where user can enter answer
//   * @param  : The text that says display your answer
//   */
//  public void answerDialog(String answer) {
//    String input = JOptionPane.showInputDialog(null, "Enter your answer:");
//
//
//
//  }

  //showInputDialog	for answer
  //show list dialog box for take/examine

}
