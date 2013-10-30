package com.peter.rogue.entities;

public class Monster extends NPC{
	
	public Monster(String filename) {
		super(filename, "Worm");
		name = type;
		message = "*Gurgle*";
		list.addType("Citizen");
		list.addType("Shopkeep");
		

		stats.setLevel(1);
		stats.setHitpoints(10);
		stats.setDexterity(5);
		stats.setStrength(2);
		stats.setExperience(100);
	}
}
