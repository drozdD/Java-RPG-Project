package com.rpg.core.weapon.decorator;

import com.rpg.core.weapon.DamageSource;

/**
 * Dekorator dodający obrażenia od ognia do broni.
 * Przykład konkretnego dekoratora we wzorcu Dekorator.
 */
public class FireEnchantment extends WeaponDecorator {

   private static final int FIRE_DAMAGE_BONUS = 5;

   public FireEnchantment(DamageSource wrappedSource) {
      super(wrappedSource);
   }

   @Override
   public int calculateDamage() {
      return super.calculateDamage() + FIRE_DAMAGE_BONUS;
   }

   @Override
   public String getDescription() {
      return super.getDescription() + " + Ogniste Zaklęcie (+" + FIRE_DAMAGE_BONUS + " obrażeń od ognia)";
   }
}
