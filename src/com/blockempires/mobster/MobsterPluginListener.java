package com.blockempires.mobster;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.heroes.Heroes;

public class MobsterPluginListener implements Listener {
	private MobsterPlugin plugin;

	public MobsterPluginListener(MobsterPlugin mobsterPlugin) {
		this.plugin=mobsterPlugin;
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		Plugin eventp = event.getPlugin();
		try {
			if(eventp instanceof Heroes){ 
				MobsterPlugin.heroesPlugin = (Heroes) eventp;
				plugin.loadHeroesEvents();
				MobsterPlugin.info("Heroes has been loaded late. It is now enabled for Mobster!");
			}
		 } catch (NoClassDefFoundError ex) {
			 return;
		 }
	}
}
