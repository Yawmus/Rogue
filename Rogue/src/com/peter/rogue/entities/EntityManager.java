package com.peter.rogue.entities;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.peter.rogue.Global;
import com.peter.rogue.inventory.Chest;
import com.peter.rogue.inventory.Food;
import com.peter.rogue.inventory.Head;
import com.peter.rogue.views.UI;

public class EntityManager {

    private LinkedList<NPC> npcs;
    private LinkedList<Entity> objects;
    
    private int randX, randY;
    private Player player;
    private UI ui = new UI();
    
    public EntityManager(){
    	npcs = new LinkedList<NPC>();
    	objects = new LinkedList<Entity>();

		player = new Player("at.png");
		player.setPosition(18, 7);
		
		

		objects.add(new Chest());
		objects.getLast().setPosition(4, 4);
		
		objects.add(new Chest());
		objects.getLast().setPosition(6, 4);
		
		objects.add(Food.BREAD);
		objects.getLast().setPosition(8, 18);
		
		objects.add(Head.HAT);
		objects.getLast().setPosition(9, 18);
    }
    
	public void draw() throws Exception{
		for(int i=0; i<objects.size(); i++){
			if(objects.get(i).isPickedUp()){
				objects.remove(i);
			}
			else
				objects.get(i).draw(Entity.map.getSpriteBatch());
		}
		for(int i=0; i<npcs.size(); i++){
			//if(npcs.get(i).getX() > (player.getX() + player.getWidth()/2) - player.getViewDistance() && npcs.get(i).getX() < (player.getX() + player.getWidth()/2) + player.getViewDistance() &&
			//		npcs.get(i).getY() > (player.getY() + player.getHeight()/2) - player.getViewDistance() && npcs.get(i).getY() < (player.getY() + player.getHeight()/2) + player.getViewDistance())
			if(npcs.get(i).getStats().getHitpoints() <= 0){
				npcs.remove(i);
			}
			else
				npcs.get(i).draw(Entity.map.getSpriteBatch());
			//else
			//	npcs.get(i).update(Gdx.graphics.getDeltaTime());
		}
	
		if(player.isDead())
			throw new Exception(player.getName() + " has died!");
			
		player.draw(Entity.map.getSpriteBatch());
		
		//if(player.isNewMap())
		//	purge();
		
		Entity.map.setView(Global.camera);
		ui.draw(player, npcs);
		
		Animate.pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Global.camera.unproject(Animate.pos);

		if(Animate.pos.y + 32 > (Global.camera.position.y - Global.SCREEN_HEIGHT/3 + 12))
			player.setInformation(Entity.map.cursor(Entity.map.getMark(Animate.pos.x, Animate.pos.y)));
		else
			player.setInformation("");
		
		Global.camera.project(Animate.pos);
		Global.shapeRenderer.begin(ShapeType.Filled);
		Global.shapeRenderer.setColor(0, 0, 0, 1f);
		Global.shapeRenderer.rect(Animate.pos.x, Animate.pos.y, Entity.font.getBounds(player.getInformation()).width, Entity.font.getLineHeight());
		Global.shapeRenderer.end();
		Animate.pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Global.camera.unproject(Animate.pos);
		Entity.map.getSpriteBatch().begin();
		Entity.font.draw(Entity.map.getSpriteBatch(), player.getInformation(), Animate.pos.x, Animate.pos.y + Entity.font.getLineHeight());
    }
    
    public void init(){
    	for(int i=0; i<Entity.map.getData().getCitizens(); i++){
			randX = Global.rand(13, 3);
			randY = Global.rand(7, 3);
			npcs.add(new Citizen("c_.png"));
			npcs.getLast().setPosition(randX, randY);
		}
    	for(int i=Entity.map.getData().getCitizens(); i<Entity.map.getData().getCitizens() + Entity.map.getData().getShopkeeps(); i++){
			randX = Global.rand(13, 3);
			randY = Global.rand(7, 3);
			npcs.add(new Shopkeep("s_.png"));
			npcs.getLast().setPosition(randX, randY);
		}
    	for(int i=Entity.map.getData().getCitizens() + Entity.map.getData().getShopkeeps(); i<Entity.map.getData().getNPCTotal(); i++){
			randX = Global.rand(13, 3);
			randY = Global.rand(7, 3);
			npcs.add(new Monster("w.png"));
			npcs.getLast().setPosition(randX, randY);
    	}

		Gdx.input.setInputProcessor(player);
	}
    
    /*public void purge(){
    	Entity.map.clean(player.getID());
		npcs.clear();
		objects.clear();
		player.setNewMap();
		Entity.clearMap();
		
		init();
    }*/
    
    public void dispose(){
		for(int i=0; i<npcs.size(); i++)
			npcs.get(i).getTexture().dispose();
		player.getTexture().dispose();
		ui.dispose();
		Global.shapeRenderer.dispose();
    }
}
