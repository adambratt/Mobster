
package com.blockempires.mobster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public enum MobsterCreature {
    // Default creatures
    ZOMBIE(EntityType.ZOMBIE),            ZOMBIES(EntityType.ZOMBIE), 
    SKELETON(EntityType.SKELETON),        SKELETONS(EntityType.SKELETON),
    SPIDER(EntityType.SPIDER),            SPIDERS(EntityType.SPIDER),
    CREEPER(EntityType.CREEPER),          CREEPERS(EntityType.CREEPER),
    WOLF(EntityType.WOLF),                WOLVES(EntityType.WOLF),
    ENDERMAN(EntityType.ENDERMAN),        ENDERMEN(EntityType.ENDERMAN),
    SILVERFISH(EntityType.SILVERFISH),	SILVER(EntityType.SILVERFISH),
    
    // Special creatures
    ZOMBIEPIGMAN(EntityType.PIG_ZOMBIE),  ZOMBIEPIGMEN(EntityType.PIG_ZOMBIE),
    HUMAN(EntityType.PLAYER),            HUMANS(EntityType.PLAYER),
    GIANT(EntityType.GIANT),              GIANTS(EntityType.GIANT),
    GHAST(EntityType.GHAST),              GHASTS(EntityType.GHAST),
    CAVESPIDER(EntityType.CAVE_SPIDER),	CAVESPIDERS(EntityType.CAVE_SPIDER),
    BLAZE(EntityType.BLAZE),              BLAZES(EntityType.BLAZE),
    ENDERDRAGON(EntityType.ENDER_DRAGON),
    SLIME(EntityType.SLIME),
    SNOWMAN(EntityType.SNOWMAN),          SNOWGOLEM(EntityType.SNOWMAN),
    MAGMACUBE(EntityType.MAGMA_CUBE),     
    
    // Passive creatures
    CHICKEN(EntityType.CHICKEN),          CHICKENS(EntityType.CHICKEN),
    COW(EntityType.COW),                  COWS(EntityType.COW),
    PIG(EntityType.PIG),                  PIGS(EntityType.PIG),
    SHEEP(EntityType.SHEEP),
    MUSHROOMCOW(EntityType.MUSHROOM_COW),
    VILLAGER(EntityType.VILLAGER),
    SQUID(EntityType.SQUID),              SQUIDS(EntityType.SQUID);
    
    
    private EntityType type;
    
    private MobsterCreature(EntityType type)
    {
        this.type = type;
    }
    
    public EntityType getType()
    {
        return type;
    }
    
    public String getName(){
    	return type.getName();
    }
    
    public LivingEntity spawn(Location loc)
    {
        LivingEntity e = loc.getWorld().spawnCreature(loc, this.type);
        return e;
    }
    
    public MobsterMonster spawnMonster(Location loc){
    	LivingEntity e = spawn(loc);
    	MobsterMonster m = new MobsterMonster(e);
    	m.creature = this;
    	return m;
    }
}
