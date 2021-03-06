package com.peter.entities;

public class Entity{
	// group - shopkeep, citizen, player etc.  race - human, demon, etc.
	// type - barkeep, drunk, etc.  name - roy benis, clive owen, etc.
	protected String race, type, name;
	public Integer ID;
	protected float tileWidth = 32, tileHeight = 32;
	protected int timeout = 0;
	public boolean canDraw = false;
	protected int x, y;
	public int floor;

	protected Stats stats;
	protected int oldX, oldY;
	public boolean collision;
	protected float messageDelay = 0, statusDelay = 0, delay, time = 0;
	

	public Entity(String race, String type){
		stats = new Stats();
		this.race = race;
		this.type = type;
		floor = 0;
	}
	
	public void update(double delta){
		time += delta;
	}

	public String getName(){
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public void setPosition(int x, int y){
		this.x = x*32;
		this.y = y*32;
		oldX = this.x;
		oldY = this.y;
	}
	public void setOldX(int oldX){
		this.oldX = oldX;
	}
	
	public void setOldY(int oldY){
		this.oldY = oldY;
	}
	
	public Stats getStats(){
		return stats;
	}
	public String getRace(){
		return race;
	}
}

