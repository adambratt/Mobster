package com.blockempires.mobster;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Mobster implements Runnable {
	private MobsterPlugin plugin;
	private Set<MobsterDungeon> dungeonList;
	private FileConfiguration config;
	public MySQL db;
	public int thread;

	public Mobster(MobsterPlugin mobsterPlugin) {
		plugin = mobsterPlugin;
		config = plugin.getConfig();
		dungeonList = new HashSet<MobsterDungeon>();
	}

	public void init() {
		setupConfig();	
		setupDungeons();
	}
	
	/**************************************
	 * 
	 * Mobster Setup
	 * 
	 **************************************/

	private void setupConfig() {
		// Get MySQL info from config, populate if it doesn't exist
		String hostname = config.getString("database.host","localhost");
		String database = config.getString("database.database", "BEWarn");
		String username = config.getString("database.username", "root");
		String port = config.getString("database.port", "8889");
		String password = config.getString("database.password", "root");
		//config.save();
		
		// Load MySQL Connector Object
		this.db = new MySQL(plugin.getServer().getLogger(), "[Mobster] ", hostname, port, database, username, password);	
		
		if (db.getConnection() != null){
			
			// Setup Tables
			if (!db.checkTable("mobster_dungeons")){
				String query = "create table mobster_dungeons(id int not null auto_increment, primary key(id), name varchar(80) not null)";
				db.createTable(query);
			}
			if (!db.checkTable("mobster_rooms")){
				String query = "create table mobster_rooms(id int not null auto_increment, primary key(id), name varchar(80) not null, world varchar(80) not null, dungeon varchar(80) not null)";
				db.createTable(query);
			}
			if (!db.checkTable("mobster_spawners")){
				String query = "create table mobster_spawners(id int not null auto_increment, primary key(id), name varchar(80) not null, creature varchar(80) not null, room varchar(80) not null, health int not null, speed int not null, size int not null, `limit` int not null, x double not null, y double not null, z double not null)";
				db.createTable(query);
			}
			
			// Load Dungeons
			ResultSet dungeonResult = db.query("select * from mobster_dungeons");
			try {
				while ( dungeonResult.next() ){
					MobsterDungeon d = new MobsterDungeon(this, dungeonResult.getString("name"));
					dungeonList.add(d);
				}
			} catch (SQLException e) {
				MobsterPlugin.error("SQL failed with: "+e.getMessage());
				return;
			}
			
			// Load Rooms
			ResultSet roomResult = db.query("SELECT r.*, d.name as dungeon_name FROM mobster_rooms r LEFT JOIN mobster_dungeons d on d.name = r.dungeon WHERE d.name IS NOT NULL");
			try {
				while ( roomResult.next() ){
					World world = plugin.getServer().getWorld(roomResult.getString("world"));
					if (world == null)
						continue;
					ProtectedRegion region = MobsterPlugin.getWorldGuard().getRegionManager(world).getRegion(roomResult.getString("name"));
					if (region == null)
						continue;
					MobsterDungeon dungeon = getDungeon(roomResult.getString("dungeon_name")); 
					if (dungeon == null)
						continue;
					MobsterRoom r = new MobsterRoom(this, dungeon, region, world);
					dungeon.addRoom(r);
				}
			} catch (SQLException e) {
				MobsterPlugin.error("SQL failed with: "+e.getMessage());
				return;
			}
			
			// Load Spawners
			ResultSet spawnResult = db.query("SELECT s.*, r.name as room_name, r.world  FROM mobster_spawners s LEFT JOIN mobster_rooms r ON r.name = s.room WHERE r.name IS NOT NULL");
			try {
				while ( spawnResult.next() ){
					MobsterRoom room = getRoom(spawnResult.getString("room_name"));
					if (room == null)
						continue;
					Location loc = new Location(room.getWorld(), spawnResult.getDouble("x"), spawnResult.getDouble("y"), spawnResult.getDouble("z"));
					MobsterSpawner spawner = new MobsterSpawner(room, spawnResult.getString("name"), spawnResult.getInt("size"), spawnResult.getInt("limit"), spawnResult.getInt("speed"), spawnResult.getInt("health"), Mobster.getEnumFromString(MobsterCreature.class, spawnResult.getString("creature")), loc);				
					room.addSpawner(spawner);
				}
			} catch (SQLException e) {
				MobsterPlugin.error("SQL failed with: "+e.getMessage());
				return;
			}
			
		} else{
			MobsterPlugin.error("Could not connect to database!");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
	}
	
	private void setupDungeons(){
		MobsterPlugin.info("Initializing spawners in dungeons...");
		if(thread > 0){ 
			Bukkit.getServer().getScheduler().cancelTask(thread);
			for (MobsterDungeon d : dungeonList){
				for (MobsterRoom r : d.roomList()){
					if(r.spawnEnabled){
						r.reset();
					}
				}
			}
		}
		thread = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 100, 20);
	}
	
	public void run(){
		for (MobsterDungeon d : dungeonList){
			for (MobsterRoom r : d.roomList()){
				if(r.spawnEnabled){
					r.run();
				}
			}
		}
	}
	
	public void reset(){
		setupDungeons();
	}

	public void shutdown() {
		// Cleanup all the rooms
		for (MobsterDungeon d : dungeonList){
			for (MobsterRoom r : d.roomList()){
				r.cleanup();
			}
		}
	}
	
	/**************************************
	 * 
	 * General Mobster API Functions
	 * 
	 **************************************/
	
	public MobsterPlugin getPlugin(){
		return plugin;
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
		MobsterSpawner spawner = new MobsterSpawner(room, spawnerName);
		spawner.setLocation(location);
		spawner.setCreature(MobsterCreature.ZOMBIE);
		
		// Add to room
		room.addSpawner(spawner);
		
		db.query("insert into mobster_spawners(name,room,x,y,z,speed,`limit`,`size`,health,creature) values('"+spawnerName+"','"+room.getName()+"','"+location.getX()+"','"+location.getY()+"','"+location.getZ()+"','"+spawner.getSpeed()+"','"+spawner.getLimit()+"','"+spawner.getSize()+"','"+spawner.getHealth()+"','"+spawner.getCreatureName()+"')");
	}

	public Set<MobsterDungeon> dungeonList() {
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
		db.query("insert into mobster_dungeons(name) values('"+dungeonName+"')");
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

	public void createRoom(String roomName, ProtectedRegion region, MobsterDungeon dungeon, World world) {
		MobsterRoom r = new MobsterRoom(this, dungeon, region, world);
		dungeon.addRoom(r);
		db.query("insert into mobster_rooms(dungeon,world,name) values('"+dungeon.getName()+"', '"+world.getName()+"', '"+roomName+"')");
	}
	
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string)
    {
        if (c != null && string != null)
        {
            try
            {
                return Enum.valueOf(c, string.trim().toUpperCase());
            }
            catch (IllegalArgumentException ex) { }
        }
        return null;
    }
}
