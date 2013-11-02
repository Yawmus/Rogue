package com.peter.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.peter.rogue.Global;
import com.peter.rogue.Rogue;
import com.peter.rogue.entities.Entity;
import com.peter.rogue.entities.EntityManager;

public class Play implements Screen{

	//private Rogue game;
	
	@SuppressWarnings("unused")
	private FPSLogger fps;
	
    private EntityManager manager;
    
    
	public Play(Rogue game){
	//	this.game = game;
		fps = new FPSLogger();
	}
	
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

	    fps.log();

		Global.mapShapes.setProjectionMatrix(Global.camera.combined);
		Entity.map.getSpriteBatch().setProjectionMatrix(Global.camera.combined);
	    Entity.map.setView(Global.camera);
	    
		Entity.map.draw();
		manager.draw();
	}

	@Override
	public void resize(int width, int height) {
		Global.camera.viewportWidth = width;
		Global.camera.viewportHeight = height;
	}
	
	@Override
	public void show() {
		manager = new EntityManager();
		manager.init();

		Global.camera.setToOrtho(false);

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		//renderer.dispose();
		//entityManager.dispose();
	}

}
