package com.rpg.core.service;

import com.rpg.core.entity.Enemy;
import com.rpg.core.entity.GameCharacter;
import com.rpg.core.repository.CharacterRepository;
import com.rpg.core.utils.BattleLogger;
import com.rpg.core.utils.InputHandler;

import java.util.Scanner;

/**
 * Silnik gry zarządzający symulacją walki.
 * Zrefaktoryzowany do użycia interfejsów Logger/InputHandler.
 */
public class GameEngine {

   private final CharacterRepository repository;
   private final BattleLogger logger;
   private final InputHandler inputHandler;

   private Runnable onTurnEnd;

   public GameEngine() {
      this(new ConsoleLogger(), new ConsoleInputHandler());
   }

   public GameEngine(BattleLogger logger, InputHandler inputHandler) {
      this.repository = new CharacterRepository();
      this.logger = logger;
      this.inputHandler = inputHandler;
   }

   public void setOnTurnEnd(Runnable onTurnEnd) {
      this.onTurnEnd = onTurnEnd;
   }

   public void simulateCombat(GameCharacter player, GameCharacter enemy) {
      logger.log("\n=== ROZPOCZĘCIE WALKI ===");
      logger.log(player.getName() + " VS " + enemy.getName());

      int turnCount = 1;

      while (player.isAlive() && enemy.isAlive()) {
         logger.log("\n--- TURA " + turnCount + " ---");

         // Regeneracja many gracza (+5 co turę)
         if (player.isAlive()) {
            player.modifyMana(5);

            logger.log(">> Tura gracza: " + player.getName());
            if (player.processTurn()) {
               handlePlayerAction(player, enemy);
            }
         }

         if (onTurnEnd != null)
            onTurnEnd.run();

         if (!enemy.isAlive())
            break;

         if (enemy.isAlive()) {
            // Regeneracja many wroga
            enemy.modifyMana(5);

            logger.log(">> Tura przeciwnika: " + enemy.getName());
            if (enemy instanceof Enemy) {
               ((Enemy) enemy).executeTurn(player);
            } else {
               if (enemy.processTurn()) {
                  enemy.attack(player);
               }
            }
         }

         if (onTurnEnd != null)
            onTurnEnd.run();

         turnCount++;
         printStatus(player, enemy);
      }

      handleCombatEnd(player, enemy);
   }

   private void handlePlayerAction(GameCharacter player, GameCharacter target) {
      logger.log("Wybierz akcję: 1. Atak, 2. Ulecz (30 MP), 3. Potężne Uderzenie (20 MP)");

      while (true) {
         String choice = inputHandler.getAction();

         switch (choice) {
            case "2": // Leczenie
               if (player.getManaPoints() >= 30) {
                  player.modifyMana(-30);
                  int healAmount = 30;
                  player.setHealthPoints(player.getHealthPoints() + healAmount);
                  logger.log(player.getName() + " rzuca Czar Leczenia! (+" + healAmount + " HP)");
                  return;
               } else {
                  logger.log("Brak many! Potrzeba 30 MP.");
                  continue;
               }
            case "3": // Potężne Uderzenie
               if (player.getManaPoints() >= 20) {
                  player.modifyMana(-20);
                  logger.log(player.getName() + " wykonuje Potężne Uderzenie!");
                  int baseDamage = player.attack(target);
                  int bonusDamage = (int) (baseDamage * 1.5);
                  target.takeDamage(bonusDamage);
                  logger.log("KRYTYCZNE WZMOCNIENIE! Dodatkowe " + bonusDamage + " obrażeń!");
                  return;
               } else {
                  logger.log("Brak many! Potrzeba 20 MP.");
                  continue;
               }
            case "1":
            default:
               logger.log(player.getName() + " atakuje!");
               int damage = player.attack(target);
               logger.log("Zadano " + damage + " obrażeń!");
               return;
         }
      }
   }

   private void handleCombatEnd(GameCharacter c1, GameCharacter c2) {
      logger.log("\n=== KONIEC WALKI ===");

      GameCharacter winner = c1.isAlive() ? c1 : c2;
      GameCharacter loser = c1.isAlive() ? c2 : c1;

      logger.log("Zwycięzca: " + winner.getName() + " (HP: " + winner.getHealthPoints() + ")");
      logger.log("Przegrany: " + loser.getName());

      logger.log("Zapisywanie zwycięzcy do bazy danych...");
      repository.save(winner);
   }

   private void printStatus(GameCharacter c1, GameCharacter c2) {
      logger.log("STATUS: " + c1.getName() + " (" + c1.getHealthPoints() + " HP, " + c1.getManaPoints() + " MP) | " +
            c2.getName() + " (" + c2.getHealthPoints() + " HP)");
   }

   static class ConsoleLogger implements BattleLogger {
      @Override
      public void log(String message) {
         System.out.println(message);
      }
   }

   static class ConsoleInputHandler implements InputHandler {
      private final Scanner scanner = new Scanner(System.in);

      @Override
      public String getAction() {
         System.out.print("> ");
         return scanner.hasNextLine() ? scanner.nextLine() : "1";
      }
   }
}
