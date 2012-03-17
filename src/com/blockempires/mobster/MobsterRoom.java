package com.blockempires.mobster;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class MobsterRoom {
	protected Set<MobsterSpawner> spawnerList;
	protected boolean spawnEnabled;
	protected List<Player> playerList;
	protected ProtectedRegion wgRegion;
	public Mobster mob;
	private MobsterDungeon dungeon;
	private World world;
	
	public MobsterRoom(Mobster m, MobsterDungeon d, ProtectedRegion region, World w){
		this.mob = m;
		this.dungeon = d;
		this.wgRegion = region;
		this.world = w;
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
	
	public World getWorld() {
		return world;
	}

	public void addSpawner(MobsterSpawner spawner) {
		spawnerList.add(spawner);
	}

	public Collection<MobsterSpawner> spawnerList() {
		return spawnerList;
	}
	
	public void cleanup(){
		for (MobsterSpawner s : spawnerList){
			s.reset();
		}
		spawnerList.clear();
		spawnerList=null;
	}

	public void removeSpawner(MobsterSpawner s) {
		s.reset();
		mob.db.query("delete from mobster_spawners where name='"+s.name+"'");
		spawnerList.remove(s);		
	}


	public void run() {
		for (MobsterSpawner spawn : spawnerList){
			spawn.run();
		}
	}


	public void reset() {
		for (MobsterSpawner spawn : spawnerList){
			spawn.reset();
		}
	}
}
