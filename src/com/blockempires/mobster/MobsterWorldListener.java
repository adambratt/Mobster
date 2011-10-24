package com.blockempires.mobster;

import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldListener;

public class MobsterWorldListener extends WorldListener {
	private Mobster mob;

	public MobsterWorldListener(Mobster mob) {
		this.mob=mob;
	}

	@Override
	public void onChunkLoad(ChunkLoadEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onChunkLoad(event);
	}

	@Override
	public void onChunkUnload(ChunkUnloadEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onChunkUnLoad(event);
	}

}
