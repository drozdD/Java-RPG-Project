package com.rpg.core.ui;

import com.rpg.core.entity.Hero;
import com.rpg.core.exploration.Dungeon;
import com.rpg.core.exploration.DungeonRoom;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * Główne okno gry wykorzystujące bibliotekę Swing.
 * Zarządza widokami (View) za pomocą CardLayout.
 */
public class GameWindow extends JFrame {

   private final CardLayout cardLayout;
   private final JPanel mainPanel;

   // Pola eksploracji
   private Iterator<DungeonRoom> dungeonIterator;
   private Hero currentHero;

   // Stałe dla nazw widoków
   public static final String VIEW_MENU = "MENU";
   public static final String VIEW_CREATOR = "CREATOR";
   public static final String VIEW_BATTLE = "BATTLE";

   public GameWindow() {
      setTitle("JavaRPG-Core");
      setSize(800, 600);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null); // Centrowanie okna
      setResizable(false);

      // Inicjalizacja layoutu
      cardLayout = new CardLayout();
      mainPanel = new JPanel(cardLayout);

      // Dodanie widoków
      mainPanel.add(new MenuView(this), VIEW_MENU);
      mainPanel.add(new CreatorView(this), VIEW_CREATOR);
      mainPanel.add(new BattleView(this), VIEW_BATTLE);

      add(mainPanel);

      // Start od menu
      showView(VIEW_MENU);
   }

   /**
    * Rozpoczyna nową przygodę (tworzy loch).
    * 
    * @param hero Utworzony bohater
    */
   public void startDungeon(Hero hero) {
      this.currentHero = hero;
      // Tworzenie lochu (poziom trudności 1)
      Dungeon dungeon = new Dungeon(1);
      this.dungeonIterator = dungeon.iterator();

      loadNextRoom();
   }

   /**
    * Ładuje następny pokój z iteratora.
    */
   public void loadNextRoom() {
      if (dungeonIterator.hasNext()) {
         DungeonRoom room = dungeonIterator.next();

         // Jeśli pokój ma wroga, rozpocznij walkę
         if (room.hasEnemy()) {
            startBattle(currentHero, room);
         } else {
            // (Opcjonalnie) Obsługa pustego pokoju / skarbu
            JOptionPane.showMessageDialog(this, "Pokój " + room.getRoomNumber() + ": Pusto. Idziesz dalej.");
            loadNextRoom();
         }
      } else {
         JOptionPane.showMessageDialog(this, "Koniec Lochu! Gratulacje!");
         showView(VIEW_MENU);
      }
   }

   /**
    * Rozpoczyna walkę w danym pokoju.
    */
   private void startBattle(Hero hero, DungeonRoom room) {
      Component[] components = mainPanel.getComponents();
      for (Component component : components) {
         if (component instanceof BattleView) {
            ((BattleView) component).setBattle(hero, room.getEnemy(), room);
         }
      }
      showView(VIEW_BATTLE);
   }

   public void showView(String viewName) {
      cardLayout.show(mainPanel, viewName);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         new GameWindow().setVisible(true);
      });
   }
}
