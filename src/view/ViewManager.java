package view;

import java.awt.*;

import javax.swing.*;

public class ViewManager {
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color PANEL_COLOR = new Color(236, 240, 235);

  private GameBoard board;
  private DescriptionPanel descriptionPanel;
  private InventoryPanel inventoryPanel;
  private StatusPanel statusPanel;
  private PicturePanel picturePanel;

  /**
   * Constructs a class that manages the layout of the gameboard.
   *
   * @param board : the gameboard component.
   * @param descriptionPanel : the description panel component.
   * @param inventoryPanel : the inventory panel component.
   */
  public ViewManager(GameBoard board, DescriptionPanel descriptionPanel,
                     InventoryPanel inventoryPanel, StatusPanel statusPanel, PicturePanel picturePanel) {
    this.board = board;
    this.descriptionPanel = descriptionPanel;
    this.inventoryPanel = inventoryPanel;
    this.statusPanel = statusPanel;
    this.picturePanel = picturePanel;
  }

  /**
   * Set up the layout and add panels to the gameboard.
   */
  public void displayView() {
    // Main Panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridBagLayout());
    GridBagConstraints mainGC = new GridBagConstraints();

    // Define Grid Constraints
    mainGC.gridx = 0;
    mainGC.gridy = 0;
    mainGC.gridwidth = 1;
    mainGC.gridheight = 1;
    mainGC.weightx = 0.60;
    mainGC.weighty = 100;
    mainGC.insets = new Insets(8, 8, 8, 0);
    mainGC.fill = GridBagConstraints.BOTH;

    // Create JPanel
    JPanel leftPanel = new JPanel();
    leftPanel.setBackground(BACKGROUND_COLOR);
    JPanel rightPanel = new JPanel();
    rightPanel.setBackground(BACKGROUND_COLOR);

    // Add JPanel
    mainPanel.add(leftPanel, mainGC);
    mainGC.insets = new Insets(8, 0, 8, 8);
    mainGC.gridx = 1;
    mainGC.weightx = 0.40;
    mainPanel.add(rightPanel, mainGC);

    mainPanel.setBackground(BACKGROUND_COLOR);

    this.setupLeftPanel(leftPanel);
    this.setupRightPanel(rightPanel);

    // Add Main Panel to Board
    this.board.add(mainPanel);
    this.board.setContentPane(mainPanel);
    this.board.display();
  }

  /**
   * Set up the left panel with its components.
   *
   * @param leftPanel : the right panel container.
   */
  private void setupLeftPanel(JPanel leftPanel) {
    leftPanel.setLayout(new GridBagLayout());
    GridBagConstraints leftGC = new GridBagConstraints();

    leftGC.gridx = 0;
    leftGC.gridy = 0;
    leftGC.gridwidth = 1;
    leftGC.gridheight = 1;
    leftGC.weightx = 1;
    leftGC.weighty = 0.75;
    leftGC.insets = new Insets(8, 8, 8, 8);
    leftGC.fill = GridBagConstraints.BOTH;

    JPanel leftTopPanel = this.picturePanel;
    leftTopPanel.setPreferredSize(new Dimension(25, 50));
    leftTopPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

    JPanel leftBottomPanel = this.descriptionPanel;
    leftBottomPanel.setPreferredSize(new Dimension(25, 50));
    leftBottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

    leftPanel.add(leftTopPanel, leftGC);
    leftGC.insets = new Insets(8, 8, 8, 8);
    leftGC.gridy = 1;
    leftGC.weighty = 0.25;
    leftPanel.add(leftBottomPanel, leftGC);
  }

  /**
   * Set up the right panel and its components.
   *
   * @param rightPanel : the right panel container/
   */
  private void setupRightPanel(JPanel rightPanel) {
    rightPanel.setLayout(new GridBagLayout());
    GridBagConstraints rightGC = new GridBagConstraints();

    rightGC.gridx = 0;
    rightGC.gridy = 0;
    rightGC.gridwidth = 1;
    rightGC.gridheight = 1;
    rightGC.weightx = 1;
    rightGC.weighty = 0.2;
    rightGC.insets = new Insets(8, 8, 8, 8);
    rightGC.fill = GridBagConstraints.BOTH;

    JPanel rightTopPanel = new JPanel();
    rightTopPanel.setBackground(PANEL_COLOR);

    rightTopPanel = this.statusPanel;
    rightTopPanel.setPreferredSize(new Dimension(25, 20));
    rightTopPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

    JPanel rightMiddlePanel = this.inventoryPanel;
    rightMiddlePanel.setPreferredSize(new Dimension(25, 50));
    rightMiddlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    rightMiddlePanel.setBackground(PANEL_COLOR);

    JPanel rightBottomPanel = new JPanel();
    rightBottomPanel.setBackground(PANEL_COLOR);

    rightPanel.add(rightTopPanel, rightGC);
    rightGC.insets = new Insets(8, 8, 8, 8);
    rightGC.weighty = 0.55;
    rightGC.gridy = 1;
    rightPanel.add(rightMiddlePanel, rightGC);
    rightGC.insets = new Insets(8, 8, 8, 8);
    rightGC.weighty = 0.25;
    rightGC.gridy = 2;
    rightPanel.add(rightBottomPanel, rightGC);
  }
}
