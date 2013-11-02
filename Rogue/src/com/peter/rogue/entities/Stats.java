package com.peter.rogue.entities;

public class Stats{
	private int level;
	private boolean levelPending;
	private int dexterity;
	private int experience;
	private int maxExperience;
	private int strength;
	private int hitpoints;
	private int maxHitpoints;
	
	public Stats(){
		setLevel(1);
		setDexterity(5);
		setExperience(0);
		setStrength(5);
		setHitpoints(10);
		setMaxHitpoints(20);
		setMaxExperience(100);
		setLevelPending(false);
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void mutateHitpoints(int amount){
		this.hitpoints += amount;
	}
	
	public void addExperience(String type){
		if(type == "Worm")
			mutateExperience(5);
		else if(type == "Citizen")
			mutateExperience(1);
		else if(type == "Shopkeep")
			mutateExperience(1);
	}
	
	public void mutateExperience(int experience){
		this.experience += experience;
		if(this.experience >= maxExperience){
			setLevelPending(true);
			maxExperience += maxExperience*(level+1);
		}
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
	public void setMaxExperience(int maxExperience) {
		this.maxExperience = maxExperience;
	}
	
	public int getMaxExperience() {
		return maxExperience;
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
	
	public void setLevelPending(boolean status){
		levelPending = status;
	}
	
	public char getLevelPending(){
		if(levelPending)
			return '+';
		return ' ';
	}

	public int getMaxHitpoints() {
		return maxHitpoints;
	}

	public void setMaxHitpoints(int maxHitpoints) {
		this.maxHitpoints = maxHitpoints;
	}
}