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
  private static final int WIDTH_SCALE = 100;
  private static final int HEIGHT_SCALE = 80;

  public JMenuBar getMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(400, 40));

    JMenu fileMenu = new JMenu("File");
    JMenuItem about = new JMenuItem("About");
    JMenuItem credits = new JMenuItem("Credits");
    JMenuItem saveGame = new JMenuItem("Save");
    JMenuItem restoreGame = new JMenuItem("Restore");
    JMenuItem exit = new JMenuItem("Exit");
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
    //menuBar.setOpaque(true);

    about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAboutDialog("About the game");
      }
    });

    credits.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAboutDialog("credits");
      }
    });

    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          showExitDialog("Game Summary", "/data/Resources/lamp.png");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
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
    dialog.setLocationRelativeTo(getMenuBar());
    dialog.setVisible(true);
  }

  private void showExitDialog(String text, String imgPath) throws IOException {
    JLabel gameSummary = new JLabel(text);
    gameSummary.setFont(getPanelFont().deriveFont(Font.PLAIN, 12));
    gameSummary.setAlignmentX(Component.CENTER_ALIGNMENT);

    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
    Image scaledImage = getScaledImage(image);
    JLabel imageLabel = new JLabel();
    imageLabel.setIcon(new ImageIcon(scaledImage));

    JButton exitButton = createButton("Exit");
    exitButton.addActionListener(event -> System.exit(0));

    // Create a dialog to display the exit box
    JDialog dialog = new JDialog();
    dialog.setBackground(PANEL_COLOR);
    dialog.setLayout(new BorderLayout());

    dialog.add(new JScrollPane(gameSummary), BorderLayout.EAST);
    dialog.add(imageLabel, BorderLayout.WEST);
    dialog.add(exitButton, BorderLayout.SOUTH);

    dialog.setSize(400, 400);
    dialog.setLocationRelativeTo(null);
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

}
