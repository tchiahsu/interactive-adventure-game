package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import static view.ViewUtils.getPanelFont;

public class MenuBar {
  private static final Color MAIN_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);
  private final static Color BUTTON_COLOR = new Color(220, 220, 220);
  private static final int WIDTH_SCALE = 200;
  private static final int HEIGHT_SCALE = 200;

  private JMenuItem saveMenuItem;
  private JMenuItem restoreMenuItem;
  private JMenuItem exitMenuItem;

  public JMenuBar getMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(400, 40));

    JMenu fileMenu = new JMenu("File");
    JMenuItem about = new JMenuItem("About");
    JMenuItem credits = new JMenuItem("Credits");
    this.saveMenuItem = new JMenuItem("Save");
    this.restoreMenuItem = new JMenuItem("Restore");
    this.exitMenuItem = new JMenuItem("Exit");
    //exit.addActionListener(event -> System.exit(0));

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

    return menuBar;
  }

  /**
   * Method to show a JDialog with a given description.
   * @param description : The text to display in the dialog.
   */
  public void showDialogBox(String description, String title, String imgPath) throws IOException {
    JTextArea text = new JTextArea(description, 1, 20);
    text.setFont(getPanelFont().deriveFont(Font.BOLD, 15));
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

  private void showAboutDialog(String title, String text, String imgPath) {
    JLabel aboutText = new JLabel(text, SwingConstants.CENTER);
    aboutText.setFont(getPanelFont().deriveFont(Font.BOLD, 14));
    //aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);
    aboutText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Create a dialog to display the text box
//    JDialog dialog = new JDialog();
//    dialog.setLayout(new BorderLayout(2,2));
//    dialog.setBackground(PANEL_COLOR);
//    dialog.add(aboutText, BorderLayout.CENTER);
//    dialog.setSize(300, 200);
//    dialog.setLocationRelativeTo(getMenuBar());
//    dialog.setVisible(true);

    JDialog dialog = new JDialog();
    dialog.setLayout(new BorderLayout(10, 10));
    dialog.setBackground(PANEL_COLOR);
    dialog.setTitle(title);
    dialog.setResizable(false);

    // Add the about text to the dialog's center
    dialog.add(aboutText, BorderLayout.CENTER);

    // Set the dialog size
    dialog.setSize(350, 250);
    dialog.setLocationRelativeTo(null);

    // Make the dialog visible
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setVisible(true);
  }

  private JButton createButton(String title) {
    JButton newBtn = new JButton();
    Dimension buttonSize = new Dimension(40, 20);

    newBtn.setBounds(100, 100, 250, 100);
    newBtn.setText(title);
    newBtn.setHorizontalTextPosition(JButton.CENTER);
    newBtn.setVerticalTextPosition(JButton.CENTER);
    newBtn.setFocusable(false);
    newBtn.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    newBtn.setPreferredSize(buttonSize);
    newBtn.setMinimumSize(buttonSize);
    newBtn.setMaximumSize(buttonSize);
    newBtn.setBackground(BUTTON_COLOR);

    return newBtn;
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

  public JMenuItem getSaveMenuItem() {
    return this.saveMenuItem;
  }

  public JMenuItem getRestoreMenuItem() {
    return this.restoreMenuItem;
  }

  public JMenuItem getExitMenuItem() {
    return this.exitMenuItem;
  }
}
