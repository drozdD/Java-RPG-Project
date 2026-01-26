package com.rpg.core.state;

import com.rpg.core.entity.GameCharacter;

/**
 * Stan zdrowej postaci - domyślny stan.
 * Postać może normalnie wykonywać akcje bez żadnych ograniczeń.
 */
public class HealthyState implements CharacterState {

   @Override
   public boolean handleTurn(GameCharacter character) {
      // Zdrowa postać może normalnie wykonać akcję
      System.out.println(character.getName() + " jest w pełni zdrowy i gotowy do akcji.");
      return true;
   }

   @Override
   public void onEnterState(GameCharacter character) {
      System.out.println(character.getName() + " powraca do pełni zdrowia!");
   }

   @Override
   public void onExitState(GameCharacter character) {
      // Brak specjalnych akcji przy opuszczaniu stanu zdrowego
   }

   @Override
   public String getStateName() {
      return "Zdrowy";
   }
}
