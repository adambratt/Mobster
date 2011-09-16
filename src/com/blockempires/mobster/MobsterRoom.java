package com.blockempires.mobster;

import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MobsterRoom {
	protected List<MobsterSpawner> spawnerList;
	protected HashSet<Entity> monsters;
	protected boolean useWaves;
	protected boolean spawnEnabled;
	protected List<Player> playerList;
	
	/* If useWaves is true it will use the MobsterWaves class for spawning
	 * recurrent waves of monsters else it will create MobsterSpawners for 
	 * each spawner in the room.
	 */

	public void removeMonster(Entity e) {
		monsters.remove(e);		
	}

	public boolean inRoom(Location location) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addMonster(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
