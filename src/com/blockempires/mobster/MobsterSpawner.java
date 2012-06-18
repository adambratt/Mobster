package com.blockempires.mobster;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;


public class MobsterSpawner implements Runnable {

	private MobsterRoom room;
	protected int mobSize, monsterLimit, monsterCount, monsterHealth, spawnSpeed, timeLeft;
	protected MobsterCreature creature;
	protected Location loc;
	protected String name;
	protected boolean dirty = false;
	protected boolean loaded = false;
	public ConcurrentMap<Integer, MobsterMonster> monsters;

	public MobsterSpawner(MobsterRoom mobRoom, String name){
		// Setup main variables
		room = mobRoom;
		this.name = name;

		// Default values
		mobSize = 1;
		monsterLimit = 5;
		spawnSpeed = 10;
		
		creature = MobsterCreature.ZOMBIE;
		monsterHealth = 20;

		// For default health check for Heroes default
		if (MobsterPlugin.heroesPlugin != null){
			
			
		}


		// Finish setting up
		reset();
	}

	public MobsterSpawner(MobsterRoom mobRoom, String name, int size, int limit, int speed, int health, MobsterCreature mobcreature, Location l){
		room = mobRoom;
		this.name = name;
		mobSize = size;
		monsterLimit = limit;
		spawnSpeed = speed;
		monsterHealth = health;
		creature = mobcreature;
		loc = l;

		// Initialize
		reset();
	}

	// Main loop that will run as long as the room is populated
	@Override
	public void run() {
		// Update the configuration if needed
		if (dirty)
			reset();

		// Removes stray entities
		clean();

		// Check to see if chunk is loaded
		if (!loc.getBlock().getChunk().isLoaded()){
			loaded = false;
			return;
		} else if (loaded == false){
			loaded = true;
			reset();
			return;
		}


		// Check to see if spawn enabled (eventually check for people)
		if (!room.spawnEnabled)
			return;

		// Check to see if we have reached our spawn limit
		if (monsterCount > (monsterLimit - mobSize))
			return;

		// If no time on the clock, spawn. If time, lets count down!
		if(timeLeft <= 0){
			runSpawn();		
			timeLeft = spawnSpeed;
		} else
			timeLeft--;
	}

	public void clean(){
		if (monsters.isEmpty()) 
			return;

		for (MobsterMonster m : monsters.values()){
			// If monster is dead or outside of the room/loaded chunk, remove it.
			if (m.getEntity().isDead() || m.getHealth() <= 0 || !room.inRoom(m.getEntity().getLocation()) || !m.getEntity().getLocation().getBlock().getChunk().isLoaded()){
				monsters.remove(m.id());
				m.remove();
				monsterCount--;
			}
		}		
	}

	public void reset(){
		if (monsters != null){
			for (MobsterMonster m : monsters.values()){
				m.remove();		
			}
			monsters.clear();
		}
		monsters = new ConcurrentHashMap<Integer, MobsterMonster>();
		timeLeft = 0;
		monsterCount = 0;
		dirty = false;
	}

	// Spawn mob for this room
	protected void runSpawn()
	{
		for (int i=0; i<mobSize; i++){
			MobsterMonster m = creature.spawnMonster(loc);
			m.setHealth(this.monsterHealth);
			if(m.id() == 0 || m.getEntity() == null)
				continue; // protection against bad entities
			monsters.put(m.id(), m);
			monsterCount++;
		}
	}

	public MobsterMonster getMonster(LivingEntity e){
		return monsters.get(e.getEntityId());
	}

	public void killMonster(MobsterMonster m){
		m.kill();
	}


	public MobsterRoom getRoom(){
		return room;
	}

	public String getName() {
		return name;
	}

	private void updateConfig(){
		dirty = true;
	}

	/**************************************
	 * 
	 * Spawn Attribute Getters/Setters
	 * 
	 **************************************/

	public boolean setSize(int size) {
		if (size > monsterLimit)
			return false;
		mobSize = size;
		room.mob.db.query("update mobster_spawners set size='"+size+"' where name='"+name+"'");
		updateConfig();
		return true;
	}

	public int getSize() {
		return mobSize;
	}

	public boolean setHealth(int health) {
		if (health < 4 || health > 1000)
			return false;
		monsterHealth = health;
		room.mob.db.query("update mobster_spawners set health='"+health+"' where name='"+name+"'");
		updateConfig();
		return true;
	}

	public int getHealth() {
		return monsterHealth;
	}

	public int getTime() {
		return timeLeft;
	}

	public boolean setCreature(MobsterCreature c) {
		creature = c;
		room.mob.db.query("update mobster_spawners set creature='"+c.getName()+"' where name='"+name+"'");
		updateConfig();
		return true;
	}

	public boolean setCreature(String type) {
		MobsterCreature mc = Mobster.getEnumFromString(MobsterCreature.class, type);
		if(mc == null) return false;
		creature = mc;
		room.mob.db.query("update mobster_spawners set creature='"+type+"' where name='"+name+"'");
		updateConfig();
		return true;
	}

	public String getCreatureName() {
		return creature.getName();
	}

	public boolean setSpeed(int speed) {
		if(speed < 1 || speed > 360)
			return false;
		spawnSpeed = speed;
		room.mob.db.query("update mobster_spawners set speed='"+speed+"' where name='"+name+"'");
		updateConfig();
		return true;
	}

	public int getSpeed() {
		return spawnSpeed;
	}	

	public int getLimit() {
		return monsterLimit;
	}

	public boolean setLimit(int limit) {
		if (limit < mobSize)
			return false;
		monsterLimit = limit;
		room.mob.db.query("update mobster_spawners set `limit`='"+limit+"' where name='"+name+"'");
		updateConfig();
		return true;
	}

	public Location getLocation(){
		return loc;
	}

	public void setLocation(Location location) {
		this.loc = location;		
	}

	public int getCount() {
		return monsterCount;
	}
}

