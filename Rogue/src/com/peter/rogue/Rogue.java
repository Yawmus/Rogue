package com.peter.rogue;

import java.util.Scanner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.peter.rogue.network.ClientWrapper;
import com.peter.rogue.screens.Death;
import com.peter.rogue.screens.Play;
import com.peter.rogue.screens.Splash;

public class Rogue extends Game{
	
	public static final String TITLE = "Rogue", VERSION = "0.5.8";

	Splash splash;
	Play play;
	public static ClientWrapper clientWrapper;
    public static Scanner in;
	
	@Override
	public void create() {
		in = new Scanner(System.in);
		splash = new Splash(this);

		Global.camera.setToOrtho(false);    
		/*System.out.print("IP?: ");
		String ip = in.nextLine();
		if(!ip.equals(""))
			Global.IP = ip;*/
		Global.IP = "209.213.46.189";
		setScreen(new Play(this));

		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
		
		if(Global.gameOver){
			setScreen(new Death(this));
		    if(Gdx.input.isKeyPressed(Keys.ENTER)){
		    	Global.gameOver = false;
		    	Global.multiplexer = new InputMultiplexer();
		    	setScreen(new Play(this));
		    }
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
