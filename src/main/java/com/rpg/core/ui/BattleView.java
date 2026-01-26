package com.rpg.core.ui;

import com.rpg.core.entity.Enemy;
import com.rpg.core.entity.Hero;
import com.rpg.core.service.GameEngine;
import com.rpg.core.utils.BattleLogger;
import com.rpg.core.utils.BattleObserver;
import com.rpg.core.utils.InputHandler;
import com.rpg.core.exploration.DungeonRoom;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class BattleView extends JPanel implements BattleLogger, InputHandler, BattleObserver {

   private final GameWindow parent;
   private final JTextArea logArea;
   private final JProgressBar playerHpBar;
   private final JProgressBar playerManaBar;
   private final JProgressBar enemyHpBar;

   private final JButton attackBtn;
   private final JButton healBtn;
   private final JButton heavyBtn;
   private final JButton nextRoomBtn;

   private Hero playerHero;

   private CountDownLatch latch;
   private String selectedAction = "1";

   public BattleView(GameWindow parent) {
      this.parent = parent;
      setLayout(new BorderLayout());
      setBackground(Color.DARK_GRAY);

      // --- Panel Statusu ---
      JPanel statusPanel = new JPanel(new GridLayout(3, 1));
      statusPanel.setBackground(Color.DARK_GRAY);

      playerHpBar = createStyledProgressBar(Color.GREEN);
      playerManaBar = createStyledProgressBar(Color.BLUE);
      enemyHpBar = createStyledProgressBar(Color.RED);

      statusPanel.add(createLabeledPanel("Gracz HP:", playerHpBar));
      statusPanel.add(createLabeledPanel("Gracz MP:", playerManaBar));
      statusPanel.add(createLabeledPanel("Wróg HP:", enemyHpBar));
      add(statusPanel, BorderLayout.NORTH);

      // --- Logi ---
      logArea = new JTextArea();
      logArea.setEditable(false);
      logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
      JScrollPane scrollPane = new JScrollPane(logArea);
      add(scrollPane, BorderLayout.CENTER);

      // --- Panel Akcji ---
      JPanel actionPanel = new JPanel(new FlowLayout());
      actionPanel.setBackground(Color.DARK_GRAY);

      attackBtn = new JButton("Atak (0)");
      heavyBtn = new JButton("Cios (20)");
      healBtn = new JButton("Lecz (30)");

      nextRoomBtn = new JButton("Dalej");
      nextRoomBtn.setBackground(new Color(50, 100, 200));
      nextRoomBtn.setForeground(Color.WHITE);
      nextRoomBtn.setVisible(false);

      attackBtn.setEnabled(false);
      heavyBtn.setEnabled(false);
      healBtn.setEnabled(false);

      attackBtn.addActionListener(e -> onActionSelected("1"));
      healBtn.addActionListener(e -> onActionSelected("2"));
      heavyBtn.addActionListener(e -> onActionSelected("3"));
      nextRoomBtn.addActionListener(e -> parent.loadNextRoom());

      actionPanel.add(attackBtn);
      actionPanel.add(heavyBtn);
      actionPanel.add(healBtn);
      actionPanel.add(nextRoomBtn);
      add(actionPanel, BorderLayout.SOUTH);
   }

   private JProgressBar createStyledProgressBar(Color color) {
      JProgressBar bar = new JProgressBar(0, 100);
      bar.setValue(100);
      bar.setStringPainted(true);
      bar.setForeground(color);
      return bar;
   }

   private JPanel createLabeledPanel(String labelText, JComponent component) {
      JPanel p = new JPanel(new BorderLayout());
      p.setBackground(Color.DARK_GRAY);
      JLabel l = new JLabel(" " + labelText + " ");
      l.setForeground(Color.WHITE);
      l.setPreferredSize(new Dimension(80, 20));
      p.add(l, BorderLayout.WEST);
      p.add(component, BorderLayout.CENTER);
      return p;
   }

   public void setBattle(Hero hero, Enemy enemy, DungeonRoom room) {
      this.playerHero = hero;
      this.playerHero.addObserver(this);

      logArea.setText("");
      nextRoomBtn.setVisible(false);

      onStatsChanged(hero.getHealthPoints(), hero.getMaxHealthPoints(), hero.getManaPoints(), hero.getMaxManaPoints());
      enemyHpBar.setValue(enemy.getHealthPoints());
      enemyHpBar.setString(enemy.getHealthPoints() + " HP");

      String prefix = room.isBossRoom() ? "[MINI-BOSS] " : "";
      log("=== POKÓJ " + room.getRoomNumber() + " ===");
      log("Spotykasz: " + prefix + enemy.getName());

      new Thread(() -> runGameLoop(hero, enemy)).start();
   }

   private void runGameLoop(Hero hero, Enemy enemy) {
      GameEngine engine = new GameEngine(this, this);
      engine.setOnTurnEnd(() -> SwingUtilities.invokeLater(() -> updateEnemyBar(enemy)));

      engine.simulateCombat(hero, enemy);

      hero.removeObserver(this);
      SwingUtilities.invokeLater(() -> handleBattleEnd(hero));
   }

   private void updateEnemyBar(Enemy enemy) {
      enemyHpBar.setMaximum(100);
      enemyHpBar.setValue(enemy.getHealthPoints());
      enemyHpBar.setString(enemy.getHealthPoints() + " HP");
   }

   private void handleBattleEnd(Hero hero) {
      if (hero.isAlive()) {
         log("Zwycięstwo! Możesz ruszać dalej.");
         attackBtn.setEnabled(false);
         heavyBtn.setEnabled(false);
         healBtn.setEnabled(false);
         nextRoomBtn.setVisible(true);
      } else {
         JOptionPane.showMessageDialog(this, "Poległeś w boju...\nKoniec gry.");
         parent.showView(GameWindow.VIEW_MENU);
      }
   }

   @Override
   public void onStatsChanged(int currentHp, int maxHp, int currentMana, int maxMana) {
      SwingUtilities.invokeLater(() -> {
         playerHpBar.setMaximum(maxHp);
         playerHpBar.setValue(currentHp);
         playerHpBar.setString(currentHp + " / " + maxHp + " HP");

         playerManaBar.setMaximum(maxMana);
         playerManaBar.setValue(currentMana);
         playerManaBar.setString(currentMana + " / " + maxMana + " MP");
      });
   }

   @Override
   public String getAction() {
      SwingUtilities.invokeLater(() -> {
         int mana = playerHero.getManaPoints();
         attackBtn.setEnabled(true);
         healBtn.setEnabled(mana >= 30);
         heavyBtn.setEnabled(mana >= 20);
      });

      latch = new CountDownLatch(1);
      try {
         latch.await();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      return selectedAction;
   }

   private void onActionSelected(String action) {
      this.selectedAction = action;
      if (latch != null)
         latch.countDown();
      attackBtn.setEnabled(false);
      heavyBtn.setEnabled(false);
      healBtn.setEnabled(false);
   }

   @Override
   public void log(String message) {
      SwingUtilities.invokeLater(() -> {
         logArea.append(message + "\n");
         logArea.setCaretPosition(logArea.getDocument().getLength());
      });
   }

   public void setPlayer(Hero hero) {
   }
}
