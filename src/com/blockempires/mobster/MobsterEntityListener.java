package com.blockempires.mobster;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class MobsterEntityListener {
	private Mobster mob;
	
	public MobsterEntityListener(Mobster mob){
		this.mob=mob;
	}

	public void onEntityExplode(EntityExplodeEvent event){
		// Nothing for now
	}
	
	public void onEntityDamage(EntityDamageEvent event){
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onEntityDamage(event);
	}
}
