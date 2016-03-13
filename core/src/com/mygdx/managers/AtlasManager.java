/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

/**
 *
 * @author looch
 */
public class AtlasManager {
    
    private final HashMap<String, Texture> textures;
    
    public AtlasManager(){
        textures = new HashMap<String,Texture>();
    }
    
    public void loadTexture(String path, String key){
        Texture texture = new Texture(Gdx.files.internal(path));
        textures.put(key,texture);
    }
    
    public Texture getTexture(String key){
        return textures.get(key);
    }
    
    public void disposeTexture(String key){
        Texture texture = textures.get(key);
        if(texture != null) texture.dispose();
    }
    
}
