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
    
    //skill slot sprites
    private final ImageSprite
            skillHud_def, skillHud_light, skillHud_heavy, skillHud_special, skillHud_passive,
            skillHud_empty0, skillHud_empty1, skillHud_empty2, skillHud_empty3, skillHud_empty4;
    private ImageSprite 
            skillSprite0, skillSprite1, skillSprite2, skillSprite3, skillSprite4;
             
    private float slotWidth, slotHeight;
    
    
    //layout values
    private final float desc_x = 50f*RATIO;
    private float icon_x = 525f*RATIO;
    private float 
            light_y = 620f*RATIO, 
            heavy_y = 470f*RATIO, 
            special_y = 320f*RATIO, 
            defense_y = 170f*RATIO, 
            passive_y = 20f*RATIO;
    
    //skill cursor
    private final Texture skillCursor;
    private boolean cursorEnable = true;
    private float 
            cursor_w = 320f*RATIO, 
            cursor_h = 140f*RATIO, 
            cursor_x = 275f*RATIO,
            cursor_y;
    
    //transition in/out
    private float trans_x = 0, trans_x_max ;
    private final float trans_rate = 50f*RATIO;
    
    //skill descriptions
    private DescriptionWindow lightDesc, heavyDesc, specialDesc, defDesc, passiveDesc;
    
    //skill key bindings
    //key bindings
    private BitmapFont uifont;
    private Array<String> keyStrings = new Array<String>();
    private boolean padEnabled = false;
    private Texture PAD_A, PAD_B, PAD_X, PAD_LB;
    
    
    public SkillHud(){
        
        width = MainGame.WIDTH/2;
        height = MainGame.HEIGHT;
        
        bg = MainGame.am.get(ResourceManager.HUD_SKILL_OVERLAY);
        
        /*
        cursor = new ImageSprite("skill-overlay-cursor", false);
        cursor.sprite.setBounds(width/2 - 310f*RATIO, height/2 - 310f*RATIO, 620f*RATIO, 620f*RATIO);
        cursor.sprite.setOrigin(310f*RATIO, 310f*RATIO);
        */
        
        skillCursor = MainGame.am.get(ResourceManager.SKILL_OVERLAY_CURSOR);
        
        slotWidth = 130f*RATIO; 
        slotHeight = 130f*RATIO;
        
        
        /*************************
                DRAFT TWO
        ***********************/
        
        
        
        
        skillHud_light = new ImageSprite("hud-skill-light", false);
        skillHud_light.sprite.setBounds(icon_x  - slotWidth/2, light_y, slotWidth, slotHeight);
        skillHud_empty0 = new ImageSprite("skill-empty", false);
        skillHud_empty0.sprite.setBounds(icon_x  - slotWidth/2, light_y, slotWidth, slotHeight);
        
        
        skillHud_heavy = new ImageSprite("hud-skill-heavy", false);
        skillHud_heavy.sprite.setBounds(icon_x - slotWidth/2, heavy_y, slotWidth, slotHeight);
        skillHud_empty1 = new ImageSprite("skill-empty", false);
        skillHud_empty1.sprite.setBounds(icon_x - slotWidth/2, heavy_y, slotWidth, slotHeight);
        
        skillHud_special = new ImageSprite("hud-skill-special", false);
        skillHud_special.sprite.setBounds(icon_x - slotWidth/2, special_y, slotWidth, slotHeight);
        skillHud_empty2 = new ImageSprite("skill-empty", false);
        skillHud_empty2.sprite.setBounds(icon_x - slotWidth/2, special_y, slotWidth, slotHeight);
        
        
        skillHud_def = new ImageSprite("hud-skill-defense", false);
        skillHud_def.sprite.setBounds(icon_x - slotWidth/2, defense_y, slotWidth, slotHeight);
        skillHud_empty4 = new ImageSprite("skill-empty", false);
        skillHud_empty4.sprite.setBounds(icon_x - slotWidth/2, defense_y, slotWidth, slotHeight);
        
        skillHud_passive = new ImageSprite("hud-skill-passive", false);
        skillHud_passive.sprite.setBounds(icon_x - slotWidth/2, passive_y, slotWidth, slotHeight);
        skillHud_passive.setComplete(true);
        skillHud_empty3 = new ImageSprite("skill-empty", false);
        skillHud_empty3.sprite.setBounds(icon_x - slotWidth/2, passive_y, slotWidth, slotHeight);
        
        
        
        /************************************
                    DRAFT ONE
        
        skillHud_light = new ImageSprite("hud-skill-light", false);
        skillHud_light.sprite.setBounds(width*0.42f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty0 = new ImageSprite("skill-empty", false);
        skillHud_empty0.sprite.setBounds(width*0.42f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        
        
        skillHud_heavy = new ImageSprite("hud-skill-heavy", false);
        skillHud_heavy.sprite.setBounds(width*0.58f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty1 = new ImageSprite("skill-empty", false);
        skillHud_empty1.sprite.setBounds(width*0.58f - slotWidth/2, height*0.25f - slotHeight/2, slotWidth, slotHeight);
        
        skillHud_special = new ImageSprite("hud-skill-special", false);
        skillHud_special.sprite.setBounds(width*0.65f - slotWidth/2, height*0.6f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty2 = new ImageSprite("skill-empty", false);
        skillHud_empty2.sprite.setBounds(width*0.65f - slotWidth/2, height*0.6f - slotHeight/2, slotWidth, slotHeight);
        
        
        skillHud_passive = new ImageSprite("hud-skill-passive", false);
        skillHud_passive.sprite.setBounds(width*0.5f - slotWidth/2, height*0.78f - slotHeight/2, slotWidth, slotHeight);
        skillHud_passive.setComplete(true);
        skillHud_empty3 = new ImageSprite("skill-empty", false);
        skillHud_empty3.sprite.setBounds(width*0.50f - slotWidth/2, height*0.78f - slotHeight/2, slotWidth, slotHeight);
        
        skillHud_def = new ImageSprite("hud-skill-defense", false);
        skillHud_def.sprite.setBounds(width*0.35f - slotWidth/2, height*0.60f - slotHeight/2, slotWidth, slotHeight);
        skillHud_empty4 = new ImageSprite("skill-empty", false);
        skillHud_empty4.sprite.setBounds(width*0.35f - slotWidth/2, height*0.60f - slotHeight/2, slotWidth, slotHeight);
        
        *******************************/
        
        
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
            PAD_LB = MainGame.am.get(ResourceManager.GUI_PAD_LB);
        }
    }
    
    
    public void update(){
        //update transition in and out
        if(trans_x < 0){
            trans_x += trans_rate;
        }
        
        trans_x = trans_x < -trans_rate ? trans_x + trans_rate : 0;
    }
    
    public void render(SpriteBatch sb){
        
        if(bg != null){
            sb.draw(bg, trans_x + 0, 0, width, height);
        }
        
        
        
        if(cursorEnable){
            sb.draw(skillCursor, trans_x + cursor_x, cursor_y, cursor_w, cursor_h);
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
                            trans_x +  skillHud_light.sprite.getX(), skillHud_light.sprite.getY(), 
                            skillHud_light.sprite.getWidth(), skillHud_light.sprite.getHeight());
                    
                    break;
                case 1:
                    //HEAVY
                    sb.draw(s.getSkillIcon(), 
                            trans_x +  skillHud_heavy.sprite.getX(), skillHud_heavy.sprite.getY(), 
                            skillHud_heavy.sprite.getWidth(), skillHud_heavy.sprite.getHeight());
                    
                    break;
                case 2:
                    //SPECIAL
                    sb.draw(s.getSkillIcon(), 
                            trans_x + skillHud_special.sprite.getX(), skillHud_special.sprite.getY(), 
                            skillHud_special.sprite.getWidth(), skillHud_special.sprite.getHeight());
                    
                    break;
                case 3:
                    //PASSIVE
                    sb.draw(s.getSkillIcon(), 
                            trans_x + skillHud_passive.sprite.getX(), skillHud_passive.sprite.getY(), 
                            skillHud_passive.sprite.getWidth(), skillHud_passive.sprite.getHeight());
                    
                    break;
                case 4:
                    //DEFENSE
                    
                    sb.draw(s.getSkillIcon(), 
                            trans_x + skillHud_def.sprite.getX(), skillHud_def.sprite.getY(), 
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
        
        
        skillSprite0.drawOffset(sb, trans_x, 0);
        skillSprite1.drawOffset(sb, trans_x, 0);
        skillSprite2.drawOffset(sb, trans_x, 0);
        skillSprite3.drawOffset(sb, trans_x, 0);
        skillSprite4.drawOffset(sb, trans_x, 0);
        /*
        //STATIC RENDER
        
        skillSprite0.render(sb);
        skillSprite1.render(sb);
        skillSprite2.render(sb);
        skillSprite3.render(sb);
        skillSprite4.render(sb);
        */
        
        
        
        //KEY BINDINGS
        
        if (!padEnabled) {
            if (skillSprite0.equals(skillHud_light)) {
                    uifont.draw(sb,
                            keyStrings.get(0), 
                            trans_x + icon_x + slotWidth/2 , 
                            light_y + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
            if(skillSprite1.equals(skillHud_heavy)){
                uifont.draw(sb,
                            keyStrings.get(1), 
                            trans_x + icon_x + slotWidth/2 , 
                            heavy_y + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
            if(skillSprite2.equals(skillHud_special)){
                uifont.draw(sb,
                            keyStrings.get(2), 
                            trans_x + icon_x + slotWidth/2 , 
                            special_y + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
            if(skillSprite4.equals(skillHud_def)){
                uifont.draw(sb,
                            keyStrings.get(3), 
                            trans_x + icon_x + slotWidth/2 , 
                            defense_y + slotHeight/2 + uifont.getCapHeight()/2);
            }
            
        }else{
            /************************
                GAME PAD BUTTONS
            *************************/
            float iw = 40f*RATIO;
            if (skillSprite0.equals(skillHud_light)) {
                    sb.draw(PAD_A, 
                            trans_x + icon_x - iw/2 + slotWidth/2, 
                            light_y + slotHeight/2 - iw/2,
                            iw, iw);
            }
            
            if(skillSprite1.equals(skillHud_heavy)){
                sb.draw(PAD_B, 
                            trans_x + icon_x - iw/2 + slotWidth/2, 
                            heavy_y + slotHeight/2 - iw/2,
                            iw, iw);
            }
            
            if(skillSprite2.equals(skillHud_special)){
                sb.draw(PAD_X, 
                            trans_x + icon_x - iw/2 + slotWidth/2, 
                            special_y + slotHeight/2 - iw/2,
                            iw, iw);
            }
            
            if(skillSprite4.equals(skillHud_def)){
                sb.draw(PAD_LB, 
                            trans_x + icon_x - iw/2 + slotWidth/2, 
                            defense_y + slotHeight/2 - iw/2,
                            iw, iw*0.65f);
            }
        }
        
        
        
        /*
        DRAFT 1
        
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
            /************************
                GAME PAD BUTTONS
            *************************
            float iw = 40f*RATIO;
            if (skillSprite0.equals(skillHud_light)) {
                    sb.draw(PAD_A, 
                            width*0.42f - iw/2 , 
                            height*0.25f + slotHeight/2 - iw/2,
                            iw, iw);
            }
            
            if(skillSprite1.equals(skillHud_heavy)){
                sb.draw(PAD_B, 
                            width*0.58f - iw/2, 
                            height*0.25f + slotHeight/2 - iw/2,
                            iw, iw);
            }
            
            if(skillSprite2.equals(skillHud_special)){
                sb.draw(PAD_X, 
                            width*0.65f - iw/2, 
                            height*0.6f + slotHeight/2 - iw/2,
                            iw, iw);
            }
            
            if(skillSprite4.equals(skillHud_def)){
                sb.draw(PAD_LB, 
                            width*0.35f - iw/2, 
                            height*0.60f + slotHeight/2 - iw/2,
                            iw, iw*0.65f);
            }
        }
        
        */
        
        //descriptions
        
        if(lightDesc != null){
            lightDesc.render(sb, trans_x + desc_x, light_y);
        }
        if (heavyDesc != null) {
            heavyDesc.render(sb, trans_x + desc_x, heavy_y);
        }
        if(specialDesc != null){
            specialDesc.render(sb,trans_x +  desc_x, special_y);
        }
        if(defDesc != null){
            defDesc.render(sb, trans_x + desc_x, defense_y);
        }
        if(passiveDesc != null){
            passiveDesc.render(sb, trans_x + desc_x, passive_y);
        }
        
        /*
            DRAFT ONE
       
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
        
         */
        
    }
    
    public void enable(int index){
        setDescWindows();
        
        if (index == -1) {
            cursorEnable = false;
        } else {
            cursorEnable = true;
            switch (index) {
                case 0:
                    cursor_y = light_y - 5f*RATIO;
                    break;
                case 1:
                    cursor_y = heavy_y - 5f*RATIO;
                    break;
                case 2:
                    cursor_y = special_y - 5f*RATIO;
                    break;
                case 3:
                    cursor_y = passive_y - 5f*RATIO;
                    break;
                case 4:
                    cursor_y = defense_y - 5f*RATIO;
                    break;
                default:
                    break;
            }
        }
        
        //transition 
        trans_x = -width;
    }
    
    
    //Rotate cursor to line up with skill coresponding to SkillPad skill type
    //Disable cursor if skillPad is empty
    /*
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
    */

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
