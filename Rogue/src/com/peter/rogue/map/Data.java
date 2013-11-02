package com.peter.rogue.map;

public class Data {
    private int citizens, shopkeeps, monsters;
    
    /*citizens = 400;
		shopkeeps = 190;
		monsters = 10;
		past 20fps*/

	public Data(){
		citizens = 400;
	shopkeeps = 190;
	monsters = 10;
		/*citizens = 4;
		shopkeeps = 1;
		monsters = 1;*/
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
