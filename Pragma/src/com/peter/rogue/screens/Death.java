package com.peter.rogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.peter.rogue.Global;
import com.peter.rogue.Rogue;

public class Death implements Screen{
	
	private SpriteBatch spriteBatch = new SpriteBatch();

	private BitmapFont gothicFont;
	public Death(Rogue rogue) {
		gothicFont = new BitmapFont(Gdx.files.internal("fonts/Cardinal.fnt"), Gdx.files.internal("fonts/Cardinal.png"), false);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
   
	    spriteBatch.begin();
	    spriteBatch.draw(new Texture(Gdx.files.internal("img/death.png")), 0, 0);
	    gothicFont.setColor(Color.WHITE);
	    gothicFont.draw(spriteBatch, "You died!", Global.SCREEN_WIDTH/2 - 40, Global.SCREEN_HEIGHT/2 + 40);
	    gothicFont.draw(spriteBatch, "Press enter to respawn", Global.SCREEN_WIDTH/2 - 120, Global.SCREEN_HEIGHT/2);
	    spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		Global.camera.viewportWidth = width;
		Global.camera.viewportHeight = height;
	}
	
	@Override
	public void show() {

		Global.camera.setToOrtho(false);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
