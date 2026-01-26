package com.rpg.core.exploration;

import com.rpg.core.entity.Enemy;
import com.rpg.core.strategy.AggressiveStrategy;
import com.rpg.core.strategy.DefensiveStrategy;
import com.rpg.core.weapon.BaseWeapon;

import java.util.Iterator;
import java.util.Random;

/**
 * Iterator prowadzący gracza przez loch.
 * Dynamicznie generuje kolejne pokoje.
 */
public class DungeonIterator implements Iterator<DungeonRoom> {

   private int currentRoom = 0;
   private final int difficulty;
   private final Random random = new Random();

   public DungeonIterator(int difficulty) {
      this.difficulty = difficulty;
   }

   @Override
   public boolean hasNext() {
      // Loch jest nieskończony (dla tego przykładu)
      return true;
   }

   @Override
   public DungeonRoom next() {
      currentRoom++;
      boolean isBoss = (currentRoom % 3 == 0);

      Enemy enemy = isBoss ? generateBoss() : generateNormalEnemy();
      return new DungeonRoom(currentRoom, enemy, isBoss);
   }

   private Enemy generateNormalEnemy() {
      if (random.nextBoolean()) {
         Enemy orc = new Enemy("Ork", 60 + (difficulty * 10), 8 + difficulty, "Ork", new AggressiveStrategy());
         orc.equipWeapon(new BaseWeapon("Topór", 8));
         return orc;
      } else {
         Enemy goblin = new Enemy("Goblin", 40 + (difficulty * 5), 5 + difficulty, "Goblin", new AggressiveStrategy());
         goblin.equipWeapon(new BaseWeapon("Sztylet", 4));
         return goblin;
      }
   }

   private Enemy generateBoss() {
      Enemy boss = new Enemy("ROGATA BESTIA (Mini-Boss)", 80 + (difficulty * 10), 8 + (difficulty * 1), "Boss",
            new DefensiveStrategy(100));
      boss.equipWeapon(new BaseWeapon("Morgulskie Ostrze", 10));
      return boss;
   }
}
