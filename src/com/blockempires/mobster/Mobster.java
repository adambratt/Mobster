package com.blockempires.mobster;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;
import com.blockempires.mobster.MobsterRoom;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Mobster {
	private MobsterPlugin plugin;
	private HashSet<MobsterDungeon> dungeonList;
	private Configuration config;

	public Mobster(MobsterPlugin mobsterPlugin) {
		plugin = mobsterPlugin;
		config = plugin.getConfig();
		dungeonList = new HashSet<MobsterDungeon>();
	}

	public void init() {
		config.load();
		setupConfig();
		setupDungeons();		
	}

	private void setupConfig() {
		// TODO Auto-generated method stub
		
	}

	private void setupDungeons() {
		// TODO Auto-generated method stub
		
	}

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public ProtectedRegion getRegion(String regionName, World world){
		ProtectedRegion region=MobsterPlugin.getWorldGuard().getRegionManager(world).getRegion(regionName);
		if(region==null) return null;
		return region;
	}

	public MobsterSpawner getSpawner(String spawnerName) {
		for (MobsterDungeon d : dungeonList){
			for (MobsterRoom r : d.roomList()){
				for (MobsterSpawner s : r.spawnerList()){
					if (s.getName().equalsIgnoreCase(spawnerName))
						return s;
				}
			}
		}
		return null;
	}

	public void removeSpawner(String spawnerName) {
		MobsterSpawner s = getSpawner(spawnerName);		
		s.getRoom().removeSpawner(s);
	}

	public MobsterRoom getRoom(Location location) {
		for (MobsterDungeon d : dungeonList){
			for (MobsterRoom r : d.roomList()){
				if (r.inRoom(location))
					return r;
			}
		}
		return null;
	}

	public void createSpawner(String spawnerName, Location location, MobsterRoom room) {
		// Create spawner
		MobsterSpawner spawner = new MobsterSpawner(room);
		spawner.setLocation(location);
		
		// Set default values
		spawner.setSpeed(10);
		spawner.setHealth(10);
		spawner.setLimit(5);
		spawner.setSize(1);
		spawner.setCreature(MobsterCreature.ZOMBIE);
		
		// Add to room
		room.addSpawner(spawner);
	}

	public HashSet<MobsterDungeon> dungeonList() {
		return dungeonList;
	}

	public MobsterDungeon getDungeon(String dungeonName) {
		for (MobsterDungeon d : dungeonList){
			if (dungeonName.equalsIgnoreCase(d.getName()))
				return d;
		}
		return null;
	}

	public void createDungeon(String dungeonName) {
		MobsterDungeon d = new MobsterDungeon(this, dungeonName);
		dungeonList.add(d);
	}

	public MobsterRoom getRoom(String roomName) {
		for (MobsterDungeon d : dungeonList){
			for (MobsterRoom r : d.roomList()){
				if (roomName.equalsIgnoreCase(r.getName()))
					return r;
			}
		}
		return null;
	}

	public void createRoom(String roomName, ProtectedRegion region, MobsterDungeon dungeon) {
		MobsterRoom r = new MobsterRoom(this, dungeon, region);
		dungeon.addRoom(r);
	}

}
