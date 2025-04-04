package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NavigationPanel extends JPanel {
  private JButton northButton;
  private JButton southButton;
  private JButton eastButton;
  private JButton westButton;


  private JButton examineButton;
  private JButton answerButton;
  private JButton takeButton;

  private static final Color BACKGROUND_COLOR = new Color(240, 239, 235);
  private static final Color BUTTON_COLOR = new Color(183, 183, 164);
  private static final Color ARROW_COLOR = new Color(183, 183, 164);

  public NavigationPanel() {
    setBackground(BACKGROUND_COLOR);
    setLayout(new BorderLayout());

    JLabel navigationLabel = new JLabel("Navigation");
    navigationLabel.setFont(new Font("Arial", Font.BOLD, 18));
    navigationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
    add(navigationLabel, BorderLayout.NORTH);

    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(BACKGROUND_COLOR);

    JPanel actionButtonsPanel = createActionButtonsPanel();

    JPanel mainContentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
    mainContentPanel.setBackground(BACKGROUND_COLOR);
    mainContentPanel.add(actionButtonsPanel);
    mainContentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    contentPanel.add(mainContentPanel, BorderLayout.CENTER);
    add(contentPanel, BorderLayout.CENTER);
  }

  /**
   * Creates the action buttons panel.
   */
  private JPanel createActionButtonsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(BACKGROUND_COLOR);

    examineButton = createButton("EXAMINE");
    answerButton = createButton("ANSWER");
    takeButton = createButton("TAKE");

    panel.add(examineButton);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(answerButton);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(takeButton);

    return panel;
  }

  /**
   * Creates an action button
   * @param text The button text
   * @return The created button
   */
  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(BUTTON_COLOR);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setFocusPainted(false);
    return button;
  }

  public void setActionButtonListeners(ActionListener examine, ActionListener take,
                                       ActionListener answer) {
    examineButton.addActionListener(examine);
    takeButton.addActionListener(take);
    answerButton.addActionListener(answer);
  }

}
