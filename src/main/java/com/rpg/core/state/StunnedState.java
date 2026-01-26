package com.rpg.core.state;

import com.rpg.core.entity.GameCharacter;

/**
 * Stan ogłuszenia - postać traci turę i nie może wykonać żadnej akcji.
 * 
 * Zgodnie z zasadą Open/Closed - można dodawać nowe stany
 * bez modyfikacji istniejących klas.
 */
public class StunnedState implements CharacterState {

   private int remainingTurns;

   /**
    * Tworzy stan ogłuszenia z określoną liczbą tur.
    * 
    * @param turns Liczba tur ogłuszenia
    */
   public StunnedState(int turns) {
      this.remainingTurns = turns;
   }

   /**
    * Tworzy stan ogłuszenia na 1 turę.
    */
   public StunnedState() {
      this(1);
   }

   @Override
   public boolean handleTurn(GameCharacter character) {
      System.out.println(character.getName() + " jest ogłuszony i traci turę!");

      remainingTurns--;

      // Sprawdź czy ogłuszenie się kończy
      if (remainingTurns <= 0) {
         System.out.println(character.getName() + " odzyskuje przytomność.");
         character.changeState(new HealthyState());
      } else {
         System.out.println("Pozostałe tury ogłuszenia: " + remainingTurns);
      }

      // Ogłuszona postać NIE może wykonać akcji
      return false;
   }

   @Override
   public void onEnterState(GameCharacter character) {
      System.out.println(character.getName() + " został ogłuszony na " + remainingTurns + " turę/tury!");
   }

   @Override
   public void onExitState(GameCharacter character) {
      System.out.println(character.getName() + " już nie jest ogłuszony.");
   }

   @Override
   public String getStateName() {
      return "Ogłuszony (" + remainingTurns + " tura/tury)";
   }

   public int getRemainingTurns() {
      return remainingTurns;
   }
}
