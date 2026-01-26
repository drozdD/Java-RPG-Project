package com.rpg.core.strategy;

import com.rpg.core.entity.GameCharacter;

/**
 * Interfejs strategii walki - wzorzec Strategia (Strategy Pattern).
 * 
 * Pozwala na dynamiczną zmianę zachowania AI przeciwników
 * bez modyfikacji ich kodu.
 * 
 * Zgodnie z zasadą Dependency Inversion - klasy wysokiego poziomu
 * zależą od abstrakcji, nie od konkretnych implementacji.
 */
public interface CombatStrategy {

   /**
    * Wykonuje akcję w turze postaci.
    * 
    * @param self   Postać wykonująca akcję (właściciel strategii)
    * @param target Cel akcji (przeciwnik)
    */
   void executeAction(GameCharacter self, GameCharacter target);

   /**
    * Zwraca nazwę strategii dla celów wyświetlania.
    * 
    * @return Nazwa strategii
    */
   String getStrategyName();
}
