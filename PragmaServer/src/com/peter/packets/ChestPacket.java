package com.peter.packets;

import java.util.ArrayList;

public class ChestPacket {
	public ArrayList<ItemPacket> items;
	public String name;
	public int x, y;
	public Integer ID;

	public ChestPacket(){}
	public ChestPacket(String name, int x, int y, Integer ID){
		items = new ArrayList<ItemPacket>();
		this.name = name;
		this.x = x;
		this.y = y;
		this.ID = ID;
	}
}