package com.blockempires.mobster;

import org.bukkit.entity.LivingEntity;

public class MobsterMonster {
	
	public MobsterCreature creature;
	private LivingEntity entity;
	private int healthAmount;
	
	public MobsterMonster(LivingEntity e){
		entity=e;
		// Default health value
		healthAmount = 10;
	}

    public LivingEntity getEntity()
    {
        return entity;
    }
    
    public int getHealth()
    {
        return healthAmount;
    }
    
    public void setHealth(int healthAmount)
    {
        this.healthAmount = healthAmount;
    }
    
    public void subtractHealth(int amount)
    {
        healthAmount -= amount;
    }

	public void kill() {
		entity.damage(10000);
	}
	
	public void remove() {
		entity.remove();
		entity=null;
	}

	public int id() {
		int id = getEntity().getEntityId();
		return id;
	}
    

}
