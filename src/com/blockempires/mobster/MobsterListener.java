package com.blockempires.mobster;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class MobsterListener {
	private MobsterDungeon dungeon;
	
	public MobsterListener(MobsterDungeon dungeon){
		this.dungeon=dungeon;
	}
	
	
	public void onCreatureSpawn(CreatureSpawnEvent event){
		if (!dungeon.enabled)
			return;
		LivingEntity entity = (LivingEntity) event.getEntity();
		if(entity instanceof Slime)
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
	
	public void onEntityDamage(EntityDamageEvent event)
	{
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

	private void onPlayerDamage(EntityDamageEvent event, Player player, Entity damager)
	{
        // Will handle player damage in the future
    }
	
	// Called when a monster in this dungeon is damaged
	 private void onMonsterDamage(EntityDamageEvent event, Entity monster, Entity damager)
	 {
		// Use our virtual "entity"
		 MobsterMonster m = dungeon.getMonster(monster);

		 MobsterPlugin.info("Preparing to deal "+event.getDamage()+" to creature with "+m.getHealth());
		 // Take away virtual HP but keep actual full
		 m.subtractHealth(event.getDamage());
		 m.getEntity().setHealth(100);
		 
		 // Mimic real damage
		 event.setDamage(1);
		 
		 MobsterPlugin.info("You hit a "+m.creature.getName()+" which now has "+m.getHealth()+" HP");
		 
		 // If virtual HP is gone, kill it
		 if (m.getHealth() <= 0)
			 dungeon.killMonster(monster);		 
	 }
	 
	 public void onEntityCombust(EntityCombustEvent event){
		 if (!dungeon.enabled) 
			 return;
		 
		 // Don't let things in our dungeons combust
		 if (dungeon.hasMonster(event.getEntity())){
			 event.setCancelled(true);
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
}
