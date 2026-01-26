package com.rpg.core.strategy;

import com.rpg.core.entity.GameCharacter;

/**
 * Strategia defensywna - leczy się gdy HP < 20%, w przeciwnym razie atakuje.
 * 
 * Implementuje prostą logikę przetrwania dla przeciwników.
 */
public class DefensiveStrategy implements CombatStrategy {

   private static final double HEAL_THRESHOLD = 0.20; // 20% HP
   private static final int HEAL_AMOUNT = 15;

   private final int maxHealthPoints;

   /**
    * Tworzy strategię defensywną z określonym maksymalnym HP.
    * 
    * @param maxHealthPoints Maksymalne HP postaci (do obliczenia progu leczenia)
    */
   public DefensiveStrategy(int maxHealthPoints) {
      this.maxHealthPoints = maxHealthPoints;
   }

   @Override
   public void executeAction(GameCharacter self, GameCharacter target) {
      double healthPercentage = (double) self.getHealthPoints() / maxHealthPoints;

      if (healthPercentage < HEAL_THRESHOLD) {
         // Leczenie gdy HP < 20%
         heal(self);
      } else {
         // Atak gdy HP >= 20%
         attack(self, target);
      }
   }

   /**
    * Leczy postać.
    */
   private void heal(GameCharacter self) {
      int currentHp = self.getHealthPoints();
      int newHp = Math.min(maxHealthPoints, currentHp + HEAL_AMOUNT);
      self.setHealthPoints(newHp);

      System.out.println(self.getName() + " [DEFENSYWNY] leczy się! (+" + HEAL_AMOUNT + " HP)");
      System.out.println("HP: " + currentHp + " -> " + newHp);
   }

   /**
    * Atakuje cel.
    */
   private void attack(GameCharacter self, GameCharacter target) {
      System.out.println(self.getName() + " [DEFENSYWNY] atakuje " + target.getName() + "!");

      int damage = self.attack(target);

      System.out.println("Zadano " + damage + " obrażeń! "
            + target.getName() + " HP: " + target.getHealthPoints());
   }

   @Override
   public String getStrategyName() {
      return "Defensywna";
   }
}
