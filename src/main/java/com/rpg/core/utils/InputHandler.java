package com.rpg.core.utils;

/**
 * Interfejs do obsługi wejścia użytkownika.
 * Pozwala na uniezależnienie silnika od Scanner/System.in.
 */
public interface InputHandler {
   /**
    * Pobiera akcję od użytkownika.
    * 
    * @return Wybrana opcja jako String (np. "1", "2")
    */
   String getAction();
}
