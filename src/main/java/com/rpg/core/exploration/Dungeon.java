package com.rpg.core.exploration;

import java.util.Iterator;

/**
 * Reprezentuje cały loch.
 * Implementuje Iterable, co pozwala na iterowanie po pokojach.
 */
public class Dungeon implements Iterable<DungeonRoom> {

   private final int difficultyLevel;

   /**
    * @param difficultyLevel Wpływa na siłę generowanych wrogów (base multiplier)
    */
   public Dungeon(int difficultyLevel) {
      this.difficultyLevel = difficultyLevel;
   }

   @Override
   public Iterator<DungeonRoom> iterator() {
      return new DungeonIterator(difficultyLevel);
   }
}
