package com.rpg.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca postać gracza.
 * W przeciwieństwie do wrogów, nie posiada automatycznej strategii -
 * decyzje podejmuje użytkownik.
 */
@Entity
@Table(name = "heroes")
@NoArgsConstructor
public class Hero extends GameCharacter {

   public Hero(String name, int healthPoints, int baseDamage) {
      super(name, healthPoints, baseDamage);
   }

   @Override
   public int attack(GameCharacter target) {
      int damage = calculateTotalDamage();
      target.takeDamage(damage);
      return damage;
   }

   @Override
   public int defend(int incomingDamage) {
      // Gracz może mieć np. pancerz w przyszłości
      return incomingDamage;
   }
}
