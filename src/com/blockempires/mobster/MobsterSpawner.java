package com.blockempires.mobster;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;


public class MobsterSpawner implements Runnable {
	
	private MobsterRoom room;
	protected int mobSize, monsterLimit, monsterCount, monsterHealth, spawnSpeed, timeLeft;
	protected MobsterCreature creature;
	protected HashSet<MobsterMonster> monsters;
	protected Location loc;
	protected String name;

	public MobsterSpawner(MobsterRoom mobRoom){
		room=mobRoom;
		reset();
	}

	// Main loop that will run as long as the room is populated
	@Override
	public void run() {
		// Removes stray entities
		clean();
		
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
		for (MobsterMonster m : monsters){
			// If monster is dead or outside of the room, remove it.
			if (m.getEntity().isDead() || !room.inRoom(m.getEntity().getLocation())){
				monsters.remove(m);
				m.kill();
				monsterCount--;
			}
		}
	}
	
	public void reset(){
		for (MobsterMonster m : monsters){
			monsters.remove(m);
			m.kill();
		}
		monsters = new HashSet<MobsterMonster>();
		timeLeft = 0;
		monsterCount = 0;
	}
	
	// Spawn mob for this room
	protected void runSpawn()
	{
		for (int i=0; i<mobSize; i++){
			MobsterMonster m = creature.spawnMonster(loc);
			m.setHealth(monsterHealth);
			monsters.add(m);
			monsterCount++;
		}
	}
	
	public MobsterMonster getMonster(LivingEntity e){
		for(MobsterMonster m : monsters){
			if(m.getEntity() == e)
				return m;
		}
		return null;
	}
	
	public void killMonster(MobsterMonster m){
		monsters.remove(m);
		monsterCount--;
	}
	
	
	public MobsterRoom getRoom(){
		return room;
	}
	
	public String getName() {
		return name;
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
		reset();
		return true;
	}
	
	public int getSize() {
		return mobSize;
	}

	public boolean setHealth(int health) {
		if (health < 4 || health > 500)
			return false;
		monsterHealth = health;
		reset();
		return true;
	}

	public int getHealth() {
		return monsterHealth;
	}

	public boolean setCreature(MobsterCreature c) {
		creature = c;
		return true;
	}
	
	public String getCreatureName() {
		return creature.getName();
	}

	public boolean setSpeed(int speed) {
		if(speed < 1 || speed > 360)
			return false;
		spawnSpeed = speed;
		reset();
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
		reset();
		return true;
	}
	
	public Location getLocation(){
		return loc;
	}

	public void setLocation(Location location) {
		this.loc = location;		
	}

	public boolean setCreature(String type) {
		// TODO Auto-generated method stub
		return false;
	}
}
