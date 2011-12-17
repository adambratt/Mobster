package com.blockempires.mobster;

import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;

public enum MobsterCreature {
    // Default creatures
    ZOMBIE(CreatureType.ZOMBIE),            ZOMBIES(CreatureType.ZOMBIE), 
    SKELETON(CreatureType.SKELETON),        SKELETONS(CreatureType.SKELETON),
    SPIDER(CreatureType.SPIDER),            SPIDERS(CreatureType.SPIDER),
    CREEPER(CreatureType.CREEPER),          CREEPERS(CreatureType.CREEPER),
    WOLF(CreatureType.WOLF),                WOLVES(CreatureType.WOLF),
    ENDERMAN(CreatureType.ENDERMAN),        ENDERMEN(CreatureType.ENDERMAN),
    SILVERFISH(CreatureType.SILVERFISH),	SILVER(CreatureType.SILVERFISH),
    
    // Special creatures
    ZOMBIEPIGMAN(CreatureType.PIG_ZOMBIE),  ZOMBIEPIGMEN(CreatureType.PIG_ZOMBIE),
    HUMAN(CreatureType.MONSTER),            HUMANS(CreatureType.MONSTER),
    GIANT(CreatureType.GIANT),              GIANTS(CreatureType.GIANT),
    GHAST(CreatureType.GHAST),              GHASTS(CreatureType.GHAST),
    CAVESPIDER(CreatureType.CAVE_SPIDER),	CAVESPIDERS(CreatureType.CAVE_SPIDER),
    BLAZE(CreatureType.BLAZE),              BLAZES(CreatureType.BLAZE),
    ENDERDRAGON(CreatureType.ENDER_DRAGON),
    SLIME(CreatureType.SLIME),
    SNOWMAN(CreatureType.SNOWMAN),          SNOWGOLEM(CreatureType.SNOWMAN),
    MAGMACUBE(CreatureType.MAGMA_CUBE),     
    
    // Passive creatures
    CHICKEN(CreatureType.CHICKEN),          CHICKENS(CreatureType.CHICKEN),
    COW(CreatureType.COW),                  COWS(CreatureType.COW),
    PIG(CreatureType.PIG),                  PIGS(CreatureType.PIG),
    SHEEP(CreatureType.SHEEP),
    MUSHROOMCOW(CreatureType.MUSHROOM_COW),
    VILLAGER(CreatureType.VILLAGER),
    SQUID(CreatureType.SQUID),              SQUIDS(CreatureType.SQUID);
    
    
    private CreatureType type;
    
    private MobsterCreature(CreatureType type)
    {
        this.type = type;
    }
    
    public CreatureType getType()
    {
        return type;
    }
    
    public String getName(){
    	return type.getName();
    }
    
    public LivingEntity spawn(Location loc)
    {
        LivingEntity e = loc.getWorld().spawnCreature(loc, type);    
        return e;
    }
    
    public MobsterMonster spawnMonster(Location loc){
    	LivingEntity e = spawn(loc);
    	MobsterMonster m = new MobsterMonster(e);
    	m.creature = this;
    	return m;
    }
}
