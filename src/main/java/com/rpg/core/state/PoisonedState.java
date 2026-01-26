package com.rpg.core.state;

import com.rpg.core.entity.GameCharacter;

/**
 * Stan zatrucia - postać traci HP na początku tury, ale może atakować.
 * 
 * Zgodnie z zasadą Liskov Substitution - ten stan może być użyty
 * wszędzie tam, gdzie oczekiwany jest CharacterState.
 */
public class PoisonedState implements CharacterState {

   private static final int POISON_DAMAGE = 5;
   private int remainingTurns;

   /**
    * Tworzy stan zatrucia z określoną liczbą tur.
    * 
    * @param turns Liczba tur trwania zatrucia
    */
   public PoisonedState(int turns) {
      this.remainingTurns = turns;
   }

   /**
    * Tworzy stan zatrucia z domyślną liczbą 3 tur.
    */
   public PoisonedState() {
      this(3);
   }

   @Override
   public boolean handleTurn(GameCharacter character) {
      // Zadaj obrażenia od trucizny
      int currentHp = character.getHealthPoints();
      int newHp = Math.max(0, currentHp - POISON_DAMAGE);
      character.setHealthPoints(newHp);

      System.out.println(character.getName() + " otrzymuje " + POISON_DAMAGE
            + " obrażeń od trucizny! (HP: " + currentHp + " -> " + newHp + ")");

      remainingTurns--;

      // Sprawdź czy zatrucie się kończy
      if (remainingTurns <= 0) {
         System.out.println("Efekt trucizny mija.");
         character.changeState(new HealthyState());
      } else {
         System.out.println("Pozostałe tury zatrucia: " + remainingTurns);
      }

      // Zatruta postać może nadal atakować (jeśli żyje)
      return character.isAlive();
   }

   @Override
   public void onEnterState(GameCharacter character) {
      System.out.println(character.getName() + " został zatruty na " + remainingTurns + " tury!");
   }

   @Override
   public void onExitState(GameCharacter character) {
      System.out.println(character.getName() + " wyzdrowiał z zatrucia.");
   }

   @Override
   public String getStateName() {
      return "Zatruty (" + remainingTurns + " tury)";
   }

   public int getRemainingTurns() {
      return remainingTurns;
   }
}
