package view;

import static view.ViewUtils.getPanelFont;
import static view.ViewUtils.getScaledImage;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import controller.IViewController;

/**
 * GameView class that represents the main visual of the Adventure Game.
 */
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

  private String itemName;

  /**
   * Construct a View object and initializes the game panels.
   */
  public GameView() {
    this.initializePanels();
    this.viewManager = new ViewManager(board, descriptionPanel, inventoryPanel, statusPanel,
        navigationPanel, picturePanel);
  }

  /**
   * Starts the game view, prompts for player name, and displays the user interface.
   */
  public void startView() {
    String name = JOptionPane.showInputDialog("Enter a name for your player avatar: ");
    this.controller.setPlayerName(name);
    this.viewManager.setCurrentState(this.controller.getCurrentState());
    this.viewManager.displayView();
    this.setGameName();
  }

  /**
   * Sets the controller for the view.
   * @param controller the IViewController implementation to link with.
   */
  public void setController(IViewController controller) {
    this.controller = controller;
  }

  /**
   * Method that initialises the panels and sets up the Game board.
   */
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

  /**
   * Helper method that attaches all major action listeners.
   */
  private void setActionListener() {
    setInventoryPanelActionListener();
    setNavigationPanelActionListener();
    setMenuActionListener();
  }

  /**
   * Method that handles the actions for buttons in the
   * {@code InventoryPanel} class.
   */
  public void setInventoryPanelActionListener() {
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

  /**
   * Sets up action listeners for the movement buttons in the
   * {@code NavigationPanel} class.
   */
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

  /**
   * Method that handles the actions for buttons in the
   * {@code MenuBar} class.
   */
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
   * @throws IOException error if image is not found.
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
      BufferedImage image = ImageIO.read(
              getClass().getResource("/data/Resources/correct_answer.png"));
      Image scaledImage = getScaledImage(image);
      JOptionPane.showMessageDialog(this.board, s, "ANSWER",
              JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
    }
    else {
      BufferedImage image = ImageIO.read(
              getClass().getResource("/data/Resources/incorrect_answer.png"));
      Image scaledImage = getScaledImage(image);
      JOptionPane.showMessageDialog(this.board, s, "ANSWER",
              JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
    }
  }

  /**
   * Method to show a pop-up with the description of the item to use.
   * @param s : The text to display in the dialog.
   */
  @Override
  public void showItemUsePopUp(String s) {
    JOptionPane.showMessageDialog(this.board, s, "Using: "
            + itemName, JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   *  Method to show a pop-up with an image and text.
   * @param s text message.
   * @param title title of the dialog.
   * @throws IOException if image cannot be loaded.
   */
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

  /**
   * Checks if the player is dead based on current health.
   * @return true if player health is 0 or below, false otherwise.
   */
  public boolean isPlayerAsleep() {
    Integer playerHealth = Integer.parseInt(this.controller.getCurrentState().get(4));
    return playerHealth <= 0;
  }

  /**
   * Helper method that returns the items currently in the room, parsed from the controller.
   * @return array of item names in the current room.
   */
  private String[] getRoomItems() {
    return this.controller.getRoomItems();
  }

  /**
   * Helper method that returns an array of examinable objects in the game.
   * @return String array of examinable objects.
   */
  private String[] getAllExaminableObjects() {
    String[] examinableObjects = this.controller.getExaminableObjects();
    return examinableObjects;
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
    int z = this.board.getWidth() / 2 - (x / 2);
    int w = z / y;
    String pad = "";
    pad = String.format("%" + w + "s", pad);
    this.board.setTitle(pad + this.controller.getGameName());
  }

  /**
   * Helper method that displays a dialog box with the list of items.
   * @param title Title of the dialog box
   * @param items list of items to display
   */
  private void showSelectionDialog(String title, String[] items) {
    JDialog dialog = new JDialog(this.board, title, true);
    dialog.setLayout(new BorderLayout());

    JList<String> list = new JList<>(items);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JPanel buttonPanel = new JPanel();
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");

    buttonPanel.add(okButton);
    buttonPanel.add(cancelButton);

    JScrollPane scrollPane = new JScrollPane(list);
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

  /**
   * Helper method that displays the game summary and exits the game.
   * @param description the summary to display.
   * @throws IOException if image fails to load.
   */
  private void showExitPopUp(String description) throws IOException {
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

  /**
   * Shows a game-over dialog and exits the game.
   * @throws IOException if image resource fails to load.
   */
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

  /**
   * Show a popup when the inventory of the player is full and cannot carry any more
   * items due to capacity.
   * @param s : the output string for full inventory.
   */
  @Override
  public void showFullInventoryPopUp(String s) throws IOException {
    if (s.contains("inventory is too full!")) {
      JTextArea text = new JTextArea(s, 1, 20);
      text.setFont(getPanelFont().deriveFont(Font.BOLD, 12));
      text.setWrapStyleWord(true);
      text.setLineWrap(true);
      text.setOpaque(false);
      text.setBorder(null);
      text.setFocusable(false);
      text.setEditable(false);

      BufferedImage image = ImageIO.read(getClass().getResource("/data/Resources/inventory_full.png"));
      Image scaledImage = getScaledImage(image);
      JOptionPane.showMessageDialog(this.board, text, "Checking Inventory...",
              JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
    }
  }
}

