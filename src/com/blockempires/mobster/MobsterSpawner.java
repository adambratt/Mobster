package com.blockempires.mobster;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MobsterSpawner implements Runnable {
	
	private MobsterRoom room;
	protected int monsterCount, monsterMax;
	protected MobsterCreature monster;
	protected Location loc;

	public MobsterSpawner(MobsterPlugin plugin, MobsterRoom mobRoom){
		room=mobRoom;
	}

	// Main loop that will run as long as the room is populated
	@Override
	public void run() {
		// Removes stray entities
		clean();
		// Check to see if there are still people in the room, and if it is still enabled and if not return
		if(room.playerList.isEmpty() || !room.spawnEnabled){
			return;
		}
		// Check to see if we have reached our spawn limit
		if(monsterCount >= monsterMax){
			return;
		}
		// Looks good, lets spawn a monster
		spawnMonster();			
	}
	
	public void clean(){
		for(Entity e : room.monsters){
			// If monster is dead or outside of the room, remove it.
			if(e.isDead() || !room.inRoom(e.getLocation())){
				room.removeMonster(e);
				e.remove();
				monsterCount--;
			}
		}
	}
	
	// Spawn a monster for this room
	protected LivingEntity spawnMonster()
	{
		LivingEntity e=monster.spawn(room,loc);
		room.addMonster(e);	
		monsterCount++;
		return e;
	}
	
	
	public MobsterRoom getRoom(){
		return room;
	}

}
