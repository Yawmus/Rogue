package com.peter.packets;

public class ItemPacket{
	public String name;
	public Integer ID;
	public int x, y;
	public int floor;
	
	public ItemPacket(){}
	public ItemPacket(String name, Integer ID, int floor){
		this.name = name;
		this.ID = ID;
		this.floor = floor;
	}
}
