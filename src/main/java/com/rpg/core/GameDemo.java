package com.rpg.core;

import com.rpg.core.entity.Enemy;
import com.rpg.core.entity.GameCharacter;
import com.rpg.core.state.PoisonedState;
import com.rpg.core.state.StunnedState;
import com.rpg.core.strategy.AggressiveStrategy;
import com.rpg.core.strategy.DefensiveStrategy;
import com.rpg.core.weapon.BaseWeapon;
import com.rpg.core.weapon.decorator.CriticalHitChance;
import com.rpg.core.weapon.decorator.FireEnchantment;

/**
 * Przykładowa demonstracja użycia wzorców projektowych w silniku gry.
 * 
 * Demonstruje:
 * - Wzorzec Dekorator (system broni)
 * - Wzorzec Stan (stany postaci)
 * - Wzorzec Strategia (AI przeciwników)
 */
public class GameDemo {

   public static void main(String[] args) {
      System.out.println("=== JavaRPG-Core Demo ===\n");

      // --- Tworzenie postaci gracza (prosty przykład) ---
      Enemy hero = new Enemy("Bohater", 100, 10, "Gracz", new AggressiveStrategy());

      // Wyposażenie bohatera w ulepszony miecz (Dekorator)
      hero.equipWeapon(
            new CriticalHitChance(
                  new FireEnchantment(
                        new BaseWeapon("Miecz Płomieni", 15))));

      // --- Tworzenie przeciwników ze strategiami ---

      // Ork - strategia agresywna (zawsze atakuje)
      Enemy orc = createOrc();

      // Goblin - strategia defensywna (leczy się gdy HP < 20%)
      Enemy goblin = createGoblin();

      // --- Symulacja walki ---
      System.out.println("--- RUNDA 1 ---\n");

      // Ork atakuje bohatera
      System.out.println("[Tura Orka]");
      orc.executeTurn(hero);
      System.out.println();

      // Goblin atakuje bohatera
      System.out.println("[Tura Goblina]");
      goblin.executeTurn(hero);
      System.out.println();

      // --- Demonstracja wzorca Stan ---
      System.out.println("--- EFEKTY STATUSU ---\n");

      // Zatrucie Orka
      System.out.println("[Ork zostaje zatruty]");
      orc.changeState(new PoisonedState(2));
      System.out.println();

      System.out.println("[Tura zatrutego Orka]");
      orc.executeTurn(hero);
      System.out.println();

      // Ogłuszenie Goblina
      System.out.println("[Goblin zostaje ogłuszony]");
      goblin.changeState(new StunnedState(1));
      System.out.println();

      System.out.println("[Tura ogłuszonego Goblina]");
      goblin.executeTurn(hero);
      System.out.println();

      // --- Demonstracja strategii defensywnej ---
      System.out.println("--- STRATEGIA DEFENSYWNA ---\n");

      // Ustaw niskie HP Goblina aby wywołać leczenie
      goblin.setHealthPoints(8); // 8/50 = 16% < 20%
      System.out.println("Goblin ma " + goblin.getHealthPoints() + " HP (poniżej 20%)");

      System.out.println("[Tura Goblina z niskim HP]");
      goblin.executeTurn(hero);
      System.out.println();

      // --- Podsumowanie ---
      System.out.println("=== STAN KOŃCOWY ===");
      printCharacterStatus(hero);
      printCharacterStatus(orc);
      printCharacterStatus(goblin);
   }

   /**
    * Tworzy Orka ze strategią agresywną.
    */
   private static Enemy createOrc() {
      Enemy orc = new Enemy("Ork Wojownik", 80, 12, "Ork", new AggressiveStrategy());
      orc.equipWeapon(new BaseWeapon("Wielki Topór", 8));
      System.out.println("Stworzono: " + orc.getName() + " (Strategia: Agresywna)");
      System.out.println("  Broń: Wielki Topór (+8 obrażeń)");
      System.out.println("  HP: " + orc.getHealthPoints() + ", Bazowe obrażenia: " + orc.getBaseDamage());
      System.out.println();
      return orc;
   }

   /**
    * Tworzy Goblina ze strategią defensywną.
    */
   private static Enemy createGoblin() {
      int goblinMaxHp = 50;
      Enemy goblin = new Enemy("Goblin Szaman", goblinMaxHp, 6, "Goblin",
            new DefensiveStrategy(goblinMaxHp));
      goblin.equipWeapon(new BaseWeapon("Sztylet", 4));
      System.out.println("Stworzono: " + goblin.getName() + " (Strategia: Defensywna)");
      System.out.println("  Broń: Sztylet (+4 obrażeń)");
      System.out.println("  HP: " + goblin.getHealthPoints() + ", Bazowe obrażenia: " + goblin.getBaseDamage());
      System.out.println();
      return goblin;
   }

   /**
    * Wyświetla status postaci.
    */
   private static void printCharacterStatus(GameCharacter character) {
      System.out.println(character.getName() + ": "
            + character.getHealthPoints() + " HP, Stan: " + character.getCurrentStateName());
   }
}
