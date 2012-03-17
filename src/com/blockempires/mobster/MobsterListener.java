package com.blockempires.mobster;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public class MobsterListener implements Listener {
	private MobsterDungeon dungeon;
	
	public MobsterListener(){
		
	}
	
	
	public void getDung(MobsterDungeon d) {
		this.dungeon = d;
	}
	
	
	public void onCreatureSpawn(CreatureSpawnEvent event){
		if (!dungeon.enabled)
			return;
		LivingEntity entity = (LivingEntity) event.getEntity();
		
		// Should override WorldGuard region spawn protection
		if (MobsterPlugin.getWorldGuard() != null && event.isCancelled()){
			if (dungeon.inDungeon(entity.getLocation())){
				event.setCancelled(false);
			}
		}
		
		if (entity instanceof Slime)
			// Should handle this sometime soon....
			return;
	}
	
	
	public void onEntityDeath(EntityDeathEvent event)
	{
		if (event.getEntity() instanceof Player){
			onPlayerDeath(event, (Player) event.getEntity());
		}else{
			MobsterMonster m = dungeon.getMonster(event.getEntity());
			if(m != null)
				onMonsterDeath(event, m);
		}
	}
	
	
	private void onMonsterDeath(EntityDeathEvent event, MobsterMonster m)
	{
		dungeon.killMonster(event.getEntity());
	}

	private void onPlayerDeath(EntityDeathEvent event, Player entity) 
	{
		// Will handle player deaths in the future
	}
	
	
	public void onWeaponDamage(WeaponDamageEvent event){
		// Ignore PvP
		if(event.getEntity() instanceof Player)
			return;
		
		// Make sure we can get monster
		MobsterMonster m = dungeon.getMonster(event.getEntity());
		if(m == null)
			return;
		
		//MobsterPlugin.error("Dealing heroes damage of "+event.getDamage());
		
		dealMonsterDamage(m, event.getDamage());
		 
		 // Mimic real damage
		 event.setDamage(1);
				
	}
	
	public void onSkillDamage(SkillDamageEvent event){		
		// Ignore PvP
		if(event.getEntity() instanceof Player)
			return;
		
		// Make sure we can get monster
		MobsterMonster m = dungeon.getMonster(event.getEntity());
		if(m == null)
			return;
		
		dealMonsterDamage(m, event.getDamage());
		 
		 // Mimic real damage
		 event.setDamage(1);
	}
	
	
	public void onEntityDamage(EntityDamageEvent event){
		if (!dungeon.enabled) 
			return;
		
		EntityDamageByEntityEvent e = (event instanceof EntityDamageByEntityEvent) ? (EntityDamageByEntityEvent) event : null;
		Entity damagee = event.getEntity();
		Entity damager = null;
		if(e != null){
			damager = e.getDamager();
			if(damager instanceof Projectile)
				damager = ((Projectile) damager).getShooter(); 
		}
		
		//Player
		if (damagee instanceof Player) // Also needs to check if player is in this arena
			onPlayerDamage(event, (Player) damagee, damager);
		// Monster
        else if (dungeon.hasMonster(damagee)){
        	onMonsterDamage(event, damagee, damager);
        }
		
	}

	
	private void onPlayerDamage(EntityDamageEvent event, Player player, Entity damager){
        // Will handle player damage in the future
    }
	
	// Called when a monster in this dungeon is damaged
	 private void onMonsterDamage(EntityDamageEvent event, Entity monster, Entity damager){
		 // If using heroes, we will let it do the work
		 if(MobsterPlugin.heroesPlugin != null && damager instanceof Player){
			 return;
		 }

		 MobsterMonster m = dungeon.getMonster(monster);	 
		 dealMonsterDamage(m, event.getDamage());
		 
		 // Mimic real damage
		 event.setDamage(1);
	 }
	 
	 private void dealMonsterDamage(MobsterMonster monster, int damage){
		 
		 //MobsterPlugin.error("mobster hp:"+monster.getHealth()+" doing damage of: "+damage);
		 
		// Take away virtual HP but keep actual full
		 monster.subtractHealth(damage);
		 
		 
		 //MobsterPlugin.error("normal hp (before):"+monster.getEntity().getHealth());
		 
		 /*switch(monster.creature.getType()){
		 	case MUSHROOM_COW:
		 		monster.getEntity().setHealth(10);
		 		break;
		 	case SLIME:
		 		monster.getEntity().setHealth(1);
		 		break;
		 	case CAVE_SPIDER:
		 		monster.getEntity().setHealth(12);
		 		break;
		 	case SKELETON:
		 	case ZOMBIE:
		 		monster.getEntity().setHealth(20);
		 		break;
		 	case SPIDER:
		 		monster.getEntity().setHealth(16);
		 		break;
		 	default:
		 		monster.getEntity().setHealth(4);
		 		break;
		 }*/
		 
		 monster.getEntity().setHealth(monster.getEntity().getMaxHealth());
		 
		 //MobsterPlugin.error("mobster did damage hp is now:"+monster.getHealth());
		 //MobsterPlugin.error("normal hp (after):"+monster.getEntity().getHealth()+" is alive?"+monster.getEntity().isDead());
		 
		 
		 // If virtual HP is gone, kill it
		 if (monster.getHealth() <= 0) {
			 //MobsterPlugin.error("killing monster with mobster");
			 //NEED TO FIX THIS, it's looping through when we already have the mobster monster object
			 dungeon.killMonster(monster.getEntity());
		 }
	 }
	 
	 
	 public void onEntityCombust(EntityCombustEvent event){
		 if (!dungeon.enabled) 
			 return;
		 
		 // Don't let things in our dungeons combust
		 if (dungeon.hasMonster(event.getEntity())){
			 if (MobsterPlugin.heroesPlugin == null){
				 event.setCancelled(true);
			 }
		 }
	 } 
	 
	 
	 public void onEntityTarget(EntityTargetEvent event)
	 {
		 if (!dungeon.enabled) 
			 return;
		 
		 if(dungeon.hasMonster(event.getEntity())){
			 if (event.getReason() == TargetReason.FORGOT_TARGET)
                // Choose a new player target inside this room with event.setTarget()
				 return;
             else if (event.getReason() == TargetReason.TARGET_DIED)
                // Player died, move on to the next one
            	 return;
             else if (event.getReason() == TargetReason.TARGET_ATTACKED_ENTITY)
                // Possibly ignore the attack 
            	 return;
             else if (event.getReason() == TargetReason.CLOSEST_PLAYER)
                // Cancel this event if the closest player is not in the room
            	 return;
		 }
	 }
	 
	 
	 public void onChunkLoad(ChunkLoadEvent event) {
		for (MobsterRoom room : dungeon.roomList()){
			for (MobsterSpawner spawn : room.spawnerList()){
				if(spawn.getLocation().getBlock().getChunk() == event.getChunk()){
					spawn.reset();
				}
			}
		}
	 }

	 
	 public void onChunkUnLoad(ChunkUnloadEvent event) {
		for (MobsterRoom room : dungeon.roomList()){
			for (MobsterSpawner spawn : room.spawnerList()){
				if(spawn.getLocation().getBlock().getChunk() == event.getChunk()){
					spawn.reset();
				}
			}
		}
	 }	
}
