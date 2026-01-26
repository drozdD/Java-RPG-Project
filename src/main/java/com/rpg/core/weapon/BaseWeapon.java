package com.rpg.core.weapon;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bazowa implementacja broni - konkretny komponent wzorca Dekorator.
 * Reprezentuje prostą broń bez żadnych ulepszeń.
 */
@Getter
@AllArgsConstructor
public class BaseWeapon implements DamageSource {

   private final String name;
   private final int baseDamage;

   @Override
   public int calculateDamage() {
      return baseDamage;
   }

   @Override
   public String getDescription() {
      return name + " (obrażenia bazowe: " + baseDamage + ")";
   }
}
