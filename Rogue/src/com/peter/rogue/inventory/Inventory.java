package com.peter.rogue.inventory;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.peter.rogue.Global;

public class Inventory {
	
	private Backpack backpack = new Backpack();
	private LinkedList<Item> items;
	private int weight;
	public static final int HEIGHT = 300, WIDTH = 350;
	
	public Inventory(){
		backpack = Backpack.SMALL;
		items = new LinkedList<Item>();
	}
	
	public void add(Item item){
		if(item.getWeight() + weight <= backpack.getCapacity()){
			item.pickedUp(true);
			items.add(item);
		}
	}
	public void display(SpriteBatch spriteBatch, BitmapFont font){
		Global.shapeRenderer.begin(ShapeType.Filled);
		Global.shapeRenderer.setColor(0f, 0f, 0f, 1f);
		Global.shapeRenderer.rect(Global.SCREEN_WIDTH/2 + 50, Global.SCREEN_HEIGHT/3, WIDTH, HEIGHT);
		Global.shapeRenderer.end();
		Global.shapeRenderer.begin(ShapeType.Filled);
		Global.shapeRenderer.setColor(0.2f, 0.1f, 0.0f, 1f);
		Global.shapeRenderer.rect(Global.SCREEN_WIDTH/2 + 55, Global.SCREEN_HEIGHT/3 + 5, WIDTH/2 - 5, HEIGHT - 10);
		Global.shapeRenderer.end();
		Global.shapeRenderer.begin(ShapeType.Line);
		Global.shapeRenderer.setColor(.3f, .4f, 0, 1f);
		Global.shapeRenderer.rect(Global.SCREEN_WIDTH/2 + 50, Global.SCREEN_HEIGHT/3, WIDTH, HEIGHT);
		Global.shapeRenderer.end();

		spriteBatch.begin();
		for(int i=0; i<items.size(); i++){
			font.draw(spriteBatch, items.get(i).getName(),  Global.camera.position.x + 80, (Global.camera.position.y + HEIGHT/2) - i * 15);
		}
		spriteBatch.end();
	}
	public Texture getBackpack(){
		return backpack.getTexture();
	}
}