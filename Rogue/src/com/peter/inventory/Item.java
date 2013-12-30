package com.peter.inventory;

import com.peter.entities.Entity;

public class Item extends Entity{
	
	public static Item GOLD = new Item("Gold", 0, 5, "$.png", '$');
	public static Item GEM_DIAMOND = new Item("Diamond", 0, 25, "asterisk.png", '*');
	public static Item GEM_RUBY = new Item("Ruby", 0, 10, "asterisk.png", '*');
	public static Item GEM_TOPAZ = new Item("Topaz", 0, 10, "asterisk.png", '*');
	public static Item GEM_SPESSARITE = new Item("Spessartite", 0, 20, "asterisk.png", '*');
	
	private int weight;
	private int value;
	private String filename;
	private boolean pickedUp;
	
	public Item(String name, int weight, int value, String filename, char symbol){
		super(filename, "Item", name);
		this.weight = weight;
		this.value = value;
		this.filename = filename;
		this.symbol = symbol;
	}
	
	public Item(Item item){
		super(item.filename, item.type, item.name);
		this.name = item.name;
		this.type = item.type;
		this.weight = item.weight;
		this.value = item.value;
		this.filename = item.filename;
		this.symbol = item.symbol;
	}

	public void setPosition(int x, int y){
		setX(x * 32);
		setY(y * 32);

		pickedUp = false;
	}
	
	public int getWeight() {
		return weight;
	}
	public int getValue(){
		return value;
	}
	public String getFilename() {
		return filename;
	}
	public void pickedUp(boolean pickedUp){
		this.pickedUp = pickedUp;
	}
	public boolean isPickedUp(){
		return pickedUp;
	}
}