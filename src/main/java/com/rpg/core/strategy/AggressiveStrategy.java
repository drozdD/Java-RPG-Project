package com.rpg.core.strategy;

import com.rpg.core.entity.GameCharacter;

/**
 * Strategia agresywna - zawsze atakuje przeciwnika.
 * 
 * Wykorzystuje DamageSource postaci do zadawania obrażeń.
 * Nie uwzględnia stanu własnego HP.
 */
public class AggressiveStrategy implements CombatStrategy {

   @Override
   public void executeAction(GameCharacter self, GameCharacter target) {
      System.out.println(self.getName() + " [AGRESYWNY] atakuje " + target.getName() + "!");

      int damage = self.attack(target);

      System.out.println("Zadano " + damage + " obrażeń! "
            + target.getName() + " HP: " + target.getHealthPoints());
   }

   @Override
   public String getStrategyName() {
      return "Agresywna";
   }
}
