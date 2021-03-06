package com.peter.entities;

import com.peter.map.Map;
import com.peter.packets.AddNPCPacket;
import com.peter.server.Global;
import com.peter.server.PragmaServer;

public class Monster extends NPC{
	
	public static final String Worm = "Worm";
	
	private double multiplyDelay, multiplyRate;
	private boolean fertile;
	
	public Monster(int floor, String race) {
		super(floor, "Monster", race, null);

		if(race.equals("Worm")){
			fertile = true;
			multiplyRate = 20 - floor/2;
			name = race;
		}
		
    	list.addRace("Human");

    	stats.setLevel(floor == 0 ? 1 : floor + Global.rand(2, 0));
        stats.setExperience(2 * stats.getLevel());
        stats.setMaxHitpoints(5 * stats.getLevel() + stats.getLevel()/5 * Global.rand(2, -5));
        stats.setHitpoints(stats.getMaxHitpoints() - Global.rand(3, 0));
        stats.setStrength(stats.getLevel() < 3 ? 3 : stats.getLevel() * 2);
    	
    	multiplyDelay = Global.rand(8, -4);
	}
	
	/*public Monster(Monster monster) {
		this.list = monster.list;
		this.fertile = monster.fertile;
		this.type = monster.type;
		this.name = monster.name;
		
		multiplyDelay = Global.rand(8, -4);
	}*/
	
	public void update(double delta){
		super.update(delta);
		if(fertile && attacker instanceof Player){
            multiplyDelay += delta;
            if(multiplyDelay >= multiplyRate){
            	multiplyDelay = Global.rand(4, -2);
            	multiply();
            }
		}
	}
	
	public void multiply(){
		Monster monster = new Monster(floor, race);
		monster.ID = ++Global.count;
		monster.x = x;
		monster.y = y;
		monster.attacker = attacker;
        Map.npcSets.get(floor).put(Global.count, monster);
		Map.markSets.get(0).put(monster.ID, monster.getX(), monster.getY());
		
		AddNPCPacket packet = new AddNPCPacket(x, y, monster.stats.getLevel(), monster.ID, group, race, type, name);
		for(Player player : PragmaServer.map.players.values())
			if(player.floor == floor)
				PragmaServer.server.sendToTCP(player.ID, packet);
	}
}
