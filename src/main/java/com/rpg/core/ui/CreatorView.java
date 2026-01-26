package com.rpg.core.ui;

import com.rpg.core.entity.Hero;
import com.rpg.core.factory.HeroFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Widok Kreatora Postaci.
 * Pozwala użytkownikowi skonfigurować bohatera.
 */
public class CreatorView extends JPanel {

   private final JTextField nameField;
   private final JComboBox<String> weaponBox;
   private final JCheckBox fireCheck;
   private final JCheckBox critCheck;

   public CreatorView(GameWindow parent) {
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      // Tytuł
      JLabel title = new JLabel("Kreator Postaci");
      title.setFont(new Font("Arial", Font.BOLD, 24));
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 2;
      add(title, gbc);

      // Imię
      gbc.gridwidth = 1;
      gbc.gridy = 1;
      add(new JLabel("Imię Bohatera:"), gbc);

      nameField = new JTextField("Bohater", 15);
      gbc.gridx = 1;
      add(nameField, gbc);

      // Broń
      gbc.gridx = 0;
      gbc.gridy = 2;
      add(new JLabel("Wybierz Broń:"), gbc);

      String[] weapons = { "Miecz", "Topór", "Sztylet" };
      weaponBox = new JComboBox<>(weapons);
      gbc.gridx = 1;
      add(weaponBox, gbc);

      // Ulepszenia
      gbc.gridx = 0;
      gbc.gridy = 3;
      add(new JLabel("Ulepszenia:"), gbc);

      JPanel checkPanel = new JPanel(new GridLayout(2, 1));
      fireCheck = new JCheckBox("Ogniste Zaklęcie (+5 dmg)");
      critCheck = new JCheckBox("Szansa na Krytyka (20%)");
      checkPanel.add(fireCheck);
      checkPanel.add(critCheck);

      gbc.gridx = 1;
      add(checkPanel, gbc);

      // Przyciski
      JButton startBtn = new JButton("Rozpocznij Przygodę");
      startBtn.setBackground(new Color(50, 150, 50));
      startBtn.setForeground(Color.WHITE);
      startBtn.addActionListener(e -> createAndStart(parent));

      gbc.gridx = 1;
      gbc.gridy = 4;
      add(startBtn, gbc);

      JButton backBtn = new JButton("Wróć");
      backBtn.addActionListener(e -> parent.showView(GameWindow.VIEW_MENU));

      gbc.gridx = 0;
      add(backBtn, gbc);
   }

   private void createAndStart(GameWindow parent) {
      String name = nameField.getText();
      if (name.trim().isEmpty()) {
         JOptionPane.showMessageDialog(this, "Podaj imię bohatera!", "Błąd", JOptionPane.ERROR_MESSAGE);
         return;
      }

      String weapon = (String) weaponBox.getSelectedItem();
      List<String> decorators = new ArrayList<>();
      if (fireCheck.isSelected())
         decorators.add("Ogień");
      if (critCheck.isSelected())
         decorators.add("Krytyk");

      // Użycie fabryki
      Hero hero = HeroFactory.createHero(name, weapon, decorators);

      System.out.println("Stworzono bohatera: " + hero.getName() + " z bronią " + weapon);
      parent.startDungeon(hero);
   }
}
