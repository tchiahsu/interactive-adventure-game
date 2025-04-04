package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InventoryPanel extends JPanel {
  private JPanel itemsPanel; //items in inventory

  private JButton examineButton;
  private JButton takeButton;
  private JButton answerButton;

  //implementation can be changed when connection with controller
  private List<String> inventoryItems = new ArrayList<>();

  private JLabel selectedItemLabel = null;
  private String selectedItem = null;

  private static final Color BACKGROUND_COLOR = new Color(240, 239, 235);
  private static final Color ITEM_COLOR = new Color(183, 183, 164);
  private static final Color SELECTION_COLOR = new Color(156, 234, 112); //have to work on how to select

  public InventoryPanel() {
    setBackground(BACKGROUND_COLOR);
    //setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    setLayout(new BorderLayout());

    setPreferredSize(new Dimension(getPreferredSize().width, 200));

    JLabel inventoryLabel = new JLabel("Inventory");
    inventoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
    inventoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
    add(inventoryLabel, BorderLayout.NORTH);

    itemsPanel = new JPanel();
    itemsPanel.setBackground(ITEM_COLOR);
    itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

    int panelWidth = 80;
    itemsPanel.setPreferredSize(new Dimension(panelWidth, 100));
    itemsPanel.setMaximumSize(new Dimension(panelWidth, Short.MAX_VALUE));
    //itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //implement adding items to inventory
    //items have to be/can be buttons that are connected with other buttons to be used later
    //just to test
    JLabel item1 = new JLabel();
    item1.setFont(new Font("Arial", Font.BOLD, 18));
    item1.setText("Lamp");

    JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    wrapperPanel.setBackground(BACKGROUND_COLOR);
    wrapperPanel.add(itemsPanel);

    JScrollPane scrollPane = new JScrollPane(wrapperPanel);
    scrollPane.setPreferredSize(new Dimension(getPreferredSize().width, 80));
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    itemsPanel.add(item1);

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setBackground(BACKGROUND_COLOR);
    buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

    answerButton = createButton("INSPECT");
    takeButton = createButton("USE");
    examineButton = createButton("DROP");

    //updateButtonState(false);

    buttonsPanel.add(answerButton);
    buttonsPanel.add(takeButton);
    buttonsPanel.add(examineButton);


    JPanel contentPanel = new JPanel(new BorderLayout(0, 5));
    contentPanel.setBackground(BACKGROUND_COLOR);
    contentPanel.add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
    contentPanel.add(buttonsPanel, BorderLayout.SOUTH);

    add(contentPanel, BorderLayout.CENTER);
  }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(ITEM_COLOR);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setFocusPainted(false);
    return button;
  }

//a feature to implement later
//make the buttons inaccessible if there are no items in inventory
//  private void updateButtonState(boolean enabled) {
//    examineButton.setEnabled(enabled);
//    takeButton.setEnabled(enabled);
//    answerButton.setEnabled(enabled);
//  }


  public void setActionButtonListeners(ActionListener examine, ActionListener take,
                                       ActionListener answer) {
    examineButton.addActionListener(examine);
    takeButton.addActionListener(take);
    answerButton.addActionListener(answer);
  }
}
