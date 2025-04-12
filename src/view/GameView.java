package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.IViewController;

import static view.ViewUtils.*;

public class GameView implements IGameView {
  private GameBoard board;
  private ViewManager viewManager;
  private DescriptionPanel descriptionPanel;
  private InventoryPanel inventoryPanel;
  private StatusPanel statusPanel;
  private NavigationPanel navigationPanel;
  private PicturePanel picturePanel;
  private MenuBar menuBar;
  private IViewController controller;

  private int itemIndex = -1;
  private String imagePath;
  private String answer;
  private final static Color PANEL_COLOR = new Color(236, 240, 235);
  private final static Color BUTTON_COLOR = new Color(220, 220, 220);

  private static final int WIDTH_SCALE = 100;
  private static final int HEIGHT_SCALE = 100;

  private String itemName;

  /**
   * Construct a View object.
   */
  public GameView() {
    this.initializePanels();
    this.viewManager = new ViewManager(board, descriptionPanel, inventoryPanel, statusPanel,
        navigationPanel, picturePanel);
  }

  public void startView() {
    String name = JOptionPane.showInputDialog("Enter a name for your player avatar: ");
    this.controller.setPlayerName(name);
    this.viewManager.setCurrentState(this.controller.getCurrentState());
    this.viewManager.displayView();
    this.setGameName();
  }

  public DescriptionPanel getDescriptionPanel() {
    return this.descriptionPanel;
  }

  public NavigationPanel getNavigationPanel() {
    return this.navigationPanel;
  }

  public void setController(IViewController controller) {
    this.controller = controller;
  }

  private void initializePanels() {
    this.menuBar = new MenuBar();
    this.board = new GameBoard();
    this.board.setJMenuBar(menuBar.createMenuBar());

    this.descriptionPanel = new DescriptionPanel();
    this.inventoryPanel = new InventoryPanel();
    this.statusPanel = new StatusPanel();
    this.navigationPanel = new NavigationPanel();
    this.picturePanel = new PicturePanel();

    this.setActionListener();
  }

  private void setActionListener() {
    setInventoryPanelActionListener();
    setNavigationPanelActionListener();
    setMenuActionListener();
  }

