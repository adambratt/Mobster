package com.blockempires.mobster;

import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class MobsterRoom {
	protected HashSet<MobsterSpawner> spawnerList;
	protected boolean spawnEnabled;
	protected List<Player> playerList;
	protected ProtectedRegion wgRegion;
	private	Mobster mob;
	private MobsterDungeon dungeon;
	
	public MobsterRoom(Mobster m, MobsterDungeon d, ProtectedRegion region){
		this.mob = m;
		this.dungeon = d;
		this.wgRegion = region;
		spawnerList = new HashSet<MobsterSpawner>();
		spawnEnabled = true;
	}

	public boolean inRoom(Location loc) {
		if(wgRegion==null) return false;
		com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(loc.getX(), loc.getY(), loc.getZ());
		if (wgRegion.contains(v)) {
		     return true;
		}
		return false;
	}

	public String getName() {
		return wgRegion.getId();
	}

	public MobsterDungeon getDungeon() {
		return dungeon;
	}

	public void addSpawner(MobsterSpawner spawner) {
		spawnerList.add(spawner);
	}

	public HashSet<MobsterSpawner> spawnerList() {
		return spawnerList;
	}

	public void removeSpawner(MobsterSpawner s) {
		spawnerList.remove(s);		
	}
}
