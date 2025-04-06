package view;

import javax.swing.*;

import java.awt.*;

public class GameView implements IGameView {
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color MAIN_COLOR = new Color(40, 54, 24);
  private static final Color PANEL_COLOR = new Color(236, 240, 235);

  private final GameBoard board;
  private DescriptionPanel descriptionPanel;

  /**
   * Construct a View object
   */
  public GameView() {
    this.board = new GameBoard();
    this.initializeView();
    this.setupLayout();
    this.board.display();
  }

  private void initializeView() {
    String test = "This is a very long string to test that the placement wraps around when "
        + "it reaches the ends of the panel. Try resizing the panel.";
    this.descriptionPanel = new DescriptionPanel("HELLO");
    this.descriptionPanel.updateDescriptionPanel(test);
  }

  /**
   * Set up the layout and add panels to the gameboard.
   */
  private void setupLayout() {
    // Add Menu Bar to the Top
    this.board.setJMenuBar(createMenuBar());

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

    // Add Main Panel to Board
    board.add(mainPanel);


    // =================================================================
    // Add Panel to Left Side

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

    JPanel leftTopPanel = new JPanel();
    leftTopPanel.setBackground(PANEL_COLOR);
    JPanel leftBottomPanel = this.descriptionPanel;
    leftBottomPanel.setPreferredSize(new Dimension(25, 50));
    leftBottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

    leftPanel.add(leftTopPanel, leftGC);
    leftGC.insets = new Insets(8, 8, 8, 8);
    leftGC.gridy = 1;
    leftGC.weighty = 0.25;
    leftPanel.add(leftBottomPanel, leftGC);

    // =================================================================
    // Add Panel to Left Side

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
    JPanel rightMiddlePanel = new JPanel();
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

    board.setContentPane(mainPanel);
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
}


