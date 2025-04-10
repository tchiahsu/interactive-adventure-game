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
  private IViewController controller;

  private int itemIndex = -1;
  private String imagePath;
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
    this.setActionListener();
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
    MenuBar menuBar = new MenuBar();
    this.board = new GameBoard();
    this.board.setJMenuBar(menuBar.getMenuBar());

    this.descriptionPanel = new DescriptionPanel();
    this.inventoryPanel = new InventoryPanel();
    this.statusPanel = new StatusPanel();
    this.navigationPanel = new NavigationPanel();
    this.picturePanel = new PicturePanel();
  }

  private void setActionListener() {
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

    setNavigationPanelActionListener();
  }

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

  public void showSelectionDialog(String title, String[] items) {
    this.itemIndex = JOptionPane.showOptionDialog(null,
            "Select", title, JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, items, null);
  }

  /**
   * Method that returns the InventoryPanel.
   */
  @Override
  public InventoryPanel getInventoryPanel() {
    return this.inventoryPanel;
  }

  @Override
  public void updateView() {
    this.viewManager.setCurrentState(this.controller.getCurrentState());
  }

  @Override
  public void showBlockedPopUp(String s) {
    JFrame popUp = new JFrame();
    JOptionPane.showMessageDialog(popUp, s, "Path Blocked!", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showItemUsePopUp(String s) {
    JFrame popUp = new JFrame();
    JOptionPane.showMessageDialog(popUp, s, "Using: " + itemName, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showPopUp(String s) {
    JFrame popUp = new JFrame();
    popUp.setSize(500, 500);
    popUp.setLocationRelativeTo(null);
    popUp.setLayout(new FlowLayout());
    String imagePath = this.imagePath;
    JLabel pictureLabel = new JLabel();
    JLabel descriptionLabel = new JLabel(s);
    try {
      Image image = ImageIO.read(getClass().getResource(imagePath));
      Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
      pictureLabel.setIcon(new ImageIcon(scaledImage));
      popUp.add(pictureLabel);
    } catch (IOException e) {
      e.printStackTrace();
    }
    popUp.add(descriptionLabel);
    popUp.setVisible(true);
  }

  public String[] getRoomItems() {
    String roomItemNames = this.controller.getCurrentRoomItems()[0];
    return roomItemNames.split(", ");
  }

  public String[] getAllExaminableObjects() {
    String[] examinableObjects = this.controller.getExaminableObjects();
    return examinableObjects;
  }
}



// 6 buttons
//inventory - drop use, inspect
//navigation - take, examine, answer

//drop, use, inspect, - message
// take, examine - list
// answer - input

//pop up - you cannot move in that direction
