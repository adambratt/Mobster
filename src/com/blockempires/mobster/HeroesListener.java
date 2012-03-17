package com.blockempires.mobster;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public class HeroesListener implements Listener {
	private Mobster mob;
	
	public HeroesListener(Mobster mob) {
		this.mob=mob;

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSkillDamage(SkillDamageEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onSkillDamage(event);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onWeaponDamage(WeaponDamageEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onWeaponDamage(event);
	}

}
