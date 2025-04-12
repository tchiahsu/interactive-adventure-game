package view;

import static view.ViewUtils.getMainColor;
import static view.ViewUtils.getPanelFont;
import static view.ViewUtils.getScaledImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * The {@code MenuBar} class is responsible for creating and managing
 * the menu bar. It includes standard options like Save, Restore, Exit
 * as well as informational dialogs for About and Credits.
 */
public class MenuBar {
  private JMenuBar menuBar;
  private JMenuItem saveMenuItem;
  private JMenuItem restoreMenuItem;
  private JMenuItem exitMenuItem;

  /**
   * Constructs and returns a {@code JMenuBar}.
   * The menu includes "File" options like Save, Restore, Exit,
   * as well as "About" and "Credits" informational dialogs.
   * @return a {@code JMenuBar} instance.
   */
  public JMenuBar createMenuBar() {
    this.menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(400, 40));

    JMenu fileMenu = new JMenu("File");
    JMenuItem about = new JMenuItem("About");
    JMenuItem credits = new JMenuItem("Credits");
    this.saveMenuItem = new JMenuItem("Save");
    this.restoreMenuItem = new JMenuItem("Restore");
    this.exitMenuItem = new JMenuItem("Exit");

    fileMenu.setFont(getPanelFont().deriveFont(Font.PLAIN, 18));;
    fileMenu.setForeground(Color.WHITE);

    fileMenu.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        Font font = fileMenu.getFont();
        fileMenu.setFont(font.deriveFont(Font.BOLD));
      }
      @Override
      public void mouseExited(MouseEvent e) {
        Font font = fileMenu.getFont();
        fileMenu.setFont(font.deriveFont(Font.PLAIN));
      }
    });
    this.menuBar.add(fileMenu);
    this.menuBar.setBackground(getMainColor());

    about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          String about = "An immersive, object-oriented adventure game "
          + "where players solve puzzles, battle monsters, and explore dynamic, interconnected worlds";
          showDialogBox(about, "ABOUT", "/data/Resources/adventure.png");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    });
    credits.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          String creditsText = "Developed by Team TBH - Crafting adventures, one code line at a time!";
          showDialogBox(creditsText, "CREDITS", "/data/Resources/credits.png");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    });
    fileMenu.add(about);
    fileMenu.add(credits);
    fileMenu.add(this.saveMenuItem);
    fileMenu.add(this.restoreMenuItem);
    fileMenu.add(this.exitMenuItem);
    return this.menuBar;
  }

  /**
   * Method to show a JDialog with a given description.
   * @param description : The text to display in the dialog.
   */
  public void showDialogBox(String description, String title, String imgPath) throws IOException {
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
    JOptionPane.showMessageDialog(null, text, title,
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(scaledImage));

  }

  /**
   * Method that returns the SaveMenuItem.
   */
  public JMenuItem getSaveMenuItem() {
    return this.saveMenuItem;
  }

  /**
   * Method that returns the RestoreMenuItem.
   */
  public JMenuItem getRestoreMenuItem() {
    return this.restoreMenuItem;
  }

  /**
   * Method that returns the ExitMenuItem.
   */
  public JMenuItem getExitMenuItem() {
    return this.exitMenuItem;
  }
}
