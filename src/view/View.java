package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class View implements IView {
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color HEADER_COLOR = new Color(40, 54, 24);
  private static final int PANEL_SPACING = 10;

  // Gameboard where components will be placed on
  private final GameBoard board;

  // Panel components for the gameboard
  private final PicturePanel picturePanel;
  private final DescriptionPanel descriptionPanel;
  private final InventoryPanel inventoryPanel;
  private final NavigationPanel navigationPanel;
  private final StatusPanel statusPanel;

  /**
   * Construct a View object
   */
  public View() {
    this.board = new GameBoard();
    this.picturePanel = new PicturePanel();
    this.descriptionPanel = new DescriptionPanel();
    this.inventoryPanel = new InventoryPanel();
    this.navigationPanel = new NavigationPanel();
    this.statusPanel = new StatusPanel();
  }

  /**
   * Set up the layout and add panels to the GameBoard.
   */
  public void setupLayout() {
    // Create the main panel for the UI
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(BACKGROUND_COLOR);
    mainPanel.setBorder(BorderFactory.createEmptyBorder());

    // Set up header
    JPanel headerPanel = setHeaderPanel();

    // Set a left and right panel
    JPanel centerPanel = new JPanel(new GridLayout(1, 2,PANEL_SPACING, PANEL_SPACING));
    centerPanel.add(setLeftPanel());
    centerPanel.add(setRightPanel());

    // Create the main panel
    mainPanel.add(headerPanel, BorderLayout.NORTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // Set the content pane
    board.setContentPane(mainPanel);
  }

  /**
   * Gets the instance of the gameboard.
   * @return instance of gamboard
   */
  public GameBoard getBoard() {
    return this.board;
  }

  /**
   * Create a header panel with title.
   *
   * @return the new header panel
   */
  private JPanel setHeaderPanel() {
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    headerPanel.setBackground(HEADER_COLOR);
    headerPanel.setPreferredSize(new Dimension(30, 30));

    return headerPanel;
  }

  /**
   * Create the left panel with the picture and the description.
   *
   * @return the new left panel
   */
  private JPanel setLeftPanel() {
    JPanel leftPanel = new JPanel(new BorderLayout(0, PANEL_SPACING));
    leftPanel.setBackground(new Color(255, 0, 0));

    picturePanel.setPreferredSize(new Dimension(600, 350));
    leftPanel.add(picturePanel, BorderLayout.CENTER);

    descriptionPanel.setPreferredSize(new Dimension(800, 150));
    leftPanel.add(descriptionPanel, BorderLayout.SOUTH);

    return leftPanel;
  }

  /**
   * Create the right panel with the status, inventory and navigation.
   *
   * @return the new right panel
   */
  private JPanel setRightPanel() {
    JPanel rightPanel = new JPanel();
    rightPanel.setBackground(new Color(0, 0, 255));
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

    // Configure the status panel
    statusPanel.setPreferredSize(new Dimension(200, 120)); //status panel
    statusPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACING, PANEL_SPACING,
      PANEL_SPACING, PANEL_SPACING));
    statusPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 120));

    // Configure the inventory panel
    inventoryPanel.setPreferredSize(new Dimension(370, 310));
    inventoryPanel.setMaximumSize(new Dimension(370, 310));
    inventoryPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACING, PANEL_SPACING,
      PANEL_SPACING, PANEL_SPACING));

    // Configure navigation panel
    navigationPanel.setPreferredSize(new Dimension(370, 150));
    navigationPanel.setMaximumSize(new Dimension(370, 150));

    // Add components
    rightPanel.add(statusPanel);
    rightPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SPACING)));
    rightPanel.add(inventoryPanel);
    rightPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SPACING)));
    rightPanel.add(navigationPanel);

    return rightPanel;
  }
}


