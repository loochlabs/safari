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
        
        //placeholders
        asm.load(DEFAULT_SQUARE, Texture.class);
        
        //UI
        asm.load(UI_GRID, Texture.class);
    }
    
    public void secondaryLoad(){}
    
    
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
    
    
    //*********************************
    //      TEXTURE PATHS
    //*********************************
    
    //create texture paths here
    //public static String MENU_NEW_GAME = "menus/button-newGame.png";
    
    
    //DEFAULT PLACEHOLDERS
    public static String DEFAULT_SQUARE = "square_ph.png";
    
    //UI
    public static String UI_GRID = "gui/ui-grid.png"; 
}

    