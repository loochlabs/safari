/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.skills.Skill;
import com.mygdx.entities.ImageSprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author saynt
 */
public class SkillHud {
    
    private float width, height;
    
    private final Texture bg;
    private final ImageSprite cursor;
    private boolean cursorEnable = true;
    
    //skill slot sprites
    private final ImageSprite
            skillHud_def, skillHud_light, skillHud_heavy, skillHud_special, skillHud_passive,
            skillHud_empty0, skillHud_empty1, skillHud_empty2, skillHud_empty3, skillHud_empty4;
    private ImageSprite 
            skillSprite0, skillSprite1, skillSprite2, skillSprite3, skillSprite4;
             
    private float slotWidth, slotHeight;
    //skill icons
    
    //skill descriptions
    private DescriptionWindow lightDesc, heavyDesc, specialDesc, defDesc, passiveDesc;
    
    //skill key bindings
    //key bindings
    private BitmapFont uifont;
    private String KEY_ACT0, KEY_ACT1, KEY_ACT2, KEY_ACT3, KEY_ACT4, NEW_ALERT_STR;
    private Array<String> keyStrings = new Array<String>();
    private boolean padEnabled = false;
    private Texture PAD_A, PAD_B, PAD_X, PAD_LB;//, PAD_Y;
    
    
    public SkillHud(){
        
        width = MainGame.WIDTH;
        height = MainGame.HEIGHT;
        
        bg = MainGame.am.get(ResourceManager.HUD_SKILL_OVERLAY);
        
        cursor = new ImageSprite("skill-overlay-cursor", false);
        cursor.sprite.setBounds(width/2 - 310f*RATIO, height/2 - 310f*RATIO, 620f*RATIO, 620f*RATIO);
        cursor.sprite.setOrigin(310f*RATIO, 310f*RATIO);
        
        
        slotWidth = 130f*RATIO; 
        slotHeight = 130f*RATIO;
        
        
        skillHud_light = new ImageSprite("light-rot", false);
        skillHud_light.sprite.setBounds(width*0.42f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty0 = new ImageSprite("skill-empty", true);
        skillHud_empty0.sprite.setBounds(width*0.42f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        
        
        skillHud_heavy = new ImageSprite("heavy-rot", false);
        skillHud_heavy.sprite.setBounds(width*0.58f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty1 = new ImageSprite("skill-empty", true);
        skillHud_empty1.sprite.setBounds(width*0.58f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        
        skillHud_special = new ImageSprite("special-rot", false);
        skillHud_special.sprite.setBounds(width*0.65f - slotWidth/2, height*0.6f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty2 = new ImageSprite("skill-empty", true);
        skillHud_empty2.sprite.setBounds(width*0.65f - slotWidth/2, height*0.6f - slotHeight/2, slotWidth, slotHeight);
        
        
        skillHud_passive = new ImageSprite("passive-rotSlow", true);
        skillHud_passive.sprite.setBounds(width*0.5f - slotWidth/2, height*0.78f - slotHeight/2, slotWidth, slotHeight);
        skillHud_passive.setComplete(true);
        skillHud_empty3 = new ImageSprite("skill-empty", true);
        skillHud_empty3.sprite.setBounds(width*0.50f - slotWidth/2, height*0.78f - slotHeight/2, slotWidth, slotHeight);
        
        skillHud_def = new ImageSprite("skill-empty", true);
        skillHud_def.sprite.setBounds(width*0.35f - slotWidth/2, height*0.60f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty4 = new ImageSprite("skill-empty", true);
        skillHud_empty4.sprite.setBounds(width*0.35f - slotWidth/2, height*0.60f - slotHeight/2, slotWidth, slotHeight);
        
        
        //key bindings
        if(GameInputProcessor.controller)   padEnabled = true;
        
        if(!padEnabled){
            uifont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
            uifont.setScale(0.75f * RATIO);
            keyStrings.add(Input.Keys.toString(GameInputProcessor.KEY_ACTION_0));
            keyStrings.add(Input.Keys.toString(GameInputProcessor.KEY_ACTION_1));
            keyStrings.add(Input.Keys.toString(GameInputProcessor.KEY_ACTION_2));
            keyStrings.add(Input.Keys.toString(GameInputProcessor.KEY_ACTION_4));
            
        }else{
            PAD_A = MainGame.am.get(ResourceManager.GUI_PAD_A);
            PAD_B = MainGame.am.get(ResourceManager.GUI_PAD_B);
            PAD_X = MainGame.am.get(ResourceManager.GUI_PAD_X);
            PAD_LB = MainGame.am.get(ResourceManager.GUI_PAD_RB);
        }
    }
    
    
    public void update(){}
    
    public void render(SpriteBatch sb){
        
        if(bg != null){
            sb.draw(bg, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
            
        }
        
        
        if(cursorEnable){
            cursor.render(sb);
        }
        
        
        //*******************************
        //      SKILL ICONS
        
        //todo: dirty workaround (def skill is skill[4] in PlayerEntity)
        
        for(int i = 0; i < GameScreen.player.getSkillSet().length; i++){
            
            Skill s = GameScreen.player.getSkillSet()[i];
            if(s == null) continue;
            
            switch(i){
                
                case 0:
                    //LIGHT
                    sb.draw(s.getSkillIcon(), 
                            skillHud_light.sprite.getX(), skillHud_light.sprite.getY(), 
                            skillHud_light.sprite.getWidth(), skillHud_light.sprite.getHeight());
                    
                    break;
                case 1:
                    //HEAVY
                    sb.draw(s.getSkillIcon(), 
                            skillHud_heavy.sprite.getX(), skillHud_heavy.sprite.getY(), 
                            skillHud_heavy.sprite.getWidth(), skillHud_heavy.sprite.getHeight());
                    
                    break;
                case 2:
                    //SPECIAL
                    sb.draw(s.getSkillIcon(), 
                            skillHud_special.sprite.getX(), skillHud_special.sprite.getY(), 
                            skillHud_special.sprite.getWidth(), skillHud_special.sprite.getHeight());
                    
                    break;
                case 3:
                    //PASSIVE
                    sb.draw(s.getSkillIcon(), 
                            skillHud_passive.sprite.getX(), skillHud_passive.sprite.getY(), 
                            skillHud_passive.sprite.getWidth(), skillHud_passive.sprite.getHeight());
                    
                    break;
                case 4:
                    //DEFENSE
                    
                    sb.draw(s.getSkillIcon(), 
                            skillHud_def.sprite.getX(), skillHud_def.sprite.getY(), 
                            skillHud_def.sprite.getWidth(), skillHud_def.sprite.getHeight());
                    
                    break;
                    
                default:
                    break;
            }
        }
        
        
        skillSprite0 = GameScreen.player.getSkillSet()[0] == null ? skillHud_empty0 : skillHud_light;
        skillSprite1 = GameScreen.player.getSkillSet()[1] == null ? skillHud_empty1 : skillHud_heavy;
        skillSprite2 = GameScreen.player.getSkillSet()[2] == null ? skillHud_empty2 : skillHud_special;
        skillSprite3 = GameScreen.player.getSkillSet()[3] == null ? skillHud_empty3 : skillHud_passive;
        skillSprite4 = GameScreen.player.getSkillSet()[4] == null ? skillHud_empty4 : skillHud_def;
        
        skillSprite0.render(sb);
        skillSprite1.render(sb);
        skillSprite2.render(sb);
        skillSprite3.render(sb);
        skillSprite4.render(sb);
        
        if (!padEnabled) {
            if (skillSprite0.equals(skillHud_light)) {
                    uifont.draw(sb,
                            keyStrings.get(0), 
                            width*0.42f - uifont.getBounds(keyStrings.get(0)).width/2, 
                            height*0.25f + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
            if(skillSprite1.equals(skillHud_heavy)){
                uifont.draw(sb,
                            keyStrings.get(1), 
                            width*0.58f - uifont.getBounds(keyStrings.get(1)).width/2, 
                            height*0.25f + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
            if(skillSprite2.equals(skillHud_special)){
                uifont.draw(sb,
                            keyStrings.get(2), 
                            width*0.65f - uifont.getBounds(keyStrings.get(2)).width/2, 
                            height*0.6f + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
            if(skillSprite4.equals(skillHud_def)){
                uifont.draw(sb,
                            keyStrings.get(3), 
                            width*0.35f - uifont.getBounds(keyStrings.get(3)).width/2, 
                            height*0.60f + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
        }else{
            
        }
        
        //key bindings
        /*
        if(!padEnabled){
            for (int i = 0; i < 3; i++) {
                if (GameScreen.player.getCurrentSkill(types[i]) != null) {
                    uifont.draw(sb,
                            keyStrings.get(i), x + skillOffsetX + (slotOffset*0.15f) + slotOffset * i,
                            y + uifont.getCapHeight()*1.2f);
                }
            }
            //dash
            uifont.draw(sb,
                            keyStrings.peek(), x + skillOffsetX + (slotOffset*0.15f) + slotOffset * 4,
                            y + uifont.getCapHeight()*1.2f);
        }else{
            if(GameScreen.player.getCurrentSkill(types[0]) != null)
                sb.draw(PAD_A, x + skillOffsetX , y + 5f*RATIO, 28f*RATIO, 28f*RATIO);
            
            if(GameScreen.player.getCurrentSkill(types[1]) != null)
                sb.draw(PAD_B, x + skillOffsetX + slotOffset, y + 5f*RATIO, 28f*RATIO, 28f*RATIO);
            
            if(GameScreen.player.getCurrentSkill(types[2]) != null)
                sb.draw(PAD_X, x + skillOffsetX + slotOffset*2, y + 5f*RATIO, 28f*RATIO, 28f*RATIO);
            
            sb.draw(PAD_LB, x + skillOffsetX + slotOffset*4, y + 5f*RATIO, 38f*RATIO, 28f*RATIO);
        }*/
        
        
        
        //descriptions
        if(lightDesc != null){
            lightDesc.render(sb, 125f*RATIO, 120f*RATIO);
        }
        if (heavyDesc != null) {
            heavyDesc.render(sb, 900f * RATIO, 120f * RATIO);
        }
        if(specialDesc != null){
            specialDesc.render(sb, 975f*RATIO, 400f*RATIO);
        }
        if(defDesc != null){
            defDesc.render(sb, 100f*RATIO, 400f*RATIO);
        }
        if(passiveDesc != null){
            passiveDesc.render(sb, 300f*RATIO, 600f*RATIO);
        }
        
        
        
    }
    
    public void enable(){
        setDescWindows();
    }
    
    
    //Rotate cursor to line up with skill coresponding to SkillPad skill type
    //Disable cursor if skillPad is empty
    public void rotateCursor(int index){
        
        if(index == -1) {
            cursorEnable = false;
            return;
        }
        
        cursorEnable = true;
        float angle = 0;
        switch(index){
            case 0:
                angle = 330;
                break;
            case 1:
                angle = 30;
                break;
            case 2:
                angle = 110;
                break;
            case 3:
                angle = 180;
                break;
            case 4:
                angle = 250;
                break;
            default:
                break;
        }
        
        cursor.sprite.setRotation(angle);
    }
    
    private float desc_w = 100f*RATIO, desc_h = 100f*RATIO;
    
    //Description windows
    public void setDescWindows() throws IndexOutOfBoundsException{
        Skill[] skills = GameScreen.player.getSkillSet();
        
        if(skills[0] != null){
            lightDesc = skills[0].getDescWindow();
            lightDesc.setSize(desc_w, desc_h);
        }else{
            lightDesc = null;
        }
        
        if(skills[1] != null){
            heavyDesc = skills[1].getDescWindow();
            heavyDesc.setSize(desc_w, desc_h);
        }else{
            heavyDesc = null;
        }
        
        if(skills[2] != null){
            specialDesc = skills[2].getDescWindow();
            specialDesc.setSize(desc_w, desc_h);
        }else{
            specialDesc = null;
        }
        
        if(skills[3] != null){
            passiveDesc = skills[3].getDescWindow();
            passiveDesc.setSize(desc_w, desc_h);
        }else{
            passiveDesc = null;
        }
        
        if(skills[4] != null){
            defDesc = skills[4].getDescWindow();
            defDesc.setSize(desc_w, desc_h);
        }else{
            defDesc = null;
        }
        
    }
    
    
}
