package view;

public class GameView implements IGameView {
  private GameBoard board;
  private ViewManager viewManager;
  private DescriptionPanel descriptionPanel;
  private InventoryPanel inventoryPanel;
  private StatusPanel statusPanel;

  /**
   * Construct a View object.
   */
  public GameView() {
    this.initializePanels();
    this.viewManager = new ViewManager(board, descriptionPanel, inventoryPanel, statusPanel);
    this.viewManager.displayView();
  }

  private void initializePanels() {
    MenuBar menuBar = new MenuBar();
    this.board = new GameBoard();
    this.board.setJMenuBar(menuBar.getMenuBar());

    this.descriptionPanel = new DescriptionPanel("HELLO");
    this.descriptionPanel.updateDescriptionPanel("HELLO");
    this.inventoryPanel = new InventoryPanel();
    this.statusPanel = new StatusPanel("You are healthy and wide awake!", "100", "0");
  }
}


