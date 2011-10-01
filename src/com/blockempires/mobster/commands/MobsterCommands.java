package com.blockempires.mobster.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.blockempires.mobster.LineageArea;
import com.blockempires.mobster.LineageAreaMessage;
import com.blockempires.mobster.Mobster;
import com.blockempires.mobster.MobsterDungeon;
import com.blockempires.mobster.MobsterPlugin;
import com.blockempires.mobster.LineageRace;
import com.blockempires.mobster.MobsterRoom;
import com.blockempires.mobster.MobsterSpawner;

public class MobsterCommands implements CommandExecutor {
	private Mobster mob;
	
	public MobsterCommands(Mobster mob){
		this.mob=mob;
	}

	public void msgSuccess(Player player, String msg){
		player.sendMessage(ChatColor.RED+"[Mobster][Error] "+msg);
	}
	
	public void msgError(Player player, String msg){
		player.sendMessage(ChatColor.GREEN+"[Mobster] "+msg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			if(!sender.isOp()){
				if(!sender.hasPermission("mobster.edit")) return false;
			}
		}else{
			// Need to be a player to use this, how else will we know which world to use!
			return false;
		}
		Player player = (Player) sender;
		
		// We don't have any commands that are just one thing
		if(args.length < 2) return false;
		
		if (args[0].equalsIgnoreCase("spawn")){
			
			if (args.length < 3) return false;
			
			// Get spawner to be used in following functions
			String spawnerName = args[1];
			MobsterSpawner spawner = mob.getSpawner(spawnerName);
			
			if (args[2].equalsIgnoreCase("create")){
				MobsterRoom room = mob.getRoom(player.getLocation());
				if (room == null)
					msgError(player, "You need to be in a Mobster room in order to create a spawner");
				else if (spawner != null)
					msgError(player, "A spawner called: "+spawnerName+" already exists!");
				else {
					mob.createSpawner(spawnerName, player.getLocation(), room);
					msgSuccess(player, spawnerName+" spawner has been created");
				}	
				return true;
			}
			
			// We aren't adding a new one, so make sure that the spawner listed exists
			if (spawner == null){
				msgError(player, "Invalid spawner!");
				return false;
			}
			
			if (args[2].equalsIgnoreCase("delete")){
				mob.removeSpawner(spawnerName);
				msgSuccess(player, spawnerName+" removed!");
				return true;
			}
			
			if (args[2].equalsIgnoreCase("info")){
				player.sendMessage(ChatColor.GREEN+"---- '"+spawnerName+"' Spawner Info ----");
				player.sendMessage(ChatColor.BLUE+"[Speed]"+ChatColor.WHITE+" "+spawner.getSpeed());
				player.sendMessage(ChatColor.BLUE+"[Time]"+ChatColor.WHITE+" "+spawner.getTime());
				player.sendMessage(ChatColor.BLUE+"[Type]"+ChatColor.WHITE+" "+spawner.getTypeName());
				player.sendMessage(ChatColor.BLUE+"[Size]"+ChatColor.WHITE+" "+spawner.getSize());
				player.sendMessage(ChatColor.BLUE+"[Room]"+ChatColor.WHITE+" "+spawner.getRoom().getName());
				return true;
			}
			
			// Every other command is in the format /mobster spawn <name> <option> <setting> so must have 4 args
			if (args.length != 4) return false;
			
			if (args[2].equalsIgnoreCase("speed")){
				int speed = Integer.parseInt(args[3]);
				if (spawner.setSpeed(speed))
					msgSuccess(player, spawnerName+" speed changed to "+speed);
				else
					msgError(player, "Invalid spawn speed");
				return true;
			}
			
			if (args[2].equalsIgnoreCase("health")){
				int health = Integer.parseInt(args[3]);
				if (spawner.setHealth(health))
					msgSuccess(player, spawnerName+" mob health changed to "+health);
				else
					msgError(player, "Invalid mob size");
				return true;
			}
			
			if (args[2].equalsIgnoreCase("size")){
				int size = Integer.parseInt(args[3]);
				if (spawner.setSize(size))
					msgSuccess(player, spawnerName+" mob size changed to "+size);
				else
					msgError(player, "Invalid mob size");
				return true;
			}
			
			if (args[2].equalsIgnoreCase("type")){
				String type = args[3];
				if (spawner.setCreature(type))
					msgSuccess(player, spawnerName+" creature type changed to "+type);
				else
					msgError(player, "Invalid creature type");
				return true;
			}
			
			
		}else if (args[0].equalsIgnoreCase("room")){
			
			if (args.length < 3) return false;
			
			// Get room to be used in following functions
			String roomName = args[1];
			MobsterRoom room = mob.getRoom(roomName);
			
			if (args[2].equalsIgnoreCase("spawnlist")){
				int i = 1;
				player.sendMessage(ChatColor.GREEN+"---- Room '"+roomName+"' Spawner List ----");
				for (MobsterSpawner s : mob.spawnerList()){
					player.sendMessage(ChatColor.GREEN+"["+i+"]"+ChatColor.WHITE+" "+s.getName());
					i++;
				}
				return true;
			}
			
			if (args[2].equalsIgnoreCase("createin") && args.length==4){
				String dungeonName = args[3];
				MobsterDungeon dungeon = mob.getDungeon(dungeonName);
				if (dungeon == null)
					msgError(player, "Dungeon "+dungeonName+" does not exist");
				if (room != null)
					msgError(player, "Room '"+roomName+"' already exists");
				else{
					mob.createRoom(roomName, dungeon);
					msgSuccess(player,"Room '"+roomName+"' created in dungeon '"+dungeonName+"'");
				}
				return true;
			}
			
			
			
			
		}else if (args[0].equalsIgnoreCase("dungeon")){
			
			if (args[1].equalsIgnoreCase("list")){
				int i = 1;
				player.sendMessage(ChatColor.GREEN+"---- Mobster Dungeon List ----");
				for (MobsterDungeon d : mob.dungeonList()){
					player.sendMessage(ChatColor.GREEN+"["+i+"]"+ChatColor.WHITE+" "+d.getName());
					i++;
				}
				return true;
			}
			
			// All other commands for dungeons require the name given
			String dungeonName = args[1];
			MobsterDungeon dungeon = mob.getDungeon(dungeonName);
			
			// Everything after this is in the format /mobster dungeon <name> <action> so 3 args
			if (args.length != 3) return false;
			
			if (args[2].equalsIgnoreCase("create")){
				if(dungeon != null)
					msgError(player, "A dungeon named: "+dungeonName+" already exists!");
				else{
					mob.createDungeon(dungeonName);
					msgSuccess(player, "Dungeon '"+dungeonName+"' created");
				}
				return true;
			}
			
			if (args[2].equalsIgnoreCase("roomlist")){
				int i = 1;
				player.sendMessage(ChatColor.GREEN+"---- "+dungeonName+" Room List ----");
				for (MobsterRoom r : dungeon.roomList()){
					player.sendMessage(ChatColor.GREEN+"["+i+"]"+ChatColor.WHITE+" "+r.getName());
					i++;
				}
				return true;
			}
			
			if (args[2].equalsIgnoreCase("delete")){
				//Figure this out later
			}
			
			
		}
		return false;
	}

}
