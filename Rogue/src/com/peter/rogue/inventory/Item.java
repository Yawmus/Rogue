package com.peter.rogue.inventory;

import com.peter.rogue.entities.Entity;

public class Item extends Entity{
	private int weight;
	public Item(String name, int weight, String filename){
		super(filename, "Item");
		this.name = name;
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}
}