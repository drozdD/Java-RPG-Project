package com.rpg.core.entity;

import com.rpg.core.state.CharacterState;
import com.rpg.core.state.HealthyState;
import com.rpg.core.utils.BattleObserver;
import com.rpg.core.weapon.DamageSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstrakcyjna klasa bazowa dla wszystkich postaci w grze.
 * Definiuje wspólne atrybuty i metody dla bohaterów oraz wrogów.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "game_characters")
public abstract class GameCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "health_points", nullable = false)
    private int healthPoints;

    @Column(name = "max_health_points", nullable = false)
    private int maxHealthPoints = 100; // Domyślna wartość, można nadpisać w konstruktorze

    @Column(name = "base_damage", nullable = false)
    private int baseDamage;

    @Column(name = "mana_points", nullable = false)
    private int manaPoints;

    @Column(name = "max_mana_points", nullable = false)
    private int maxManaPoints = 50;

    /**
     * Aktualnie wyposażona broń postaci.
     * Pole transient - nie jest persystowane w bazie danych.
     * Wykorzystuje wzorzec Dekorator dla dynamicznych modyfikacji obrażeń.
     */
    @Transient
    private DamageSource equippedWeapon;

    /**
     * Aktualny stan postaci (wzorzec State).
     * Domyślnie postać jest zdrowa.
     */
    @Transient
    private CharacterState currentState = new HealthyState();

    /**
     * Lista obserwatorów (UI).
     */
    @Transient
    private List<BattleObserver> observers = new ArrayList<>();

    /**
     * Konstruktor tworzący postać z podstawowymi atrybutami.
     */
    public GameCharacter(String name, int healthPoints, int baseDamage) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints; // Prawidłowa inicjalizacja maxHp
        this.baseDamage = baseDamage;
        this.currentState = new HealthyState();
        this.manaPoints = 50; // Startowa mana
        this.maxManaPoints = 50;
    }

    // --- Observer Pattern Logic ---
    public void addObserver(BattleObserver observer) {
        if (observers == null)
            observers = new ArrayList<>();
        observers.add(observer);
        notifyObservers(); // Init update
    }

    public void removeObserver(BattleObserver observer) {
        if (observers != null)
            observers.remove(observer);
    }

    protected void notifyObservers() {
        if (observers != null) {
            for (BattleObserver obs : observers) {
                obs.onStatsChanged(healthPoints, maxHealthPoints, manaPoints, maxManaPoints);
            }
        }
    }

    // Nadpisujemy settery Lombokowe, aby powiadamiał obserwatorów
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
        notifyObservers();
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
        notifyObservers();
    }

    // Zmiana many o wartość (np. koszt czaru lub regeneracja)
    public void modifyMana(int amount) {
        this.manaPoints = Math.min(maxManaPoints, Math.max(0, manaPoints + amount));
        notifyObservers();
    }

    public void equipWeapon(DamageSource weapon) {
        this.equippedWeapon = weapon;
    }

    protected int calculateTotalDamage() {
        if (equippedWeapon != null) {
            return baseDamage + equippedWeapon.calculateDamage();
        }
        return baseDamage;
    }

    public abstract int attack(GameCharacter target);

    public abstract int defend(int incomingDamage);

    public void takeDamage(int damage) {
        int finalDamage = defend(damage);
        this.healthPoints = Math.max(0, this.healthPoints - finalDamage);
        notifyObservers(); // Ponowne powiadomienie po zmianie HP
    }

    public boolean isAlive() {
        return this.healthPoints > 0;
    }

    public void changeState(CharacterState newState) {
        if (this.currentState != null) {
            this.currentState.onExitState(this);
        }
        this.currentState = newState;
        if (this.currentState != null) {
            this.currentState.onEnterState(this);
        }
    }

    public boolean processTurn() {
        if (currentState != null) {
            return currentState.handleTurn(this);
        }
        return true;
    }

    public String getCurrentStateName() {
        return currentState != null ? currentState.getStateName() : "Brak";
    }
}
