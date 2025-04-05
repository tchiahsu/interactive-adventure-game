package view;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;

public class GameView implements IGameView {
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color HEADER_COLOR = new Color(40, 54, 24);
  private static final int PANEL_SPACING = 0;
  GridBagConstraints gbc = new GridBagConstraints();

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

    this.board.setJMenuBar(createMenuBar());

    // Set a left and right panel
//    JPanel mainPanel = new JPanel(new GridBagLayout());
//    mainPanel.setBackground(Color.BLACK);
//    gbc.gridx = 0;
//    gbc.gridy = 0;
//    gbc.weightx = 1;
//    gbc.weighty = 1;
//    gbc.fill = gbc.BOTH;
//    mainPanel.add(new PicturePanel(), gbc);
//
//    gbc.gridx = 1;
//    gbc.gridy = 0;
//    gbc.weightx = 0.3;
//    gbc.weighty = 0.3;
//    gbc.fill = gbc.BOTH;
//    mainPanel.add(new PicturePanel(), gbc);
//
//    gbc.gridx = 1;
//    gbc.gridy = 1;
//    gbc.weightx = 0.3;
//    gbc.weighty = 0.3;
//    gbc.fill = gbc.BOTH;
//    mainPanel.add(new PicturePanel(), gbc);
//
//    gbc.gridx = 0;
//    gbc.gridy = 1;
//    gbc.weightx = 0.3;
//    gbc.weighty = 0.3;
//    gbc.fill = gbc.BOTH;
//    mainPanel.add(new PicturePanel(), gbc);
//
//    gbc.gridx = 1;
//    gbc.gridy = 2;
//    gbc.weightx = 0.3;
//    gbc.weighty = 0.3;
//    gbc.fill = gbc.BOTH;
//    mainPanel.add(new PicturePanel(), gbc);

    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBackground(Color.BLACK);
    gbc.insets = new Insets(5, 5, 5, 5);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = gbc.gridheight;
    mainPanel.add(new PicturePanel(), gbc);

    gbc.gridx++;
    gbc.weightx = 0.3;
    gbc.weighty = 0.3;
    mainPanel.add(new PicturePanel(), gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    mainPanel.add(new PicturePanel(), gbc);

    gbc.gridx++;
    mainPanel.add(new PicturePanel(), gbc);

    gbc.gridy++;
    mainPanel.add(new PicturePanel(), gbc);



//    centerPanel.add(setLeftPanel());
//    centerPanel.add(setRightPanel());

    board.add(mainPanel, BorderLayout.CENTER);

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

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem about = new JMenuItem("About");
    JMenuItem credits = new JMenuItem("Credits");
    JMenuItem saveGame = new JMenuItem("Save");
    JMenuItem restoreGame = new JMenuItem("Restore");
    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(event -> System.exit(0));

    menuBar.add(fileMenu);
    fileMenu.add(about);
    fileMenu.add(credits);
    fileMenu.add(saveGame);
    fileMenu.add(restoreGame);
    fileMenu.add(exit);

    return menuBar;
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


