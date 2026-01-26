package com.rpg.core.exploration;

import com.rpg.core.entity.Enemy;
import lombok.Getter;
import lombok.ToString;

/**
 * Reprezentuje pojedynczy pokój w lochu.
 * Może zawierać przeciwnika lub (w przyszłości) skarb.
 */
@Getter
@ToString
public class DungeonRoom {

   private final int roomNumber;
   private final Enemy enemy;
   private final String treasureDescription;
   private final boolean isBossRoom;

   public DungeonRoom(int roomNumber, Enemy enemy, boolean isBossRoom) {
      this.roomNumber = roomNumber;
      this.enemy = enemy;
      this.treasureDescription = null;
      this.isBossRoom = isBossRoom;
   }

   public boolean hasEnemy() {
      return enemy != null;
   }
}
