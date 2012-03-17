<<<<<<< HEAD
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
=======
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
>>>>>>> 06b74a88e3ec8a515fe4a8e3ef244404d69b3571
