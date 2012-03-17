package com.blockempires.mobster;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class MobsterEntityListener implements Listener{
	private Mobster mob;
	
	public MobsterEntityListener(Mobster mob){
		this.mob=mob;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onCreatureSpawn(event);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityExplode(EntityExplodeEvent event){
		// Nothing for now
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDeath(EntityDeathEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityDeath(event);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityDamage(event);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityCombust(EntityCombustEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityCombust(event);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityTarget(EntityTargetEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityTarget(event);
	}
	
}
