package com.rpg.core.factory;

import com.rpg.core.entity.Hero;
import com.rpg.core.weapon.BaseWeapon;
import com.rpg.core.weapon.DamageSource;
import com.rpg.core.weapon.decorator.CriticalHitChance;
import com.rpg.core.weapon.decorator.FireEnchantment;

import java.util.List;

/**
 * Fabryka do tworzenia obiektów Hero.
 * Separuje logikę tworzenia postaci i nakładania dekoratorów od warstwy UI.
 */
public class HeroFactory {

   /**
    * Tworzy nowego bohatera na podstawie danych z kreatora.
    * 
    * @param name       Imię bohatera
    * @param weaponName Nazwa bazowej broni
    * @param decorators Lista wybranych dekoratorów (nazwy)
    * @return Skonfigurowany obiekt Hero
    */
   public static Hero createHero(String name, String weaponName, List<String> decorators) {
      // Domyślne statystyki bohatera
      Hero hero = new Hero(name, 100, 10);

      // Tworzenie bazowej broni
      DamageSource weapon = createBaseWeapon(weaponName);

      // Nakładanie dekoratorów
      for (String decorator : decorators) {
         weapon = decorateWeapon(weapon, decorator);
      }

      hero.equipWeapon(weapon);
      return hero;
   }

   private static DamageSource createBaseWeapon(String weaponName) {
      switch (weaponName) {
         case "Topór":
            return new BaseWeapon("Topór", 12);
         case "Sztylet":
            return new BaseWeapon("Sztylet", 6);
         case "Miecz":
         default:
            return new BaseWeapon("Miecz", 10);
      }
   }

   private static DamageSource decorateWeapon(DamageSource weapon, String decoratorName) {
      switch (decoratorName) {
         case "Ogień":
            return new FireEnchantment(weapon);
         case "Krytyk":
            return new CriticalHitChance(weapon);
         default:
            return weapon;
      }
   }
}
