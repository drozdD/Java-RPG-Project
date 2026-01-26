package com.rpg.core.utils;

/**
 * Interfejs do logowania zdarzeń w grze.
 * Pozwala na uniezależnienie silnika gry od konsoli (System.out).
 */
public interface BattleLogger {
   void log(String message);
}
