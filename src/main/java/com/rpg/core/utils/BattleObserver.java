package com.rpg.core.utils;

/**
 * Obsługuje zmiany statystyk postaci.
 * Wzorzec Obserwator (Observer Pattern).
 */
public interface BattleObserver {
   /**
    * Wywoływana przy zmianie HP lub Many.
    */
   void onStatsChanged(int currentHp, int maxHp, int currentMana, int maxMana);
}
