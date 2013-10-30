package com.peter.rogue.map;

public class Data {
    private int citizens, shopkeeps, monsters;
    
    

	public Data(){
		citizens = 4;
		shopkeeps = 2;
		monsters = 1;
	}
	
	public int getCitizens() {
		return citizens;
	}
	public int getShopkeeps() {
		return shopkeeps;
	}
	public int getMonsters() {
		return monsters;
	}
	public int getNPCTotal() {
		return monsters + citizens + shopkeeps;
	}
}
