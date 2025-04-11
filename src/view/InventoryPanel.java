package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static view.ViewUtils.getPanelFont;

public class InventoryPanel extends JPanel {
  private final static Color MAIN_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);
  private final static Color BUTTON_COLOR = new Color(220, 220, 220);
  private static final int WIDTH_SCALE = 100;
  private static final int HEIGHT_SCALE = 100;

  private final JButton inspectBtn;
  private final JButton useBtn;
  private final JButton dropBtn;
  private final JList<String> inventoryList;
  private DefaultListModel<String> listModel;


  public InventoryPanel() {
    String testDescription = "This is a description for testing"; //to be deleted
    String testImage = "/data/Resources/lamp.png"; //to be deleted
    // Set the title
    this.setLayout(new BorderLayout(10, 10));
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(PANEL_COLOR);

    // Create a title panel for the top
    JLabel title = new JLabel("Inventory");
    title.setForeground(MAIN_COLOR);
    Font font = getPanelFont().deriveFont(Font.BOLD, 20);
    title.setFont(font);
    this.add(title, BorderLayout.NORTH);

    // Create inventory list
    this.listModel = new DefaultListModel<>();

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

  public JList<String> getInventoryList() {
    return inventoryList;
  }

  public JButton getDropBtn() {
    return this.dropBtn;
  }

  public JButton getInspectBtn() {
    return this.inspectBtn;
  }

  public JButton getUseBtn() {
    return this.useBtn;
  }

  public DefaultListModel<String> getListModel() {
    return this.listModel;
  }

  // Method to add action listeners to buttons
  public void getDescriptionBox(JButton button, String message, String title, String imgPath) {
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //System.out.println("Button clicked, showing dialog...");
        try {
          showDescriptionDialog(message, title, imgPath);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    });
  }

  /**
   * Method to add items to inventory's listModel.
   */
  public void addItemToInventory(String item) {
    this.listModel.addElement(item);
  }

  public void clearItemsInInventory() {
    this.listModel.clear();
  }

  /**
   * Method to show a JDialog with a given description.
   * @param description : The text to display in the dialog.
   */
  public void showDescriptionDialog(String description, String title, String imgPath) throws IOException {
    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
    Image scaledImage = getScaledImage(image);
    JOptionPane.showMessageDialog(null, description, title,
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));

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
    newBtn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    newBtn.setText(title);
    newBtn.setHorizontalTextPosition(JButton.CENTER);
    newBtn.setVerticalTextPosition(JButton.CENTER);
    newBtn.setFocusable(false);
    newBtn.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);
    newBtn.setBackground(BUTTON_COLOR);
    return newBtn;
  }

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
