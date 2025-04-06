package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InventoryPanel extends JPanel {
  private final static Color MAIN_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);
  private final static Color BUTTON_COLOR = new Color(220, 220, 220);
  private final JButton inspectBtn;
  private final JButton useBtn;
  private final JButton dropBtn;
  private final JList<String> inventoryList;

  public InventoryPanel() {
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

    // Create inventory list
    DefaultListModel<String> listModel = new DefaultListModel<>();

    listModel.addElement("Fish");
    listModel.addElement("Chicken");
    listModel.addElement("Cow Meat");
    listModel.addElement("Sardines");

    this.inventoryList = new JList<>(listModel);
    inventoryList.setBorder(new EmptyBorder(5, 5, 5, 5));
    inventoryList.setFont(getPanelFont().deriveFont(Font.BOLD, 16));
    inventoryList.setBackground(BUTTON_COLOR);

    this.add(inventoryList, BorderLayout.CENTER);

    // Create buttons for inventory
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

    this.inspectBtn = createButton("Inspect");
    this.useBtn = createButton("Use");
    this.dropBtn = createButton("Drop");

    buttonPanel.add(Box.createHorizontalGlue());
    buttonPanel.add(this.inspectBtn);
    buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    buttonPanel.add(this.useBtn);
    buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    buttonPanel.add(this.dropBtn);
    buttonPanel.add(Box.createHorizontalGlue());

    this.add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Creates a button object with the given title for the button.
   *
   * @param title : text displayed in button.
   * @return the newly created button.
   */
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
    newBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String command = title.toUpperCase();
        if (inventoryList.getSelectedValue() != null) {
          command = (command + " " + inventoryList.getSelectedValue()).toUpperCase();
        }
      }
    });
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
