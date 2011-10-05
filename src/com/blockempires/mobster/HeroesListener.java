package com.blockempires.mobster;

import com.herocraftonline.dev.heroes.api.HeroesEventListener;
import com.herocraftonline.dev.heroes.api.SkillDamageEvent;
import com.herocraftonline.dev.heroes.api.WeaponDamageEvent;

public class HeroesListener extends HeroesEventListener {
	private Mobster mob;
	
	public HeroesListener(Mobster mob) {
		this.mob=mob;
	}

	@Override
	public void onSkillDamage(SkillDamageEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onSkillDamage(event);
	}

	@Override
	public void onWeaponDamage(WeaponDamageEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onWeaponDamage(event);
	}

}
