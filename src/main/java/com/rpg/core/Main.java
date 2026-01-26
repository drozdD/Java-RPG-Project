package com.rpg.core;

import com.rpg.core.entity.Enemy;
import com.rpg.core.entity.Hero;
import com.rpg.core.service.GameEngine;
import com.rpg.core.strategy.AggressiveStrategy;
import com.rpg.core.weapon.BaseWeapon;
import com.rpg.core.weapon.decorator.FireEnchantment;

/**
 * Główna klasa aplikacji (Entry Point).
 * Uruchamia pełną symulację gry.
 */
public class Main {

   public static void main(String[] args) {
      System.out.println("Uruchamianie JavaRPG-Core...\n");

      GameEngine engine = new GameEngine();

      // 1. Tworzenie postaci gracza
      Hero player = new Hero("Bohater", 100, 10);

      // Dekorowanie broni gracza: Miecz + Ogniste Zaklęcie
      player.equipWeapon(
            new FireEnchantment(
                  new BaseWeapon("Miecz Świetlny", 12)));

      System.out.println("Gracz gotowy: " + player.getName());
      // Uwaga: Opis broni mógłby być wyświetlony jeśli dodalibyśmy metodę do Hero

      // 2. Tworzenie przeciwnika (Wzorzec Strategia)
      Enemy enemy = new Enemy("Mroczny Rycerz", 150, 8, "Człowiek", new AggressiveStrategy());
      enemy.equipWeapon(new BaseWeapon("Topór Zagłady", 10));

      System.out.println("Przeciwnik gotowy: " + enemy.getName() + " (" + enemy.getEnemyType() + ")");

      // 3. Uruchomienie symulacji
      engine.simulateCombat(player, enemy);

      System.out.println("\nSymulacja zakończona. Sprawdź bazę danych.");

      // Wymuszenie wyjścia (zamknięcie wątków Hibernate/H2 jeśli coś wisi)
      System.exit(0);
   }
}
