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

  private void initializePanels() {
    MenuBar menuBar = new MenuBar();
    this.board = new GameBoard();
    this.board.setJMenuBar(menuBar.getMenuBar());

    this.descriptionPanel = new DescriptionPanel("HELLO");
    this.inventoryPanel = new InventoryPanel();
    this.statusPanel = new StatusPanel("You are healthy and wide awake!", "100", "0");
    this.navigationPanel = new NavigationPanel();
    this.picturePanel = new PicturePanel("Hallway 1", "src/data/images/courtyard.png");

    this.setActionListener();
  }

  public void startView() {
    String name = JOptionPane.showInputDialog("Enter a name for your player avatar: ");
    this.controller.setPlayerName(name);
    this.viewManager.displayView();
  }

  public DescriptionPanel getDescriptionPanel() {
    return this.descriptionPanel;
  }

  public void setController(IController controller) {
    this.controller = controller;
    this.setActionListener();
  }

  private void setActionListener() {
    this.inventoryPanel.getUseBtn().addActionListener(event -> {
      String selectedItem = inventoryPanel.getInventoryList().getSelectedValue();
      if (selectedItem != null) {
        try {
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
  }
}