package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.managers.InputProcessor_Gamepad;
import com.mygdx.managers.InputProcessor_Keyboard;
import com.mygdx.managers.GameKeyLibrary;
import com.mygdx.managers.ResourceManager;
import com.mygdx.managers.SoundManager;
import com.mygdx.screen.ScreenManager;
import com.mygdx.demo.DemoLoadScreen;
import com.mygdx.dev.DevLoadScreen;

public class MainGame extends ApplicationAdapter {

    public static final String TITLE = "Null and Void";
    public static int WIDTH = 1366, HEIGHT = 768;
    public static final float SCALE = 1 / 768f;
    public static float RATIO;
    public static boolean debugmode = false;
    public static boolean MUTE = true;
   
    //gfx
    public static ResourceManager rm = new ResourceManager();
    public static AssetManager am = new AssetManager();
    public static BitmapFont FONT_DMG;
    public static BitmapFont FONT_MAIN;
    
    public static final float STEP = 1 / 60f;
    private float acum;
    
    private SpriteBatch batch;
    
    //input/game pad
    public static boolean hasControllers = false, enableController = true;
    public static InputProcessor_Gamepad cip;
    
    //Game version
    public static int version = -1;
    
    public MainGame(int w, int h, boolean db, boolean m){ 
        super(); 
        
        WIDTH = w;
        HEIGHT = h;
        debugmode = db;
        MUTE = m;
    }
    
    public MainGame(int version, int w, int h, boolean db, boolean m){
        this(w,h,db, m);
        
        this.version = version;
    }
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        
        
        //set render ratio
        RATIO = MainGame.HEIGHT * MainGame.SCALE;
        
        //fonts
        FONT_DMG = new BitmapFont(Gdx.files.internal("fonts/nav-font.fnt"));
        FONT_MAIN = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        
        
        createVersion(version);
    }
    
    private void createVersion(int v){
        
        if (Controllers.getControllers().size > 0) {
            hasControllers = true;
        }

        //set input to keyboard if no controller
        //TODO:proper conditional
        if (!hasControllers || !enableController) {
            Gdx.input.setInputProcessor(new InputProcessor_Keyboard());
        } else {
            cip = new InputProcessor_Gamepad();
            Controllers.addListener(cip);
            Gdx.input.setInputProcessor(cip);

        }

        SoundManager.MUSIC_VOL = 0.65f;
        SoundManager.muted = MUTE;
        
        switch (v) {
            case 0:
                //screen
                ScreenManager.setScreen(new DevLoadScreen());
                break;
                
            case 1:

                //screen
                ScreenManager.setScreen(new DemoLoadScreen());

                break;

            default:

                //screen
                ScreenManager.setScreen(new DevLoadScreen());
                break;
        }
    }
    
    @Override 
    public void dispose(){
        if(ScreenManager.getCurrentScreen() != null)
            ScreenManager.getCurrentScreen().dispose();
        batch.dispose();
        
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        
        //acum += Gdx.graphics.getDeltaTime();
        //while(acum >= STEP){
            //acum -= STEP;
            
            //todo:screen update not needed, everything handles in render()
            if (ScreenManager.getCurrentScreen() != null) {
                ScreenManager.getCurrentScreen().update(STEP);
            }

            if (ScreenManager.getCurrentScreen() != null) {
                ScreenManager.getCurrentScreen().render(batch);
            }
        //}
            
        //input update
        GameKeyLibrary.update();
        
        if(null != cip) cip.update();
    }
    
    @Override 
    public void resize(int width, int height){
        
        //WIDTH = width;
        //HEIGHT = height;
        //RATIO = 1/ (float)HEIGHT;
        
        if(ScreenManager.getCurrentScreen() != null)
            ScreenManager.getCurrentScreen().resize(width,height);
    }
    
    @Override
    public void pause(){
        if(ScreenManager.getCurrentScreen() != null)
            ScreenManager.getCurrentScreen().pause();
    }
    
    @Override
    public void resume(){
        if(ScreenManager.getCurrentScreen() != null)
            ScreenManager.getCurrentScreen().resume();
    }
    
    
    
}
