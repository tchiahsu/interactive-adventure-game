package view;

import static view.ViewUtils.createButton;
import static view.ViewUtils.getButtonColor;
import static view.ViewUtils.getMainColor;
import static view.ViewUtils.getPanelColor;
import static view.ViewUtils.getPanelFont;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Class that represents an InventoryPanel and extends {@code JPanel}.
 */
public class InventoryPanel extends JPanel {
  private final JButton inspectBtn;
  private final JButton useBtn;
  private final JButton dropBtn;
  private final JList<String> inventoryList;
  private DefaultListModel<String> listModel;

  /**
   * Constructs an InventoryPanel with a titled layout, inventory list, and
   * action buttons (Inspect, Use, Drop).
   * UI components are styled using helper methods from ViewUtils.
   */
  public InventoryPanel() {
    // Set the title
    this.setLayout(new BorderLayout(10, 10));
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.setBackground(getPanelColor());

    // Create a title panel for the top
    JLabel title = new JLabel("Inventory");
    title.setForeground(getMainColor());
    Font font = getPanelFont().deriveFont(Font.BOLD, 20);
    title.setFont(font);
    this.add(title, BorderLayout.NORTH);

    // Create inventory list
    this.listModel = new DefaultListModel<>();

    this.inventoryList = new JList<>(listModel);
    inventoryList.setBorder(new EmptyBorder(5, 5, 5, 5));
    inventoryList.setFont(getPanelFont().deriveFont(Font.BOLD, 16));
    inventoryList.setBackground(getButtonColor());

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
   * Returns the JList that displays inventory items.
   * @return inventory JList component.
   */
  public JList<String> getInventoryList() {
    return inventoryList;
  }

  /**
   * Gets the Drop button to drop an item from inventory.
   * @return the drop button.
   */
  public JButton getDropBtn() {
    return this.dropBtn;
  }

  /**
   * Gets the Inspect button to inspect an item from inventory.
   * @return the inspect button.
   */
  public JButton getInspectBtn() {
    return this.inspectBtn;
  }

  /**
   * Gets the Inspect button to use an item from inventory.
   * @return the inspect button.
   */
  public JButton getUseBtn() {
    return this.useBtn;
  }

  /**
   * Returns the DefaultListModel to populate the inventory list
   * with item names.
   * @return inventory list model.
   */
  public DefaultListModel<String> getListModel() {
    return this.listModel;
  }

  /**
   * Method to add items to inventory's listModel.
   */
  public void addItemToInventory(String item) {
    this.listModel.addElement(item);
  }

  /**
   * Clears all items from the inventory list model.
   */
  public void clearItemsInInventory() {
    this.listModel.clear();
  }
}
