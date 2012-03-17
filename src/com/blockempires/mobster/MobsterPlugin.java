package com.blockempires.mobster;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.blockempires.mobster.commands.MobsterCommands;
import com.herocraftonline.heroes.Heroes;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class MobsterPlugin extends JavaPlugin {
	private Mobster mob;
	private static WorldGuardPlugin wgPlugin;
	public static Heroes heroesPlugin;
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
    	// World Guard
    	if (pManage.isPluginEnabled("WorldGuard")){
			Plugin wg = pManage.getPlugin("WorldGuard");
			if (wg instanceof WorldGuardPlugin)
				wgPlugin = (WorldGuardPlugin) wg;
		} else
			error("WorldGuard does not appear to be installed and is REQUIRED by Mobster");
    	
    	// Heroes
    	if (pManage.isPluginEnabled("Heroes")){
    		Plugin h = pManage.getPlugin("Heroes");
    		if(h instanceof Heroes)
    			heroesPlugin = (Heroes) h;
    	}else
    		info("Heroes plugin not found");
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
		getServer().getPluginManager().registerEvents(new MobsterPluginListener(this), this);
		getServer().getPluginManager().registerEvents(new MobsterWorldListener(this.mob), this);
		getServer().getPluginManager().registerEvents(new MobsterEntityListener(this.mob), this);
		loadHeroesEvents();
	}
	
	public void loadHeroesEvents(){
		if (heroesPlugin != null){
			getServer().getPluginManager().registerEvents(new HeroesListener(this.mob), this);
		}
	}


	public static WorldGuardPlugin getWorldGuard(){
		return MobsterPlugin.wgPlugin;
	}
	 
}
