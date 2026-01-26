package com.rpg.core.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Widok Menu Głównego.
 */
public class MenuView extends JPanel {

   public MenuView(GameWindow parent) {
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(10, 0, 10, 0); // Odstępy
      gbc.gridx = 0;
      gbc.fill = GridBagConstraints.HORIZONTAL;

      // Tytuł
      JLabel titleLabel = new JLabel("JavaRPG-Core");
      titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
      gbc.gridy = 0;
      add(titleLabel, gbc);

      // Przycisk Nowa Gra
      JButton newGameBtn = new JButton("Nowa Gra");
      newGameBtn.setPreferredSize(new Dimension(200, 40));
      newGameBtn.addActionListener(e -> parent.showView(GameWindow.VIEW_CREATOR));
      gbc.gridy = 1;
      add(newGameBtn, gbc);

      // Przycisk Historia
      JButton historyBtn = new JButton("Historia Walk");
      historyBtn.setPreferredSize(new Dimension(200, 40));
      historyBtn.setEnabled(false); // Placeholder
      gbc.gridy = 2;
      add(historyBtn, gbc);

      // Przycisk Wyjście
      JButton exitBtn = new JButton("Wyjście");
      exitBtn.setPreferredSize(new Dimension(200, 40));
      exitBtn.addActionListener(e -> System.exit(0));
      gbc.gridy = 3;
      add(exitBtn, gbc);
   }
}
