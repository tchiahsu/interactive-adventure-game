package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.IViewController;

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
    this.board.setJMenuBar(menuBar.getMenuBar());

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
        try {
          controller.executeCommand("EXAMINE " + selectedItem);
        } catch (IOException ex) {
          ex.printStackTrace();
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
    //TAKE BUTTON
    this.navigationPanel.getTakeBtn().addActionListener(event -> {
      try {
        String[] roomItems = this.getRoomItems();
        this.showSelectionDialog("Items you can take:", roomItems);
        if (this.itemIndex != -1) {
          //TAKE BUTTON
          this.controller.executeCommand("TAKE " + roomItems[this.itemIndex]);
          this.itemIndex = -1;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    //EXAMINE BUTTON
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

    //ANSWER BUTTON
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

  /**
   * Method that displays a dialog box that takes in user input.
   */
  public void showInputDialog(String title) {
    this.answer = JOptionPane.showInputDialog(null,
            "Enter your answer: ", title, JOptionPane.QUESTION_MESSAGE);
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

    dialog.setVisible(true);
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
    String imgPath = "/data/Resources/block.png";
    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
    Image scaledImage = getScaledImage(image);
    JOptionPane.showMessageDialog(this.board, s, "Path Blocked!",
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
  }

  /**
   * Method that shows a pop-up for the answer command.
   * @param s description to return.
   * @throws IOException error if image is not found.
   */
  public void showPopUpAnswer(String s) throws IOException {
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
   * Method to show a pop-up with a given description.
   * @param s : The text to display in the dialog.
   */
  //Bhoomika popup
  @Override
  public void showItemUsePopUp(String s) {
    //JFrame popUp = new JFrame();
    JOptionPane.showMessageDialog(this.board, s, "Using: " + itemName, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showPopUp(String s, String title) throws IOException {
    BufferedImage image = ImageIO.read(getClass().getResource(this.imagePath));
    Image scaledImage = getScaledImage(image);
    JOptionPane.showMessageDialog(this.board, s, title,
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));
  }

  public String[] getRoomItems() {
    String roomItemNames = this.controller.getCurrentRoomItems()[0];
    return roomItemNames.split(", ");
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

  public boolean isPlayerDead() {
    Integer playerHealth = Integer.parseInt(this.controller.getCurrentState().get(4));
    return playerHealth <= 0;
  }
}
