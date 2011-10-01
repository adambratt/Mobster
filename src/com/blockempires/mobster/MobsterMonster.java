package com.blockempires.mobster;

import org.bukkit.entity.LivingEntity;

public class MobsterMonster {
	
	public MobsterCreature creature;
	private LivingEntity entity;
	private int healthAmount;
	
	public MobsterMonster(LivingEntity e){
		entity=e;
		// Default health value
		healthAmount = 20;
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
		getEntity().remove();	
		entity = null;
	}
    

}
