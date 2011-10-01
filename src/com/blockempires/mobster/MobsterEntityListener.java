package com.blockempires.mobster;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

public class MobsterEntityListener extends EntityListener {
	private Mobster mob;
	
	public MobsterEntityListener(Mobster mob){
		this.mob=mob;
	}
	
	public void onCreatureSpawn(CreatureSpawnEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onCreatureSpawn(event);
	}

	public void onEntityExplode(EntityExplodeEvent event){
		// Nothing for now
	}
	
	public void onEntityDeath(EntityDeathEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityDeath(event);
	}
	
	public void onEntityDamage(EntityDamageEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityDamage(event);
	}
	
	public void onEntityCombust(EntityCombustEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityCombust(event);
	}
	
	public void onEntityTarget(EntityTargetEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityTarget(event);
	}
	
}
