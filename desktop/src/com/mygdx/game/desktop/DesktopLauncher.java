package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainGame;

public class DesktopLauncher {
    
    private static int WIDTH = 1366;
    private static int HEIGHT = 768;
    private static boolean fullscreen = false;
    private static boolean debug = false;
    private static boolean muted;
    private static int ver = 0;
    
    public DesktopLauncher(String version, String screenres, boolean fs, boolean db, boolean m){
        super();
        
        fullscreen = fs;
        debug = db;
        muted = m;
        
        if(screenres.equals("1366x768")){
            WIDTH = 1366;
            HEIGHT = 768;
        }else if(screenres.equals("1920x1080")){
            WIDTH = 1920;
            HEIGHT = 1080;
        }else if(screenres.equals("1600x900")){
            WIDTH = 1600;
            HEIGHT = 900;
        }
        
        if(version.equals("Dev stuff")){
            ver = 0;
        }else if(version.equals("Demo")){
            ver = 1;
        }
    }
    
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                
                
                
                config.title = MainGame.TITLE;
                config.width = WIDTH;
                config.height = HEIGHT;
                config.useGL30 = false;
                config.resizable = false;
                config.fullscreen = fullscreen;
                
                //new LwjglApplication(new MainGame(), config);
                new LwjglApplication(new MainGame(ver, WIDTH, HEIGHT, debug, muted), config);
	}
}
