package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class MenuBar {
  private static final Color MAIN_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);

  public JMenuBar getMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(400, 40));

    JMenu fileMenu = new JMenu("File");
    JMenuItem about = new JMenuItem("About");
    JMenuItem credits = new JMenuItem("Credits");
    JMenuItem saveGame = new JMenuItem("Save");
    JMenuItem restoreGame = new JMenuItem("Restore");
    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(event -> System.exit(0));

    fileMenu.setFont(getPanelFont().deriveFont(Font.PLAIN, 18));;
    fileMenu.setForeground(Color.WHITE);

    fileMenu.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        // Get the font and make it bold
        Font font = fileMenu.getFont();
        fileMenu.setFont(font.deriveFont(Font.BOLD));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        // Get the font and make it normal
        Font font = fileMenu.getFont();
        fileMenu.setFont(font.deriveFont(Font.PLAIN));
      }
    });

    menuBar.add(fileMenu);
    menuBar.setBackground(MAIN_COLOR);
    //menuBar.setOpaque(true);

    about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAboutDialog("About the game"); // Call the method to display the text box
      }
    });

    credits.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAboutDialog("credits"); // Call the method to display the text box
      }
    });

    fileMenu.add(about);
    fileMenu.add(credits);
    fileMenu.add(saveGame);
    fileMenu.add(restoreGame);
    fileMenu.add(exit);

    return menuBar;
  }

  private void showAboutDialog(String text) {
    JLabel aboutText = new JLabel(text);
    aboutText.setFont(getPanelFont().deriveFont(Font.PLAIN, 12));
    aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Create a dialog to display the text box
    JDialog dialog = new JDialog();
    dialog.setBackground(PANEL_COLOR);
    dialog.add(new JScrollPane(aboutText));
    dialog.setSize(300, 200);
    dialog.setLocationRelativeTo(getMenuBar()); // Center the dialog on the main frame
    dialog.setVisible(true);
  }

  /**
   * Gets the Aharoni font for the panel.
   *
   * @return Aharoni font if the path to the file exists, Arial font otherwise.
   */
  private Font getPanelFont() {
    File fontFile = new File("src/data/ahronbd.ttf");
    Font font;
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    } catch (Exception e) {
      font = new Font("arial", Font.PLAIN, 20);
    }
    return font;
  }


}