  public void setInventoryPanelActionListener() {
    //USE BUTTON
    this.inventoryPanel.getUseBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          this.itemName = selectedItem;
          controller.executeCommand("USE " + selectedItem);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });
    //DROP BUTTON
    this.inventoryPanel.getDropBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          controller.executeCommand("DROP " + selectedItem);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });
    //INSPECT BUTTON
    this.inventoryPanel.getInspectBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        String itemToInspect = inventoryPanel.getInventoryList().getSelectedValue();
        try {
          this.imagePath = this.controller.getImagePath(itemToInspect);
          controller.executeCommand("EXAMINE " + itemToInspect);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Method that handles the actions for buttons in the
   * {@code NavigationPanel} class.
   */
  public void setNavigationPanelActionListener() {
    setMovementActionListener();
    this.navigationPanel.getTakeBtn().addActionListener(event -> {
      try {
        String[] roomItems = this.getRoomItems();
        this.showSelectionDialog("Items you can take:", roomItems);
        if (this.itemIndex != -1) {
          this.controller.executeCommand("TAKE " + roomItems[this.itemIndex]);
          this.itemIndex = -1;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    this.navigationPanel.getExamineBtn().addActionListener(event -> {
      try {
        String[] examinableObjects = getAllExaminableObjects();
        this.showSelectionDialog("What you can examine:", examinableObjects);
        if (this.itemIndex != -1) {
          this.imagePath = this.controller.getImagePath(examinableObjects[this.itemIndex]);
          this.controller.executeCommand("EXAMINE " + examinableObjects[this.itemIndex]);
          this.itemIndex = -1;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    this.navigationPanel.getAnswerBtn().addActionListener(event -> {
      this.showInputDialog("ANSWER");
      if (this.answer != null) {
        try {
          this.controller.executeCommand("ANSWER " + this.answer);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  public void setMovementActionListener() {
    this.navigationPanel.getNorthBtn().addActionListener(event -> {
      try {
        this.controller.executeCommand("NORTH");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    this.navigationPanel.getSouthBtn().addActionListener(event -> {
      try {
        this.controller.executeCommand("SOUTH");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    this.navigationPanel.getEastBtn().addActionListener(event -> {
      try {
        this.controller.executeCommand("EAST");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    this.navigationPanel.getWestBtn().addActionListener(event -> {
      try {
        this.controller.executeCommand("WEST");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void setMenuActionListener() {
    this.menuBar.getSaveMenuItem().addActionListener(event -> {
      try {
        this.imagePath = "/data/Resources/save.png";
        this.controller.executeCommand("SAVE");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    this.menuBar.getRestoreMenuItem().addActionListener(event -> {
      try {
        this.imagePath = "/data/Resources/restore.png";
        this.controller.executeCommand("RESTORE");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    this.menuBar.getExitMenuItem().addActionListener(event -> {
      try {
        String status = this.controller.getGameSummary();
        showExitPopUp(status);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Method that displays a dialog box that takes in user input.
   */
  public void showInputDialog(String title) {
    this.answer = JOptionPane.showInputDialog(null,
            "Enter your answer: ", title, JOptionPane.QUESTION_MESSAGE);
  }

  /**
   * Method that returns the InventoryPanel.
   */
  @Override
  public InventoryPanel getInventoryPanel() {
    return this.inventoryPanel;
  }

  /**
   * Method that updates the view with the current description.
   */
  @Override
  public void updateView() {
    this.viewManager.setCurrentState(this.controller.getCurrentState());
  }

  /**
   * Method that shows a pop-up box when player tries to
   * move in a blocked path.
   * @param s String description to display.
   */
  @Override
  public void showBlockedPopUp(String s) throws IOException {
    if (s.contains("You cannot go in") || s.contains("You are being blocked")) {
      String imgPath = "/data/Resources/block.png";
      BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
      Image scaledImage = getScaledImage(image);
      JOptionPane.showMessageDialog(this.board, s, "Path Blocked!",
              JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
      }
  }

  /**
   * Method that shows a pop-up for the answer command.
   * @param s description to return.
   * @throws IOException error if image is not found.
   */
  public void showAnswerPopUp(String s) throws IOException {
    if (s.contains("SUCCESS")) {
      BufferedImage image = ImageIO.read(getClass().getResource("/data/Resources/correct_answer.png"));
      Image scaledImage = getScaledImage(image);
      JOptionPane.showMessageDialog(this.board, s, "ANSWER",
              JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
    }
    else {
      BufferedImage image = ImageIO.read(getClass().getResource("/data/Resources/incorrect_answer.png"));
      Image scaledImage = getScaledImage(image);
      JOptionPane.showMessageDialog(this.board, s, "ANSWER",
              JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
    }
  }

  /**
   * Method that displays a dialog box with the list of items.
   * @param title Title of the dialog box
   * @param items list of items to display
   */
  public void showSelectionDialog(String title, String[] items) {
    JDialog dialog = new JDialog(this.board, title, true);
    dialog.setLayout(new BorderLayout());

    JList<String> list = new JList<>(items);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(list);

    JPanel buttonPanel = new JPanel();
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");

    buttonPanel.add(okButton);
    buttonPanel.add(cancelButton);

    dialog.add(new JLabel("Select an item:"), BorderLayout.NORTH);
    dialog.add(scrollPane, BorderLayout.CENTER);
    dialog.add(buttonPanel, BorderLayout.SOUTH);

    dialog.setSize(300, 200);
    dialog.setLocationRelativeTo(this.board);

    okButton.addActionListener(e -> {
      this.itemIndex = list.getSelectedIndex();
      dialog.dispose();
    });

    cancelButton.addActionListener(e -> {
      dialog.dispose();
    });

    dialog.setResizable(false);
    dialog.setVisible(true);
  }

  public void showExitPopUp(String description) throws IOException {
    String imgPath = "/data/Resources/nighty_night.png";
    JTextArea text = new JTextArea(description, 1, 20);
    text.setFont(getPanelFont().deriveFont(Font.BOLD, 12));
    text.setWrapStyleWord(true);
    text.setLineWrap(true);
    text.setOpaque(false);
    text.setBorder(null);
    text.setFocusable(false);
    text.setEditable(false);
    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
    Image scaledImage = getScaledImage(image);
    JOptionPane.showMessageDialog(null, text, "Exit Game",
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
    System.exit(0);
  }

//  private void showExitDialog(String text, String imgPath) throws IOException {
//    JTextArea gameSummary = new JTextArea(text, 1, 30);
//    gameSummary.setFont(getPopUpFont().deriveFont(Font.PLAIN, 12));
//    gameSummary.setWrapStyleWord(true);
//    gameSummary.setLineWrap(true);
//    gameSummary.setOpaque(false);
//    gameSummary.setBorder(null);
//    gameSummary.setFocusable(false);
//    gameSummary.setEditable(false);
//
//    // Load and scale the image
//    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
//    Image scaledImage = getScaledImage(image);
//    JLabel imageLabel = new JLabel();
//    imageLabel.setIcon(new ImageIcon(scaledImage));
//    imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//    JButton exitButton = createButton("OKAY");
//    exitButton.setPreferredSize(new Dimension(200, 40));
//    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//    exitButton.addActionListener(event -> System.exit(0));
//
//    JPanel mainPanel = new JPanel();
//    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//    mainPanel.setBackground(PANEL_COLOR);
//    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//    JPanel contentPanel = new JPanel(new FlowLayout());
//    contentPanel.add(imageLabel);
//    contentPanel.add(gameSummary);
//
//    mainPanel.add(contentPanel);
//    mainPanel.add(exitButton);
//
//    // Create the dialog and set layout
//    JDialog dialog = new JDialog();
//    dialog.setBackground(PANEL_COLOR);
//    dialog.setTitle("Exit Game");
//    dialog.setLayout(new BorderLayout());
//    dialog.add(mainPanel);
//
//    dialog.setSize(450, 200);
//    dialog.setLocationRelativeTo(this.board);
//    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//    dialog.setResizable(false);
//    dialog.setVisible(true);
//  }

  private JButton createButton(String title) {
    JButton newBtn = new JButton();
    Dimension buttonSize = new Dimension(80, 20);

    newBtn.setText(title);
    newBtn.setHorizontalTextPosition(JButton.CENTER);
    newBtn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    newBtn.setVerticalTextPosition(JButton.CENTER);
    newBtn.setFocusable(false);
    newBtn.setFont(getPanelFont().deriveFont(Font.BOLD, 14));
    newBtn.setBackground(getButtonColor());
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);

    return newBtn;
  }

  public void showFullInventoryPopUp(String s) {
    if (s.contains("inventory is too full!")) {
      JTextArea text = new JTextArea(s, 1, 20);
      text.setFont(getPanelFont().deriveFont(Font.BOLD, 10));
      JOptionPane.showMessageDialog(this.board, text);
    }
  }

  /**
   * Method to show a pop-up with a given description.
   * @param s : The text to display in the dialog.
   */
  @Override
  public void showItemUsePopUp(String s) {
    JOptionPane.showMessageDialog(this.board, s, "Using: "
            + itemName, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showPopUp(String s, String title) throws IOException {
    JTextArea text = new JTextArea(s, 1, 20);
    text.setFont(getPanelFont().deriveFont(Font.BOLD, 12));
    text.setWrapStyleWord(true);
    text.setLineWrap(true);
    text.setOpaque(false);
    text.setBorder(null);
    text.setFocusable(false);
    text.setEditable(false);

    JScrollPane scroll = new JScrollPane(text);

    BufferedImage image = ImageIO.read(getClass().getResource(this.imagePath));
    Image scaledImage = getScaledImage(image);
    JOptionPane.showMessageDialog(this.board, scroll, title,
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
  }

  @Override
  public void showTextPopUp(String s) {
    JTextArea text = new JTextArea(s, 1, 20);
    text.setFont(getPanelFont().deriveFont(Font.BOLD, 10));
    JOptionPane.showMessageDialog(this.board, text);
  }

  private String[] getInventoryItems() {
    return this.controller.getInventoryItems();
  }

  public String[] getRoomItems() {
    return this.controller.getRoomItems();
  }

  public String[] getAllExaminableObjects() {
    String[] examinableObjects = this.controller.getExaminableObjects();
    return examinableObjects;
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

  /**
   * Helper method that sets and center aligns the name
   * of the game as title of the board.
   */
  private void setGameName() {
    Font f = this.board.getFont();
    FontMetrics fm = this.board.getFontMetrics(f);
    int x = fm.stringWidth("Hello Center");
    int y = fm.stringWidth(" ");
    int z = this.board.getWidth()/2 - (x/2);
    int w = z/y;
    String pad ="";
    pad = String.format("%"+w+"s", pad);
    this.board.setTitle(pad+this.controller.getGameName());
  }

  public boolean isPlayerDead() {
    Integer playerHealth = Integer.parseInt(this.controller.getCurrentState().get(4));
    return playerHealth <= 0;
  }

  public void showGameOverPopUp() throws IOException {
    String description = "You've fainted!\n" + this.controller.getGameSummary();
    String imgPath = "/data/Resources/nighty_night.png";
    JTextArea text = new JTextArea(description, 1, 20);
    text.setFont(getPanelFont().deriveFont(Font.BOLD, 14));
    text.setWrapStyleWord(true);
    text.setLineWrap(true);
    text.setOpaque(false);
    text.setBorder(null);
    text.setFocusable(false);
    text.setEditable(false);
    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
    Image scaledImage = getScaledImage(image);
    JOptionPane.showMessageDialog(null, text, "Game Over!",
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
    System.exit(0);
  }

//  public void showExitPopUp() throws IOException {
//    String description = this.controller.getGameSummary();
//    String imgPath = "/data/Resources/nighty_night.png";
//    JTextArea text = new JTextArea(description, 1, 20);
//    text.setFont(getPanelFont().deriveFont(Font.BOLD, 14));
//    text.setWrapStyleWord(true);
//    text.setLineWrap(true);
//    text.setOpaque(false);
//    text.setBorder(null);
//    text.setFocusable(false);
//    text.setEditable(false);
//    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
//    Image scaledImage = getScaledImage(image);
//    JOptionPane.showMessageDialog(null, text, "Exit Game",
//            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
//    System.exit(0);
//  }
}

