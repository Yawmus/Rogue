package com.peter.inventory;

public class Wearable extends Item{
	public static Wearable HELMET = new Wearable("Wearable$Head$Helmet", 5, 17, "^.png", 3, '^');
	public static Wearable HAT = new Wearable("Wearable$Head$Hat", 2, 6, "^.png", 1, '^');
	public static Wearable BREAST_PLATE = new Wearable("Wearable$Head$Breast Plate", 5, 17, "[.png", 3, '[');
	public static Wearable SHOES = new Wearable("Wearable$Feet$Shoes", 2, 6, "congruent.png", 1, '\u2245');
	public static Wearable WOODEN_RING = new Wearable("Wearable$Ring$Wooden Ring", 1, 20, "dash.png", 1, '-');
	
	private int defense;
	
	public Wearable(String name, int weight, int value, String filename, int defense, char symbol) {
		super(name, weight, value, filename, symbol);
		this.defense = defense;
	}
	
	public Wearable(Wearable item) {
		super((Item) item);
		this.defense = item.defense;
	}
	public int getDefense(){
		return defense;
	}
	public String getGroup(){
		return name.split("\\$")[1];
	}
}