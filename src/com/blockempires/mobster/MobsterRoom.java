package com.blockempires.mobster;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class MobsterRoom {
	protected Map<MobsterSpawner, Integer> spawnerList;
	protected boolean spawnEnabled;
	protected List<Player> playerList;
	protected ProtectedRegion wgRegion;
	private	Mobster mob;
	private MobsterDungeon dungeon;
	
	public MobsterRoom(Mobster m, MobsterDungeon d, ProtectedRegion region){
		this.mob = m;
		this.dungeon = d;
		this.wgRegion = region;
		spawnerList = new HashMap<MobsterSpawner, Integer>();
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
		int threadid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(mob.getPlugin(), spawner, 100, 20);
		spawnerList.put(spawner, threadid);
	}

	public Collection<MobsterSpawner> spawnerList() {
		return spawnerList.keySet();
	}

	public void removeSpawner(MobsterSpawner s) {
		int thread = spawnerList.get(s);
		Bukkit.getServer().getScheduler().cancelTask(thread);
		s.reset();
		spawnerList.remove(s);		
	}
}
