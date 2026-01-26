package com.rpg.core.entity;

import com.rpg.core.strategy.CombatStrategy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa reprezentująca przeciwnika w grze.
 * 
 * Wykorzystuje wzorzec Strategia do określania zachowania AI.
 * Strategia jest wstrzykiwana przez konstruktor (Dependency Injection).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "enemies")
public class Enemy extends GameCharacter {

   @Column(name = "enemy_type")
   private String enemyType;

   /**
    * Strategia walki przeciwnika (wzorzec Strategy).
    * Pole transient - nie jest persystowane.
    */
   @Transient
   private CombatStrategy combatStrategy;

   /**
    * Tworzy przeciwnika z określoną strategią walki.
    * 
    * @param name           Nazwa przeciwnika
    * @param healthPoints   Punkty zdrowia
    * @param baseDamage     Bazowe obrażenia
    * @param enemyType      Typ przeciwnika (np. "Ork", "Goblin")
    * @param combatStrategy Strategia walki (wstrzykiwana zależność)
    */
   public Enemy(String name, int healthPoints, int baseDamage,
         String enemyType, CombatStrategy combatStrategy) {
      super(name, healthPoints, baseDamage);
      this.enemyType = enemyType;
      this.combatStrategy = combatStrategy;
   }

   /**
    * Wykonuje turę przeciwnika używając przypisanej strategii.
    * 
    * @param target Cel akcji (gracz)
    */
   public void executeTurn(GameCharacter target) {
      // Najpierw przetwórz efekty stanu (zatrucie, ogłuszenie itp.)
      if (!processTurn()) {
         System.out.println(getName() + " nie może wykonać akcji w tej turze.");
         return;
      }

      // Wykonaj akcję zgodnie ze strategią
      if (combatStrategy != null) {
         combatStrategy.executeAction(this, target);
      } else {
         System.out.println(getName() + " nie ma przypisanej strategii!");
      }
   }

   /**
    * Zmienia strategię walki przeciwnika w trakcie gry.
    * 
    * @param newStrategy Nowa strategia
    */
   public void setCombatStrategy(CombatStrategy newStrategy) {
      this.combatStrategy = newStrategy;
      System.out.println(getName() + " zmienia strategię na: " + newStrategy.getStrategyName());
   }

   @Override
   public int attack(GameCharacter target) {
      int damage = calculateTotalDamage();
      target.takeDamage(damage);
      return damage;
   }

   @Override
   public int defend(int incomingDamage) {
      // Przeciwnicy nie mają redukcji obrażeń (można rozszerzyć)
      return incomingDamage;
   }
}
