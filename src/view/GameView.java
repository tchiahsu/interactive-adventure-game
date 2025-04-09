package view;

import java.io.IOException;

import javax.swing.*;

import controller.IController;

public class GameView implements IGameView {
  private GameBoard board;
  private ViewManager viewManager;
  private DescriptionPanel descriptionPanel;
  private InventoryPanel inventoryPanel;
  private StatusPanel statusPanel;
  private NavigationPanel navigationPanel;
  private PicturePanel picturePanel;
  private IController controller;

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

  public void setController(IController controller) {
    this.controller = controller;
    this.inventorySetActionListener();
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

    this.inventorySetActionListener();
  }

  private void inventorySetActionListener() {
    String testDescription = "This is description for testing";
    String testImage = "/data/Resources/lamp.png";
    this.inventoryPanel.getUseBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          String use = "USE ";
          controller.executeCommand(use + selectedItem);
          this.inventoryPanel.showDescriptionDialog(testDescription, use, testImage);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    this.inventoryPanel.getDropBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          String drop = "DROP ";
          controller.executeCommand(drop + selectedItem);
          this.inventoryPanel.showDescriptionDialog(testDescription, drop, testImage);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    this.inventoryPanel.getInspectBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          String inspect = "EXAMINE ";
          controller.executeCommand(inspect + selectedItem);
          this.inventoryPanel.showDescriptionDialog(testDescription, inspect, testImage);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });
  }
}



// 6 buttons
//inventory - drop use, inspect
//navigation - take, examine, answer

//drop, use, inspect, - message
// take, examine - list
// answer - input

//pop up - you cannot move in that direction
