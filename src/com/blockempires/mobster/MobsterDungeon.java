package com.blockempires.mobster;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MobsterDungeon {
	private Set<MobsterRoom> roomList;
	public Mobster mob;
	public MobsterListener listener;
	protected String name;
	public boolean enabled;
	
	
	public MobsterDungeon(Mobster mobster, String dungeonName) {
		this.mob = mobster;
		this.name = dungeonName;
		this.roomList = new HashSet<MobsterRoom>();
		this.enabled = true;
		this.listener = new MobsterListener(this);
	}


	public MobsterMonster getMonster(Entity e) {
		if (e instanceof LivingEntity){
			LivingEntity monster = (LivingEntity) e;
			for (MobsterRoom r : roomList){
				for (MobsterSpawner s : r.spawnerList()){
					MobsterMonster m = s.getMonster(monster);
					if (m != null)
						return m;  
				}
			}
		}
		return null;
	}


	public boolean hasMonster(Entity e) {
		if (e instanceof LivingEntity){
			LivingEntity monster = (LivingEntity) e;
			for (MobsterRoom r : roomList){
				for (MobsterSpawner s : r.spawnerList()){
					if(s.monsters.containsKey(monster.getEntityId()))
						return true; 
				}
			}
		}
		return false;
	}


	public void killMonster(Entity e) {
		if (e instanceof LivingEntity){
			LivingEntity monster = (LivingEntity) e;
			for (MobsterRoom r : roomList){
				for (MobsterSpawner s : r.spawnerList()){
					MobsterMonster m = s.getMonster(monster);
					if (m != null)
						s.killMonster(m);  
				}
			}
		}
	}


	public String getName() {
		return name;
	}


	public Set<MobsterRoom> roomList() {
		return roomList;
	}


	public void addRoom(MobsterRoom r) {
		roomList.add(r);		
	}
}
