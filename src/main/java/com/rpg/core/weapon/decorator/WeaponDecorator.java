package com.rpg.core.weapon.decorator;

import com.rpg.core.weapon.DamageSource;
import lombok.RequiredArgsConstructor;

/**
 * Abstrakcyjny dekorator broni - bazowa klasa dla wszystkich ulepszeń.
 * 
 * Implementuje wzorzec Dekorator, pozwalając na dynamiczne dodawanie
 * nowych funkcjonalności do broni bez modyfikacji istniejących klas.
 * 
 * Zgodnie z zasadą Open/Closed:
 * - Klasa jest zamknięta na modyfikacje
 * - Otwarta na rozszerzenia poprzez nowe dekoratory
 */
@RequiredArgsConstructor
public abstract class WeaponDecorator implements DamageSource {

   /**
    * Dekorowany obiekt źródła obrażeń (może być bazowa broń lub inny dekorator).
    */
   protected final DamageSource wrappedSource;

   @Override
   public int calculateDamage() {
      return wrappedSource.calculateDamage();
   }

   @Override
   public String getDescription() {
      return wrappedSource.getDescription();
   }
}
