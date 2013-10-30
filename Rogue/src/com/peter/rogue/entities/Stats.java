package com.peter.rogue.entities;

public class Stats{
	private int level;
	private int dexterity;
	private int experience;
	private int strength;
	private int hitpoints;
	private int maxHitpoints;
	
	public Stats(){
		setLevel(1);
		setDexterity(5);
		setExperience(5);
		setStrength(5);
		setHitpoints(10);
		setMaxHitpoints(20);
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void mutateHitpoints(int amount){
		this.hitpoints += amount;
	}
	
	public void mutateExperience(String type){
		if(type == "Worm")
			experience += 5;
		else if(type == "Citizen")
			experience += 1;
		else if(type == "Shopkeep")
			experience += 1;
	}
	
	public void mutateExperience(int amount){
		experience += amount;
	}
	
	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public void setMaxHitpoints(int maxHitpoints) {
		this.maxHitpoints = maxHitpoints;
	}
}