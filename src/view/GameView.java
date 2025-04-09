package view;

import java.io.IOException;

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

  public void setController(IViewController controller) {
    this.controller = controller;
    //this.setActionListener();
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

    this.setActionListener();

    //assert this.controller != null;
    this.navigationPanel.getOptionsBox(this.navigationPanel.getTakeBtn(), "TAKE", this.controller.getCurrentRoomItems());
  }

  private void setActionListener() {
    this.inventoryPanel.getUseBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          controller.executeCommand("USE" + selectedItem);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    this.inventoryPanel.getDropBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          controller.executeCommand("DROP" + selectedItem);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });

    this.inventoryPanel.getInspectBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
          controller.executeCommand("EXAMINE" + selectedItem);
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    });
  }

  /**
   * Method that returns the InventoryPanel.
   */
  @Override
  public InventoryPanel getInventoryPanel() {
    return this.inventoryPanel;
  }

}



// 6 buttons
//inventory - drop use, inspect
//navigation - take, examine, answer

//drop, use, inspect, - message
// take, examine - list
// answer - input

//pop up - you cannot move in that direction
