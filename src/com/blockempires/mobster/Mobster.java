package com.blockempires.mobster;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.config.Configuration;

public class Mobster {
	private MobsterPlugin plugin;
	private List<MobsterDungeon> dungeonList;
	private Configuration config;

	public Mobster(MobsterPlugin mobsterPlugin) {
		plugin = mobsterPlugin;
		config = plugin.getConfig();
		dungeonList = new ArrayList<MobsterDungeon>();
	}

	public void init() {
		config.load();
		setupConfig();
		setupDungeons();		
	}

	private void setupConfig() {
		// TODO Auto-generated method stub
		
	}

	private void setupDungeons() {
		// TODO Auto-generated method stub
		
	}

	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
