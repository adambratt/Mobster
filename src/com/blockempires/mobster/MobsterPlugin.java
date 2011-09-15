package com.blockempires.mobster;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MobsterPlugin extends JavaPlugin {
	private Mobster mob;
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
		//Setup Events
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
    	    			
    }
    
    private void loadConfig(){
    	
    }
    
	private void loadEvents() {
		
	}
	 
}
