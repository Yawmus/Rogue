package com.peter.map;


public class Data {
    private int citizens, shopkeeps, monsters;
    
    /*
		citizens = 2000;
		shopkeeps = 38000;
		monsters = 10;
		past ~35fps*/

	public Data(){
		citizens = 8;
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
