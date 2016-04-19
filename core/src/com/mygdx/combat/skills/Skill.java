/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.combat.skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.ImageSprite;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.utilities.FrameCounter_Combo;
import com.mygdx.utilities.SoundObject_Sfx;
import java.util.Random;

/**
 *
 * @author looch
 */
public abstract class Skill {
    
    public enum SkillType { LIGHT, HEAVY, SPECIAL, PASSIVE, DEFENSE, NONE } 
    public enum SkillAttribute { LIFE, ENERGY, DAMAGE, SPEED, SPECIAL, LIGHT_DAMAGE, HEAVY_DAMAGE, NONE}
    
    protected SkillType type;
    protected SkillAttribute attribute;
    protected Texture skillIcon;
    protected String name;
    protected String desc;
    protected int COST = 0;   //(4/16/16) new cost system for energy, only used for specials
    protected float damageMod;
    protected boolean isCombo = false;
    protected ImageSprite esprite;
    protected FrameCounter_Combo comboFC = new FrameCounter_Combo(0.3f, 0.3f, 0.15f);
    protected float FORCE;
    protected final Random rng = new Random();
    protected final Array<ImageSprite> impactTemplates = new Array<ImageSprite>();
    protected SoundObject_Sfx impactSound;
    protected boolean active = false;       //used for passiveSkills
    protected boolean newAlert = true;//todo: old
    protected DescriptionWindow descWindow;
    protected ImageSprite skillSprite;
    
    //comboChain
    protected SkillType [] comboChain = {};
    
    
    public SkillType getType() {return type;}
    public SkillAttribute getAttribute() { return attribute; }
    public int getCost() { return COST; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public FrameCounter_Combo getComboFC() { return comboFC; }
    public SkillType[] getComboChain() { return comboChain; }
    public float getDamageMod() {return damageMod;}
    public ImageSprite getSprite() { return esprite; }
    public boolean isActive() { return active; }
    public Texture getSkillIcon() { return skillIcon; }
    public DescriptionWindow getDescWindow() { return descWindow; }
    public boolean isNewAlert() { return newAlert; }
    public ImageSprite getSkillSprite() { return skillSprite; }
    
    public void setNewAlert(boolean alert) { this.newAlert = alert; }
    
    public void effect(){}
    public void comboEffect(){}
    public void removeEffect(){}
    
    //called when skill is picked up
    public abstract void activate();
    public abstract void deactivate();
    
    public void active(){
        active = true;
    }
    
    public void reset(){
        active = false;
    }
    
    
    
}
