/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui.descriptions;

import com.badlogic.gdx.Gdx;
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
    private final Texture cTexture_light, cTexture_heavy, cTexture_special;
    
    private String name, desc;
    private SkillType[] combo;
    
    private float 
            name_x, name_w,
            desc_x, desc_w,
            combo_x, combo_w;
    private float PADDING;
    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    
    public DescriptionWindow(String name, String desc, SkillType[] combo){
        
        texture = MainGame.am.get(ResourceManager.DESC_BG);
        this.name = name;
        this.desc = desc;
        this.combo = combo;
        
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setScale(0.475f * RATIO);
        effectFont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        effectFont.setScale(0.375f * RATIO);
        
        
        setSize(0,0);
        
        cTexture_light = MainGame.am.get(ResourceManager.COMBO_ICON_LIGHT);
        cTexture_heavy = MainGame.am.get(ResourceManager.COMBO_ICON_HEAVY);
        cTexture_special = MainGame.am.get(ResourceManager.COMBO_ICON_SPECIAL);
        
    }
    
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
        effectFont.draw(sb,  desc,    x + desc_x + PADDING,   y + font.getCapHeight()*2  + PADDING);
        
        //combo render
        effectFont.draw(sb, "Combo: ",          x + desc_x + PADDING,  y + font.getCapHeight() + PADDING);
        for(int i = 0 ; i < combo.length; i++){
            Texture t = null;
            switch(combo[i]){
                case LIGHT:
                    t = cTexture_light;
                    break;
                case HEAVY:
                    t = cTexture_heavy;
                    break;
                case SPECIAL:
                    t = cTexture_special;
                    break;
                case DEFENSE:
                    break;
                default:
                    break;
            }
            
            if(t != null){
                float tempx = x + desc_x + PADDING + effectFont.getBounds("Combo: ").width + i*48f*RATIO;
                sb.draw(t, tempx, y + PADDING*0.75f, 35f*RATIO, 35f*RATIO);
                
                if(i != combo.length-1){
                    effectFont.draw(sb, " +", tempx + 31f*RATIO, y + PADDING*0.75f + 35f*RATIO - effectFont.getBounds("+").height/2);
                }
            }
        }
    }
    
}
