package com.peter.rogue.entities;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.peter.rogue.Global;
import com.peter.rogue.inventory.Chest;
import com.peter.rogue.inventory.Food;
import com.peter.rogue.inventory.Head;
import com.peter.rogue.lighting.Light;
import com.peter.rogue.lighting.Line;
import com.peter.rogue.lighting.Point;
import com.peter.rogue.lighting.Rect;
import com.peter.rogue.views.UI;

public class EntityManager {

    private LinkedList<Entity> objects;
    
    private int randX, randY;
    private Player player;
    private UI ui = new UI();
    
	private ArrayList<Light> visible_lights;
	public static ArrayList<Rect> visible_rects;
	private ArrayList<Line> light_rays;
	private Rect rect;
    
    public EntityManager(){
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
    
	public void draw(){
		for(int i=0; i<objects.size(); i++){
			if(objects.get(i).isPickedUp()){
				objects.remove(i);
			}
			else
				objects.get(i).draw(Entity.map.getSpriteBatch());
		}
		
		for(int i=0; i<NPC.npcs.size(); i++)
			NPC.npcs.get(i).draw(Entity.map.getSpriteBatch());
			
		player.draw(Entity.map.getSpriteBatch());
		Entity.map.setView(Global.camera);
		ui.draw(player, NPC.npcs);
		light();
		
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
			NPC.npcs.add(new Citizen("c_.png"));
			NPC.npcs.get(NPC.npcs.size()-1).setPosition(randX, randY);
		}
    	for(int i=0; i<Entity.map.getData().getShopkeeps(); i++){
			randX = Global.rand(13, 3);
			randY = Global.rand(7, 3);
			NPC.npcs.add(new Shopkeep("s_.png"));
			NPC.npcs.get(NPC.npcs.size()-1).setPosition(randX, randY);
		}
    	for(int i=0; i<Entity.map.getData().getMonsters(); i++){
			randX = Global.rand(13, 3);
			randY = Global.rand(7, 3);
			NPC.npcs.add(new Worm("w.png"));
			NPC.npcs.get(NPC.npcs.size()-1).setPosition(randX, randY);
    	}

		
		visible_lights = new ArrayList<Light>();
		visible_lights.add(new Light(new Point(500,600), 3, 100000, Color.PINK));
		
		visible_rects = new ArrayList<Rect>();
		rect = new Rect(new Point(300f,300f),100,100);
		visible_rects.add(rect);
		visible_rects.add(new Rect(new Point(350f,350f),100,100));
		
		light_rays = new ArrayList<Line>();
		
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
    
    public void light(){
		Global.shapeRenderer.begin(ShapeType.Line);
		
		light_rays.clear();
		
		for(int i = 0; i < visible_lights.size(); i++)
		{
			visible_lights.get(i).update(player);
			
			Global.shapeRenderer.setColor(visible_lights.get(i).get_color());
			
			for(int j = 0; j < visible_rects.size(); j++)
			{
				light_rays.add(visible_lights.get(i).new_ray(visible_lights.get(i).get_position().angleRelativeToPoint(visible_rects.get(j).getUpperLeft())));
				light_rays.add(visible_lights.get(i).new_ray(visible_lights.get(i).get_position().angleRelativeToPoint(visible_rects.get(j).getUpperRight())));
				light_rays.add(visible_lights.get(i).new_ray(visible_lights.get(i).get_position().angleRelativeToPoint(visible_rects.get(j).getLowerLeft())));
				light_rays.add(visible_lights.get(i).new_ray(visible_lights.get(i).get_position().angleRelativeToPoint(visible_rects.get(j).getLowerRight())));
			}
		}
		for(int i = 0; i < light_rays.size(); i++)
		{
			Global.shapeRenderer.line(light_rays.get(i).begin.getX(), light_rays.get(i).begin.getY(), light_rays.get(i).end.getX(), light_rays.get(i).end.getY());
		}
		Global.shapeRenderer.end();
		
		Global.shapeRenderer.begin(ShapeType.Line);
		Global.shapeRenderer.setColor(Color.GREEN);
			for(int i = 0; i < visible_rects.size(); i++)
			{
				Global.shapeRenderer.rect(visible_rects.get(i).getUpperLeft().getX(), visible_rects.get(i).getUpperLeft().getY(), visible_rects.get(i).get_width(), visible_rects.get(i).get_height());
			}
			Global.shapeRenderer.end();
    }
    
    public void dispose(){
		for(int i=0; i<NPC.npcs.size(); i++)
			NPC.npcs.get(i).getTexture().dispose();
		player.getTexture().dispose();
		ui.dispose();
		Global.shapeRenderer.dispose();
    }
}
