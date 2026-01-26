package com.rpg.core.weapon.decorator;

import com.rpg.core.weapon.DamageSource;
import java.util.Random;

/**
 * Dekorator dodający szansę na trafienie krytyczne.
 * Ma 20% szans na podwojenie obrażeń.
 * 
 * Przykład dekoratora z losowym efektem.
 */
public class CriticalHitChance extends WeaponDecorator {

   private static final double CRITICAL_CHANCE = 0.20; // 20%
   private static final int CRITICAL_MULTIPLIER = 2;

   private final Random random;

   public CriticalHitChance(DamageSource wrappedSource) {
      super(wrappedSource);
      this.random = new Random();
   }

   /**
    * Konstruktor z możliwością wstrzyknięcia generatora losowego (dla testów).
    * 
    * @param wrappedSource Dekorowane źródło obrażeń
    * @param random        Generator liczb losowych
    */
   public CriticalHitChance(DamageSource wrappedSource, Random random) {
      super(wrappedSource);
      this.random = random;
   }

   @Override
   public int calculateDamage() {
      int baseDamage = super.calculateDamage();

      if (random.nextDouble() < CRITICAL_CHANCE) {
         return baseDamage * CRITICAL_MULTIPLIER;
      }

      return baseDamage;
   }

   @Override
   public String getDescription() {
      return super.getDescription() + " + Szansa na Krytyka ("
            + (int) (CRITICAL_CHANCE * 100) + "% szans na x" + CRITICAL_MULTIPLIER + " obrażeń)";
   }
}
