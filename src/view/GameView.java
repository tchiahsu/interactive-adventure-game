package view;

import javax.swing.*;
import java.awt.*;

public class GameView implements IView {
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color HEADER_COLOR = new Color(40, 54, 24);
  private static final int PANEL_SPACING = 0;

  // Gameboard where components will be placed on
  private final GameBoard board;

  // Panel components for the gameboard
//  private final PicturePanel picturePanel;
//  private final DescriptionPanel descriptionPanel;
//  private final InventoryPanel inventoryPanel;
//  private final NavigationPanel navigationPanel;
//  private final StatusPanel statusPanel;

  /**
   * Construct a View object
   */
  public GameView() {
    this.board = new GameBoard();
    setupLayout();
    this.board.display();
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
    JPanel leftPanel = new JPanel();
    leftPanel.setBackground(new Color(255, 0, 0));

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

    return rightPanel;
  }
}


