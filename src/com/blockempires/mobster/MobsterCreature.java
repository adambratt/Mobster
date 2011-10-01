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
    
    // Special creatures
    ZOMBIEPIGMAN(CreatureType.PIG_ZOMBIE),  ZOMBIEPIGMEN(CreatureType.PIG_ZOMBIE),
    HUMAN(CreatureType.MONSTER),            HUMANS(CreatureType.MONSTER),
    GIANT(CreatureType.GIANT),              GIANTS(CreatureType.GIANT),
    GHAST(CreatureType.GHAST),              GHASTS(CreatureType.GHAST),
    
    // Passive creatures
    CHICKEN(CreatureType.CHICKEN),          CHICKENS(CreatureType.CHICKEN),
    COW(CreatureType.COW),                  COWS(CreatureType.COW),
    PIG(CreatureType.PIG),                  PIGS(CreatureType.PIG),
    SHEEP(CreatureType.SHEEP),
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
