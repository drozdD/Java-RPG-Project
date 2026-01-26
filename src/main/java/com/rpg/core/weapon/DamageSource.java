package com.rpg.core.weapon;

/**
 * Interfejs reprezentujący źródło obrażeń.
 * Stanowi podstawę dla wzorca Dekorator w systemie broni.
 * 
 * Zgodnie z zasadą Open/Closed - interfejs jest zamknięty na modyfikacje,
 * ale otwarty na rozszerzenia poprzez nowe implementacje.
 */
public interface DamageSource {

   /**
    * Oblicza całkowite obrażenia zadawane przez źródło.
    * 
    * @return Wartość obrażeń
    */
   int calculateDamage();

   /**
    * Zwraca opis źródła obrażeń.
    * 
    * @return Tekstowy opis broni i jej ulepszeń
    */
   String getDescription();
}
