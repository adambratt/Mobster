package com.blockempires.mobster;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dev.heroes.Heroes;

public class MobsterPluginListener extends ServerListener {
	private MobsterPlugin plugin;

	public MobsterPluginListener(MobsterPlugin mobsterPlugin) {
		this.plugin=mobsterPlugin;
	}

	@Override
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
