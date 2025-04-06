package view;

import java.awt.*;

import javax.swing.*;

public class MenuBar {
  private static final Color MAIN_COLOR = new Color(40, 54, 24);

  public JMenuBar getMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    JMenuItem about = new JMenuItem("About");
    JMenuItem credits = new JMenuItem("Credits");
    JMenuItem saveGame = new JMenuItem("Save");
    JMenuItem restoreGame = new JMenuItem("Restore");
    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(event -> System.exit(0));

    menuBar.add(fileMenu);
    menuBar.setBackground(MAIN_COLOR);
    menuBar.setOpaque(true);

    fileMenu.add(about);
    fileMenu.add(credits);
    fileMenu.add(saveGame);
    fileMenu.add(restoreGame);
    fileMenu.add(exit);

    return menuBar;
  }
}
