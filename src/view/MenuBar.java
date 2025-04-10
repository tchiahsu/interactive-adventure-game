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
  private static final int WIDTH_SCALE = 300;
  private static final int HEIGHT_SCALE = 175;

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
          showExitDialog("Game Summary", "/data/Resources/nighty_night.png");
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
    aboutText.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);
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

  private void showExitDialog(String text, String imgPath) throws IOException {
    JLabel gameSummary = new JLabel(text);
    gameSummary.setFont(getPanelFont().deriveFont(Font.PLAIN, 14));
    gameSummary.setAlignmentX(Component.CENTER_ALIGNMENT);
    gameSummary.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Load and scale the image
    BufferedImage image = ImageIO.read(getClass().getResource(imgPath));
    Image scaledImage = getScaledImage(image);
    JLabel imageLabel = new JLabel();
    imageLabel.setIcon(new ImageIcon(scaledImage));
    imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // okay button
    JButton exitButton = createButton("OKAY");
    exitButton.addActionListener(event -> System.exit(0));
    exitButton.setPreferredSize(new Dimension(50, 20));

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout(20, 20));
    contentPanel.setBackground(PANEL_COLOR);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Create a subpanel
    JPanel messagePanel = new JPanel();
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
    messagePanel.setBackground(PANEL_COLOR);
    messagePanel.add(gameSummary);  // Add game summary text
    messagePanel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Create the dialog and set layout
    JDialog dialog = new JDialog();
    dialog.setBackground(PANEL_COLOR);
    dialog.setLayout(new BorderLayout());

    // Add components to the dialog
    dialog.add(messagePanel, BorderLayout.CENTER);
    dialog.add(imageLabel, BorderLayout.WEST);
    dialog.add(exitButton, BorderLayout.SOUTH);

    // Set dialog size and visibility
    dialog.setSize(450, 250);
    dialog.setLocationRelativeTo(null);
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

}
