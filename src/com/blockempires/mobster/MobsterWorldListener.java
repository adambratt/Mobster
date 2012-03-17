package com.blockempires.mobster;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class MobsterWorldListener implements Listener {
	private Mobster mob;

	public MobsterWorldListener(Mobster mob) {
		this.mob=mob;
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onChunkLoad(ChunkLoadEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onChunkLoad(event);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkUnload(ChunkUnloadEvent event) {
		for(MobsterDungeon d : mob.dungeonList())
			d.listener.onChunkUnLoad(event);
	}

}
