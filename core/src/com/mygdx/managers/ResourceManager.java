/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.MainGame;
import java.util.HashMap;

/**
 *
 * @author looch
 */
public class ResourceManager {
    
    private final HashMap<String, TextureAtlas> atlases;
    private final HashMap<String, Integer> atlasLengths;
    private AssetManager asm;
    
    public ResourceManager(){
        atlases = new HashMap<String,TextureAtlas>();
        atlasLengths = new HashMap<String, Integer>();
    }
    
    public void loadAtlas(String path, String key){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path));
        atlases.put(key, atlas);
    }
    
    public void loadAtlas(String path, String key, int length){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path));
        atlases.put(key,atlas);
        atlasLengths.put(key, length);
    }
    
    public TextureAtlas getAtlas(String key){
        return atlases.get(key);
    }
    
    public int getAtlasLength(String key){
        return atlasLengths.get(key);
    }
    
    public void disposeAtlas(String key){
        TextureAtlas atlas = atlases.get(key);
        if(atlas != null) atlas.dispose();
    }
    
    
    
    /*
    
    Description:
        -Called during LoadingScreen on initial launch.
        -Only contains the vital resources for starting first game.
        
        -Call secondaryLoad() for all other resources.
    
        //example
        this.loadAtlas("entities/player/poe/poe-attack-heavy.atlas", "poe-attack-heavy", 27);
        
    */
    
    public void primaryLoad(){
        asm = MainGame.am;
        
        /********************
            SPRITES
        *********************/
        
        //ui
        //charge
        this.loadAtlas("gui/charge/charge.atlas", "charge", 6);
        
        //critters
        //poe
        this.loadAtlas("entities/player/poe/poe-side.atlas", "poe-side", 40);
        this.loadAtlas("entities/player/poe/poe-back.atlas", "poe-back", 36);
        
        /********************
            STATIC TEXTURES
        *********************/
        
        //placeholders
        asm.load(DEFAULT_SQUARE, Texture.class);
        asm.load(DEFAULT_SQCOLOR, Texture.class);
        
        //UI
        asm.load(UI_GRID, Texture.class);
        
        //ENEMIES
        asm.load(ENEMY_PH, Texture.class);
        asm.load(ENEMY_PH_DEAD, Texture.class);
        
        //CRITTERS
        asm.load(ENEMY_MOUSINE, Texture.class);
        asm.load(ENEMY_MOUSINE_DEAD, Texture.class);
        asm.load(ENEMY_CREEPIT, Texture.class);
        asm.load(ENEMY_CREEPIT_DEAD, Texture.class);
        asm.load(ENEMY_POE_IDLE, Texture.class);
        asm.load(ENEMY_POE_DEAD, Texture.class);
        asm.load(ENEMY_LUMEN_IDLE, Texture.class);
        asm.load(ENEMY_LUMEN_DEAD, Texture.class);
    }
    
    public void secondaryLoad(){}
    
    
    
    
    
    //*********************************
    //      TEXTURE PATHS
    //*********************************
    
    //create texture paths here
    //public static String MENU_NEW_GAME = "menus/button-newGame.png";
    
    
    //DEFAULT PLACEHOLDERS
    public static String DEFAULT_SQUARE = "square_ph.png";
    public static String DEFAULT_SQCOLOR = "environments/EnvVoid/void-bgB.png";
    
    //UI
    public static String UI_GRID = "gui/ui-grid.png"; 
    
    //ENEMIES
    public static String ENEMY_PH = "entities/enemies/peeker-main.png";
    public static String ENEMY_PH_DEAD = "entities/enemies/sloober-att.png";
    
    //CRITTERS
    public static String ENEMY_CREEPIT = "pokeman/critters/creepit1.png"; 
    public static String ENEMY_CREEPIT_DEAD  = "pokeman/critters/creepit-dead.png"; 
    public static String ENEMY_MOUSINE = "pokeman/critters/mousine1.png"; 
    public static String ENEMY_MOUSINE_DEAD = "pokeman/critters/mousine-dead.png"; 
    public static String ENEMY_POE_IDLE = "entities/player/poe/poe-idle.png";
    public static String ENEMY_POE_DEAD = "pokeman/critters/poe-dead.png";
    public static String ENEMY_LUMEN_IDLE = "pokeman/critters/lumen-front.png";
    public static String ENEMY_LUMEN_DEAD = "pokeman/critters/lumen-recov.png";
    
}

    