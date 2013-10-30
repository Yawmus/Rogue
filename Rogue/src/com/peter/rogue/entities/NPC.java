package com.peter.rogue.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.peter.rogue.Global;

public class NPC extends Animate {

	protected int move;
	
	public NPC(String filename, String type) {
		super(filename, type);
		name = firstNames.get(Global.rand(firstNames.size(), 0)) + " " + lastNames.get(Global.rand(lastNames.size(), 0));
		wait -= Global.rand(100, 0) * .01f;
	}

	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		update(Gdx.graphics.getDeltaTime());
	}
	
	public void update(float delta){
		super.update(delta);
		
		move = Global.rand(5, 0);
		if(wait >= .6f + delay){
			switch(move){
			case 0:
				setY(getY() + 32);
				wait = 0;
				break;
			case 1:
				setY(getY() - 32);
				wait = 0;
				break;
			case 2:
				setX(getX() - 32);
				wait = 0;
				break;
			case 3:
				setX(getX() + 32);
				wait = 0;
				break;
			case 4:
				wait = 0;
			}
			checkCollision();
		}		
	}
	
	public void checkCollision(){
		if(map.getTile(getX(), getY()).isBlocked()){
			setX(oldX);
			setY(oldY);
		}
		if(!(map.getMark(getX(), getY()).equals("") || map.getMark(getX(), getY()).equals(ID))){
			if(map.get(getX(), getY()).getType().equals("Item") || map.get(getX(), getY()).getType().equals("Chest"))
				bump();
			else
				if(list.check((Animate)map.get(getX(), getY())))
					if(map.get(getX(), getY()).getType().equals("Player"))
						attack((Player) map.get(getX(), getY()));
					else
						attack((NPC) map.get(getX(), getY()));
				else
					bump((Animate) map.get(getX(), getY()));
		}
		

		map.setMark("", oldX, oldY);
		map.setMark(ID, getX(), getY());
		oldX = getX();
		oldY = getY();
	}
}

