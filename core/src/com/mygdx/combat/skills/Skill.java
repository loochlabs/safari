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
import com.mygdx.utilities.SoundObject_Sfx;
import java.util.Random;

/**
 *
 * @author looch
 */
public abstract class Skill {
    
    public enum SkillType { LIGHT, HEAVY, SPECIAL, PASSIVE, ITEM } 
    public enum SkillAttribute { LIFE, ENERGY, DAMAGE, SPEED, SPECIAL, LIGHT_DAMAGE, HEAVY_DAMAGE}
    
    protected SkillType type;
    protected SkillAttribute attribute;
    protected boolean dashSkill = false;
    protected Texture skillIcon;
    protected String name;
    protected String desc;
    protected float COST;
    protected float damageMod, comboBonus = 1.5f;
    protected ImageSprite esprite;
    protected float prepTime, attTime, recovTime;
    protected float FORCE;
    protected final Random rng = new Random();
    protected final Array<ImageSprite> impactTemplates = new Array<ImageSprite>();
    protected SoundObject_Sfx impactSound;
    protected boolean active = false;       //used for passiveSkills
    protected boolean newAlert = true;
    protected DescriptionWindow descWindow;
    protected ImageSprite skillSprite;
    
    public SkillType getType() {return type;}
    public SkillAttribute getAttribute() { return attribute; }
    public boolean isDashSkill() { return dashSkill; }
    public float getCost() { return COST; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public float getPrepTime() { return prepTime; }
    public float getAttTime() { return attTime; }
    public float getRecovTime() { return recovTime; }
    public float getDamageMod() {return damageMod;}
    public ImageSprite getSprite() { return esprite; }
    public boolean isActive() { return active; }
    public Texture getSkillIcon() { return skillIcon; }
    public DescriptionWindow getDescWindow() { return descWindow; }
    public boolean isNewAlert() { return newAlert; }
    public ImageSprite getSkillSprite() { return skillSprite; }
    
    public void setNewAlert(boolean alert) { this.newAlert = alert; }
    
    public void effect(){}       //used for passive skills
    public void effect(boolean isCombo, Skill prevSkill){}
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
