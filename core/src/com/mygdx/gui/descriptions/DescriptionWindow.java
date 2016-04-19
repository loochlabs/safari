/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.descriptions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.combat.skills.Skill.SkillType;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class DescriptionWindow {
    
    private float width,height;
    private Texture texture; //iconTexture;
    private final BitmapFont font, effectFont;
    
    private String name, desc;
    private SkillType[] combo;
    
    private float 
            name_x, name_y, name_w, name_h,
            desc_x, desc_y, desc_w, desc_h,
            combo_x, combo_y, combo_w, combo_h;
    private float PADDING;
    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    
    public DescriptionWindow(String name, String desc, SkillType[] combo){
        
        texture = MainGame.am.get(ResourceManager.DESC_BG);
        this.name = name;
        this.desc = desc;
        this.combo = combo;
        
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setScale(0.6f * RATIO);
        effectFont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        effectFont.setScale(0.5f * RATIO);
        
        
        setSize(0,0);
        
    }
    
    /*
    public DescriptionWindow(String name, String desc, SkillType type, boolean yel){
        this(name,desc,type);
        
        if(yel){
            effectFont.setColor(1, 1, 0.6f, 1.0f);//pale yellow
            equipable = false;
        }
    }*/
    
    public void setSize(float x, float y){
        height = (font.getCapHeight()*3.5f) * RATIO;
        PADDING = height * 0.095f;
        name_w = font.getBounds(name).width;
        desc_w = effectFont.getBounds(desc).width;
        combo_w = effectFont.getBounds("Combo: ").width;
        width = desc_w > name_w + PADDING*3 ? desc_w : name_w + PADDING*3;
        width += PADDING*2;
        height += PADDING;
        
        name_x = width/2 - name_w/2;
        desc_x = width/2 - desc_w/2;
        combo_x = width/2 - combo_w/2;
    
    }
    
    //TODO: Create setSize() method to dynamically scale descWindow
    //bg - width, height
    //text1 - NEED TO ADJUST font
    //text2 - NEED TO ADJUST typeFont
    //text3 - NEED TO ADJUST effectFont
    
    public void render(SpriteBatch sb, float x, float y){
        sb.draw(texture, x, y, width, height);
        font.draw(sb,       name,               x + name_x + PADDING,   y + font.getCapHeight()*3 + PADDING*2);
        effectFont.draw(sb, "\""+ desc+"\"",    x + desc_x + PADDING,   y + font.getCapHeight()*2  + PADDING);
        effectFont.draw(sb, "Combo: ",          x + combo_x + PADDING,  y + font.getCapHeight() + PADDING);
    }
    
}
