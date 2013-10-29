package com.peter.rogue.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.peter.rogue.;
import com.peter.rogue.inventory.Chest;
import com.peter.rogue.inventory.Inventory;
import com.peter.rogue.inventory.Item;
import com.peter.rogue.map.Map;

public class Player extends Animate implements InputProcessor {
	
	private float zoom = 1f;
	private Texture picture;
	private String info;
	private String menu = "null";
	private boolean menuActive = false;
	private Inventory inventory = new Inventory();
	private Entity menuObject;
	private int wallet;
	private int viewDistance;
    private boolean newMap = false;
	
	public Player(String filename, String type){
		super(filename, type);
		messageFlag = false;
		name = "Adelaide";
		picture = new Texture(Gdx.files.internal("img/adelaide.png"));
		info = new String();
		viewDistance = 228;
		setWallet(0);
		hostile = false;
		stats.setLevel(1);
		stats.setHitpoints(10);
		stats.setDexterity(5);
		stats.setStrength(5);
		stats.setExperience(100);
	}

	public void draw(Map renderer){
		super.draw(renderer.getSpriteBatch());
		
		
		/*Global.shapeRenderer.begin(ShapeType.Line);
		Global.shapeRenderer.setColor(1f, 0, 0, .1f);
		Global.shapeRenderer.circle(pos.x + getWidth()/2, pos.y + getHeight()/2, viewDistance);
		Global.shapeRenderer.end();*/
		
		update(Gdx.graphics.getDeltaTime(), renderer);
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public Texture getPicture(){
		return picture;
	}
	
	public void update(float delta, Map renderer){
		wait += Gdx.graphics.getDeltaTime();
		
		if(wait >= .15){
			if(Gdx.input.isKeyPressed(Keys.A)){
				setX(getX() - 32);
				wait = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.D)){
				setX(getX() + 32);
				wait = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.S)){
				setY(getY() - 32);
				wait = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.W)){
				setY(getY() + 32);
				wait = 0;
			}
			if(wait == 0f)
				menuActive = false;
			checkCollision(renderer);
		}

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
			resetMessage();
			messageDelay = 0;
			messageFlag = false;
		}
		
		if(getStatus() != 0){
			messageFlag = true;
			messageDelay += Gdx.graphics.getDeltaTime();
		}
	}
	
	public void checkCollision(Map renderer){

		if(map.getTile(getX(), getY()).isBlocked()){
			setX(oldX);
			setY(oldY);
		}
		else if(map.getTile(getX(), getY()).hasStairs() && !map.getTile(oldX, oldY).hasStairs()){
			if(map.getTile(getX(), getY()).direction()){
				newMap = true;
				map.load(1, getX(), getY());
			}
			else{
				newMap = true;
				map.load(-1, getX(), getY());
			}
		}
		if(!(map.getMark(getX(), getY()).equals("") || map.getMark(getX(), getY()).equals(ID))){
			if(map.getEntity(getX(), getY()).getType().equals("Item"))
				inventory.add((Item)map.getEntity(getX(), getY()));
			
			else if(map.getEntity(getX(), getY()).getType().equals("Chest")){
				setMenu("Chest");
				setMenuObject((Chest)map.getEntity(getX(), getY()));
				setX(oldX);
				setY(oldY);
			}
			
			else
				if(isHostile())
					attack((Animate) map.getEntity(getX(), getY()));
				else
					bump((Animate) map.getEntity(getX(), getY()));
		}

		map.setMark("", oldX, oldY);
		map.setMark(ID, oldX, oldY);
		oldX = getX();
		oldY = getY();
	}
	
	private void attack(Animate entity){
		int amount = -this.getStats().getStrength();
		entity.getStats().mutateHitpoints(amount);
		entity.setStatus(amount);
	}
	
	private void attack(Animate entity){
		int amount = -this.getStats().getStrength();
		entity.getStats().mutateHitpoints(amount);
		entity.setStatus(amount);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.G:
			setMenu("Inventory");
			break;
		case Keys.F:
			hostile = !hostile;
			break;
		case Keys.TAB:
			.camera.zoom += .4;
			break;
		case Keys.ESCAPE:
			System.exit(0);
		}
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		zoom += (.1f / amount);
		return true;
	}

	// Mouse scroll zoom (may break)
	public float getZoom() {
		return zoom;
	}
	
	public void setInformation(String info){
		if(info != "null")
			this.info = info;
		else
			this.info = "";
	}
	
	public String getInformation(){
		return info;
	}
	
	public void setMenu(String request){
		menu = request;
		menuObject = null;
		menuActive = !menuActive;
	}
	
	public String getMenu(){
		return menu;
	}
	
	public boolean isMenuActive(){
		return menuActive;
	}
	
	public void setMenuObject(Chest chest){
		menuObject = chest;
	}
	public Chest getMenuObject(){
		return (Chest)menuObject;
	}
	
	public int getViewDistance() {
		return viewDistance;
	}

	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}
	
	public boolean isNewMap(){
		return newMap;
	}
	
	public void setNewMap(){
		newMap = !newMap;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.TAB:
			.camera.zoom -= .4;
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
}
