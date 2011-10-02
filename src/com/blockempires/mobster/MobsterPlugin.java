package com.blockempires.mobster;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;

import com.blockempires.mobster.commands.MobsterCommands;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class MobsterPlugin extends JavaPlugin {
	private Mobster mob;
	private static WorldGuardPlugin wgPlugin;
	private PluginManager pManage;
	public static File dir;
	
	public void onEnable(){ 
		//Get Folder setup
		dir=getDataFolder();
		if(!dir.exists()) dir.mkdir();
		
		//Get Configuration
		loadConfig();		
		
		//Setup External hooks
		loadDepend();		
		
		//Setup Mob Handler
		mob=new Mobster(this);
		mob.init();		
		
		//Setup Events & Commands
		loadCommands();
		loadEvents();	
		
		//Tell the console
		info("Mobster v"+getDescription().getVersion()+" has been enabled.");
	}

	public void onDisable(){ 
		if(mob!=null){
			mob.shutdown();
		}
		info("Plugin Disabled.");
	}
	
	//Console loggers
	public static void info(String msg)    { Bukkit.getServer().getLogger().info("[Mobster] " + msg); }
    public static void warning(String msg) { Bukkit.getServer().getLogger().warning("[Mobster] " + msg); }    
    public static void error(String msg)   { Bukkit.getServer().getLogger().severe("[Mobster] " + msg); }
    
    //Loads dependencies
    private void loadDepend(){
    	if (pManage.isPluginEnabled("WorldGuard")){
			Plugin wg=pManage.getPlugin("WorldGuard");
			if (wg instanceof WorldGuardPlugin)
				wgPlugin=(WorldGuardPlugin) wg;
		} else
			error("WorldGuard does not appear to be installed and is REQUIRED by Mobster");
    }
    
    private void loadConfig(){
    	pManage=getServer().getPluginManager();
    }
    
    private void loadCommands(){
    	try {
			getCommand("mobster").setExecutor(new MobsterCommands(this.mob));
		} catch (Exception e) {
			error("Error: Commands not defined in 'plugin.yml'");
		}
    }
    
	private void loadEvents() {
		EntityListener entityListener = new MobsterEntityListener(this.mob);
		pManage.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Priority.Highest, this);
		pManage.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Low, this);
		pManage.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Lowest, this);
		pManage.registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Priority.Highest, this);
		pManage.registerEvent(Event.Type.ENTITY_COMBUST, entityListener, Priority.Normal, this);
		pManage.registerEvent(Event.Type.ENTITY_TARGET, entityListener, Priority.Low, this);
	}

	public Configuration getConfig() {
		return this.getConfiguration();
	}

	public static WorldGuardPlugin getWorldGuard(){
		return MobsterPlugin.wgPlugin;
	}
	 
}
