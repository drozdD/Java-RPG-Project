package com.rpg.core.repository;

import com.rpg.core.entity.GameCharacter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;

/**
 * Repozytorium do zarządzania encjami GameCharacter.
 * Wykorzystuje JPA EntityManager do operacji na bazie danych.
 */
public class CharacterRepository implements AutoCloseable {

   private final EntityManagerFactory emf;
   private final EntityManager em;

   public CharacterRepository() {
      // Tworzenie EntityManagerFactory na podstawie konfiguracji z persistence.xml
      this.emf = Persistence.createEntityManagerFactory("rpg-unit");
      this.em = emf.createEntityManager();
   }

   /**
    * Zapisuje lub aktualizuje postać w bazie danych.
    * 
    * @param character Postać do zapisania
    */
   public void save(GameCharacter character) {
      EntityTransaction transaction = em.getTransaction();
      try {
         transaction.begin();
         if (character.getId() == null) {
            em.persist(character);
         } else {
            em.merge(character);
         }
         transaction.commit();
         System.out.println("Zapisano postać: " + character.getName());
      } catch (Exception e) {
         if (transaction.isActive()) {
            transaction.rollback();
         }
         e.printStackTrace();
      }
   }

   /**
    * Znajduje postać po ID.
    * 
    * @param id ID postaci
    * @return Optional z postacią lub pusty
    */
   public Optional<GameCharacter> findById(Long id) {
      return Optional.ofNullable(em.find(GameCharacter.class, id));
   }

   /**
    * Pobiera wszystkie postacie z bazy.
    * 
    * @return Lista postaci
    */
   public List<GameCharacter> findAll() {
      return em.createQuery("SELECT c FROM GameCharacter c", GameCharacter.class)
            .getResultList();
   }

   /**
    * Usuwa postać z bazy.
    * 
    * @param character Postać do usunięcia
    */
   public void delete(GameCharacter character) {
      EntityTransaction transaction = em.getTransaction();
      try {
         transaction.begin();
         em.remove(em.contains(character) ? character : em.merge(character));
         transaction.commit();
      } catch (Exception e) {
         if (transaction.isActive()) {
            transaction.rollback();
         }
         e.printStackTrace();
      }
   }

   @Override
   public void close() {
      if (em.isOpen()) {
         em.close();
      }
      if (emf.isOpen()) {
         emf.close();
      }
   }
}
