package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class View implements IView {
  private GameBoard board;

  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color HEADER_COLOR = new Color(40, 54, 24);
  private static final int PANEL_SPACING = 10;


  // Panel components
  private PicturePanel picturePanel;
  private DescriptionPanel descriptionPanel;
  private InventoryPanel inventoryPanel;
  private NavigationPanel navigationPanel;
  private StatusPanel statusPanel;

  public View() {
    board = new GameBoard();
    initializePanels();
    setupLayout();
    board.display();
  }

  /**
   * Initialize all panel components.
   */
  private void initializePanels() {
    picturePanel = new PicturePanel();
    descriptionPanel = new DescriptionPanel();
    inventoryPanel = new InventoryPanel();
    navigationPanel = new NavigationPanel();
    statusPanel = new StatusPanel();
  }

  /**
   * Set up the layout and add panels to the GameBoard.
   */
  private void setupLayout() {
    JPanel mainPanel = new JPanel(new BorderLayout(PANEL_SPACING, PANEL_SPACING));
    mainPanel.setBackground(BACKGROUND_COLOR);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACING, PANEL_SPACING,
            PANEL_SPACING, PANEL_SPACING));

    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    headerPanel.setBackground(HEADER_COLOR);
    headerPanel.add(new JLabel("Files", JLabel.LEFT));
    ((JLabel)headerPanel.getComponent(0)).setForeground(Color.WHITE);
    ((JLabel)headerPanel.getComponent(0)).setFont(new Font("Arial", Font.BOLD, 15));

    //Border Layout
    JPanel leftPanel = new JPanel(new BorderLayout(0, PANEL_SPACING));
    leftPanel.setBackground(BACKGROUND_COLOR);
    picturePanel.setPreferredSize(new Dimension(600, 350));
    leftPanel.add(picturePanel, BorderLayout.CENTER);

    descriptionPanel.setPreferredSize(new Dimension(800, 150));
    leftPanel.add(descriptionPanel, BorderLayout.SOUTH);

    //Box layout
    JPanel rightPanel = new JPanel();
    rightPanel.setBackground(BACKGROUND_COLOR);
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

    statusPanel.setPreferredSize(new Dimension(200, 120)); //status panel
    statusPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACING, PANEL_SPACING,
            PANEL_SPACING, PANEL_SPACING));
    statusPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 120));

    inventoryPanel.setPreferredSize(new Dimension(370, 310));
    inventoryPanel.setMaximumSize(new Dimension(370, 310));
    inventoryPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACING, PANEL_SPACING,
            PANEL_SPACING, PANEL_SPACING));

    navigationPanel.setPreferredSize(new Dimension(370, 150));
    navigationPanel.setMaximumSize(new Dimension(370, 150));

    rightPanel.add(statusPanel);
    rightPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SPACING)));
    rightPanel.add(inventoryPanel);
    rightPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SPACING)));
    rightPanel.add(navigationPanel);

    JPanel centerPanel = new JPanel(new GridLayout(1, 2, PANEL_SPACING, 0));
    centerPanel.add(leftPanel);
    centerPanel.add(rightPanel);


    mainPanel.add(headerPanel, BorderLayout.NORTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    board.setContentPane(mainPanel);
  }
}


