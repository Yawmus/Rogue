package com.peter.rogue.entities;

import java.util.LinkedList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class Animate extends Entity{
	protected Stats stats;
	protected Responses response;
	protected float oldX, oldY;
	protected static Scanner in;
	protected static LinkedList<String> firstNames;
	protected static LinkedList<String> lastNames;
	protected float wait = 0, messageDelay = 0, statusDelay = .6f, delay = 1;
	protected HostilityList list;
	protected String message;
	public boolean messageFlag;
	protected Integer status;
	public boolean statusFlag;
	protected String target;
	public static Vector3 pos = new Vector3();
	
	static{
		firstNames = new LinkedList<String>();
		lastNames = new LinkedList<String>();
		in = new Scanner(Gdx.files.internal("data/firstName.txt").readString());
		while(in.hasNextLine())
			firstNames.add(new String(in.nextLine()));
		in.close();
		in = new Scanner(Gdx.files.internal("data/lastName.txt").readString());
		while(in.hasNextLine())
			lastNames.add(new String(in.nextLine()));
		in.close();
	}
	
	public Animate(String filename, String type) {
		super(filename, type);
		list = new HostilityList();
		animate = true;
		stats = new Stats();
		response = new Responses(type);
		message = new String("");
		status = new Integer(0);
	}
	public void update(float delta){
		wait += Gdx.graphics.getDeltaTime();
		
		if(messageDelay > 2.0){
			resetMessage();
			messageDelay = 0;
			messageFlag = false;
		}
		
		if(getMessage() != ""){
			messageFlag = true;
			messageDelay += Gdx.graphics.getDeltaTime();
		}

		if(statusDelay > 2.0){
			resetStatus();
			statusDelay = 0;
			statusFlag = false;
		}
		
		if(getStatus() != 0){
			statusFlag = true;
			statusDelay += Gdx.graphics.getDeltaTime();
		}
	}
	@Override
	public void setPosition(float x, float y){
		
		setX(x * tileWidth);
		setY(y * tileHeight);

		oldX = getX();
		oldY = getY();
		
		map.put(ID, this);
		map.setMark(ID, getX(), getY());
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	
	public String getMessage() {
		return message;
	}
	
	public void resetMessage() {
		message = "";
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(int amount){
		if(!statusFlag)
			status = amount;
	}
	public void resetStatus() {
		status = 0;
	}

	protected void attack(Animate entity){
		int amount = -this.getStats().getStrength();
		entity.getStats().mutateHitpoints(amount);
		entity.setStatus(amount);
		bump(entity);
		if(entity.getStats().getHitpoints() <= 0){
			Entity.map.setMark("", entity.getX(), entity.getY());
			stats.mutateExperience(entity.type);
		}
	}

	protected void bump(Animate entity){
		if(type.equals("Player"))
			entity.setMessage((Player)this);
		else
			entity.setMessage((NPC)this);
		setX(oldX);
		setY(oldY);
	}

	protected void bump(){
		setX(oldX);
		setY(oldY);
	}
	
	public void setMessage(Player player){
		if(player.isHostile() && !list.check(player))
			list.addID(player.getID());
		if(!messageFlag)
			message = response.call(player, list.check(player));
	}
	
	public void setMessage(NPC npc){
		if(!messageFlag)
			if(type.equals("Player"))
				message = response.call(npc, npc.list.check(this));
			else
				message = response.call(npc, list.check(npc));
	}
}