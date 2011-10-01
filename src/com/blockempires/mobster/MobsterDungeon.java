package com.blockempires.mobster;

import java.util.HashSet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MobsterDungeon {
	private HashSet<MobsterRoom> roomList;
	private Mobster mob;
	public MobsterListener listener;
	protected String name;
	public boolean enabled;
	
	
	public MobsterDungeon(Mobster mobster, String dungeonName) {
		this.mob = mobster;
		this.name = dungeonName;
		this.roomList = new HashSet<MobsterRoom>();
		this.listener = new MobsterListener();
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
		MobsterMonster m = this.getMonster(e);
		if(m==null) 
			return false;
		return true;
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


	public HashSet<MobsterRoom> roomList() {
		return roomList;
	}


	public void addRoom(MobsterRoom r) {
		roomList.add(r);		
	}
}
