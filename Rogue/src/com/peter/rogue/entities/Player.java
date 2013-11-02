package com.peter.rogue.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.peter.rogue.Global;
import com.peter.rogue.inventory.Chest;
import com.peter.rogue.inventory.Inventory;
import com.peter.rogue.inventory.Item;
import com.peter.rogue.map.Tile;

public class Player extends Animate implements InputProcessor {
	
	private float zoom = 1f;
	private Texture picture;
	private String info;
	private String menu = "null";
	private boolean menuActive = false;
	private Inventory inventory = new Inventory();
	private ArrayList<Ray> rays = new ArrayList<Ray>();
	private Entity menuObject;
	private int wallet;
	private int viewDistance;
    private boolean newMap = false;
	private boolean hostile;
	
	public Player(String filename){
		super(filename, "Player");
		messageFlag = false;
		name = "Adelaide";
		picture = new Texture(Gdx.files.internal("img/adelaide.png"));
		info = new String();
		viewDistance = 224;
		hostile = false;
		setWallet(0);
		stats.setDexterity(5);
		stats.setMaxExperience(100);
		stats.setExperience(0);
		stats.setLevel(1);
		stats.setStrength(5);
		stats.setHitpoints(20);
		stats.setMaxHitpoints(20);
		stats.setLevelPending(false);
		canDraw = true;

		for(int i=0; i<40; i++){
			rays.add(new Ray(new Vector3(0, 0, 0), new Vector3(0, 0, 0)));
		}
	}

	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		/*Global.shapeRenderer.begin(ShapeType.Line);
		Global.shapeRenderer.setColor(1f, 0, 0, .1f);
		Global.shapeRenderer.circle(pos.x + getWidth()/2, pos.y + getHeight()/2, viewDistance);
		Global.shapeRenderer.end();*/
		update(Gdx.graphics.getDeltaTime());
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public Texture getPicture(){
		return picture;
	}
	
	public void update(float delta){
		super.update(delta);
		
		if(delay >= .15){
			if(Gdx.input.isKeyPressed(Keys.A)){
				setX(getX() - 32);
				delay = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.D)){
				setX(getX() + 32);
				delay = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.S)){
				setY(getY() - 32);
				delay = 0;
			}
			if(Gdx.input.isKeyPressed(Keys.W)){
				setY(getY() + 32);
				delay = 0;
			}
			if(delay == 0f)
				menuActive = false;
			checkCollision();
		}
	}
	
	public void checkCollision(){

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
			if(map.get(getX(), getY()).getType().equals("Item")){
				inventory.add((Item)map.get(getX(), getY()));
				map.remove(map.get(getX(), getY()).getID());
			}
			
			else if(map.get(getX(), getY()).getType().equals("Chest")){
				setMenu("Chest");
				setMenuObject((Chest)map.get(getX(), getY()));
				bump();
			}
			
			else
				if(isHostile())
					attack((Animate) map.get(getX(), getY()));
				else
					bump((Animate) map.get(getX(), getY()));
		}

		map.setMark("", oldX, oldY);
		map.setMark(ID, getX(), getY());
		oldX = getX();
		oldY = getY();
	}
	
    
    public void light(){
		Player.pos = new Vector3(getX(), getY(), 0);
		Global.camera.project(Player.pos);
		Global.mapShapes.begin(ShapeType.Line);
		Global.mapShapes.setColor(Color.YELLOW);
		
		for(int i=0; i<rays.size(); i++){
			rays.get(i).set(new Vector3(getX()+getWidth()/2, getY()+getHeight()/2, 0), 
					        new Vector3((float)(getViewDistance()*Math.cos((2*Math.PI*i)/rays.size()) + getX()+getWidth()/2), 
					        		    (float)(getViewDistance()*Math.sin((2*Math.PI*i)/rays.size()) + getY()+getHeight()/2), 0f));
    		map.getSpriteBatch().begin();
			rays.get(i).direction.set(intersect(rays.get(i)));
    		map.getSpriteBatch().end();
			Global.mapShapes.line(rays.get(i).origin, rays.get(i).direction);
		}
		
		Global.mapShapes.end();
    }
    
    public Vector3 intersect(Ray ray){
    	float x, y;
    	for(int i=1; i<=getViewDistance()/32; i++){
    		x = ray.origin.x + (((ray.direction.x - ray.origin.x)*i)/(getViewDistance()/32));
    		y = ray.origin.y + (((ray.direction.y - ray.origin.y)*i)/(getViewDistance()/32));
    		Tile tile = map.getTile(x, y);
	    	if(tile.isBlocked()){
	    		
	    		return new Vector3(ray.origin.x + (((ray.direction.x - ray.origin.x)*i)/(getViewDistance()/32)), 
	    				           ray.origin.y + (((ray.direction.y - ray.origin.y)*i)/(getViewDistance()/32)), 0);
	    	}
	    	// Render entities when in sight
	    	else if(!(map.getMark(x, y).equals(ID) || map.getMark(x, y).equals(""))){
		    	map.get(x, y).canDraw = true;
	    	}
    	}
    	return ray.direction;
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
			Global.camera.zoom += .4;
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
		this.info = info;
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
	
	public boolean isDead(){
		if(stats.getHitpoints() <= 0)
			return true;
		return false;
	}

	public boolean isHostile() {
		return hostile;
	}

	public void setHostility(boolean hostile) {
		this.hostile = hostile;
	}
	

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.TAB:
			Global.camera.zoom -= .4;
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
