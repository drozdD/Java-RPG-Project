package com.rpg.core.state;

import com.rpg.core.entity.GameCharacter;

/**
 * Interfejs reprezentujący stan postaci we wzorcu Stan (State Pattern).
 * 
 * Zgodnie z zasadą Interface Segregation - interfejs zawiera tylko
 * metody niezbędne do zarządzania zachowaniem postaci w danym stanie.
 * 
 * Zgodnie z zasadą Single Responsibility - każdy stan odpowiada
 * za jedno konkretne zachowanie postaci.
 */
public interface CharacterState {

   /**
    * Obsługuje logikę tury dla postaci w danym stanie.
    * Wywoływana na początku każdej tury postaci.
    * 
    * @param character Postać, której tura jest obsługiwana
    * @return true jeśli postać może wykonać akcję w tej turze, false w przeciwnym
    *         razie
    */
   boolean handleTurn(GameCharacter character);

   /**
    * Wywoływana przy wejściu postaci w dany stan.
    * Pozwala na wykonanie akcji inicjalizacyjnych.
    * 
    * @param character Postać wchodząca w stan
    */
   void onEnterState(GameCharacter character);

   /**
    * Wywoływana przy opuszczaniu stanu przez postać.
    * Pozwala na czyszczenie efektów stanu.
    * 
    * @param character Postać opuszczająca stan
    */
   void onExitState(GameCharacter character);

   /**
    * Zwraca nazwę stanu dla celów wyświetlania.
    * 
    * @return Nazwa stanu
    */
   String getStateName();
}
