/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.skills.Skill;
import com.mygdx.combat.skills.Skill.SkillType;
import static com.mygdx.combat.skills.Skill.SkillType.HEAVY;
import static com.mygdx.combat.skills.Skill.SkillType.LIGHT;
import static com.mygdx.combat.skills.Skill.SkillType.PASSIVE;
import static com.mygdx.combat.skills.Skill.SkillType.SPECIAL;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author looch
 */
@SuppressWarnings("FieldMayBeFinal")
public class SkillHud extends OverlayComponent{

    
    private SkillType [] types = { LIGHT, HEAVY, SPECIAL, PASSIVE };
    //private final Texture hudTexture, blankTexture, redTexture;
    private final Texture redTexture;
    private final float slotWidth, slotHeight, skillOffsetX, skillOffsetY, slotOffset;
    
    //skill hud (8/8/15)
    private final EntitySprite 
            skillHud_light, skillHud_heavy, skillHud_special, skillHud_passive, 
            skillHud_empty1, skillHud_empty2, skillHud_empty3, skillHud_empty4;
    private EntitySprite skillSprite1, skillSprite2, skillSprite3, skillSprite4;
    private Texture dashTexture;
    
    //new item alert
    private Texture newAlertTexture;
    private boolean newAlert = false;
    
    //key bindings
    private BitmapFont uifont;
    private String KEY_ACT1, KEY_ACT2, KEY_ACT3, KEY_ACT4, NEW_ALERT_STR;
    private Array<String> keyStrings = new Array<String>();
    private boolean padEnabled = false;
    private Texture PAD_A, PAD_B, PAD_X, PAD_LB;//, PAD_Y;
    
    //dm ui
    private InventoryUi dmui;
    
    //desc window
    private DescriptionWindow descWindow;
    private final float desc_x, desc_y;
    private final float desc_time = 10f;
    private final FrameCounter descFC = new FrameCounter(desc_time);
    private final FrameManager fm = new FrameManager();
    
    
    private SoulHud soulHud;
    
    
    public SkillHud(float x, float y, float width, float height){
        super(x,y,width,height);
        
        redTexture = MainGame.am.get(ResourceManager.SKILL_RED);
        
        skillOffsetX = -width * 0.5f;
        skillOffsetY = width * 0.0205f;
        slotWidth = width * 0.25f; 
        slotHeight = slotWidth;
        slotOffset = slotWidth;
        
        skillHud_light = new EntitySprite("light-rot", false);
        skillHud_light.sprite.setBounds(x + skillOffsetX, y, slotWidth, slotHeight);
        skillHud_heavy = new EntitySprite("heavy-rot", false);
        skillHud_heavy.sprite.setBounds(x + skillOffsetX + slotOffset, y, slotWidth, slotHeight);
        skillHud_special = new EntitySprite("special-rot", false);
        skillHud_special.sprite.setBounds(x + skillOffsetX + slotOffset*2, y, slotWidth, slotHeight);
        skillHud_passive = new EntitySprite("passive-rotSlow", true);
        skillHud_passive.sprite.setBounds(x + skillOffsetX + slotOffset*3, y, slotWidth, slotHeight);
        skillHud_passive.setComplete(true);
        
        skillHud_empty1 = new EntitySprite("skill-empty", true);
        skillHud_empty1.sprite.setBounds(x + skillOffsetX, y, slotWidth, slotHeight);
        skillHud_empty2 = new EntitySprite("skill-empty", true);
        skillHud_empty2.sprite.setBounds(x + skillOffsetX + slotOffset, y, slotWidth, slotHeight);
        skillHud_empty3 = new EntitySprite("skill-empty", true);
        skillHud_empty3.sprite.setBounds(x + skillOffsetX + slotOffset*2, y, slotWidth, slotHeight);
        skillHud_empty4 = new EntitySprite("skill-empty", true);
        skillHud_empty4.sprite.setBounds(x + skillOffsetX + slotOffset*3, y, slotWidth, slotHeight);
        
        dashTexture = MainGame.am.get(ResourceManager.GUI_DASH);
        
        dmui = new InventoryUi(x + skillOffsetX + slotOffset*5, y, slotWidth * 1.5f, slotWidth);
        
        
        //key bindings
        if(GameInputProcessor.controller)   padEnabled = true;
        
        if(!padEnabled){
            uifont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
            uifont.setScale(0.525f * RATIO);
            keyStrings.add(Keys.toString(GameInputProcessor.KEY_ACTION_1));
            keyStrings.add(Keys.toString(GameInputProcessor.KEY_ACTION_2));
            keyStrings.add(Keys.toString(GameInputProcessor.KEY_ACTION_3));
            keyStrings.add(Keys.toString(GameInputProcessor.KEY_DASH));
            
            NEW_ALERT_STR = Keys.toString(GameInputProcessor.KEY_SKILL_SELECT);
        }else{
            PAD_A = MainGame.am.get(ResourceManager.GUI_PAD_A);
            PAD_B = MainGame.am.get(ResourceManager.GUI_PAD_B);
            PAD_X = MainGame.am.get(ResourceManager.GUI_PAD_X);
            //PAD_Y = MainGame.am.get(ResourceManager.GUI_PAD_Y);
            PAD_LB = MainGame.am.get(ResourceManager.GUI_PAD_RB);
        }
       
        
        //desc window
        desc_x = x ;
        desc_y = y + 150f*RATIO;
        
        
        //soul hud
        soulHud = new SoulHud(
                x + skillOffsetX - slotOffset*1.25f, y+0.05f,
                slotWidth,slotWidth);
        
    }
    
    
    @Override
    public void update(){
        fm.update();
        dmui.update();
        soulHud.update();
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        //skill icons
        for(int i = 0; i < types.length; i++){
            Skill s = GameScreen.player.getCurrentSkill(types[i]);
            if (s != null) {
                sb.draw(s.getSkillIcon(), x + skillOffsetX + (slotOffset * i) + slotWidth*0.1f, y + skillOffsetY , slotWidth*0.8f, slotHeight*0.8f);

                if (!availableEnergy(s.getCost())) {
                    sb.draw(redTexture, x + skillOffsetX + (slotOffset * i) + slotWidth*0.1f, y + skillOffsetY , slotWidth*0.8f, slotHeight*0.8f);
                }

            }
        }
        
        skillSprite1 = GameScreen.player.getCurrentSkill(types[0]) == null ? skillHud_empty1 : skillHud_light;
        skillSprite2 = GameScreen.player.getCurrentSkill(types[1]) == null ? skillHud_empty2 : skillHud_heavy;
        skillSprite3 = GameScreen.player.getCurrentSkill(types[2]) == null ? skillHud_empty3 : skillHud_special;
        skillSprite4 = GameScreen.player.getCurrentSkill(types[3]) == null ? skillHud_empty4 : skillHud_passive;
        
        skillSprite1.render(sb);
        skillSprite2.render(sb);
        skillSprite3.render(sb);
        skillSprite4.render(sb);
        
        skillHud_passive.setComplete(GameScreen.player.getCurrentSkill(PASSIVE) == null);
        
        sb.draw(dashTexture, 
                x + skillOffsetX + (slotOffset * 4) + slotWidth*0.1f, 
                y + skillOffsetY*3f , 
                slotWidth*0.6f, slotHeight*0.5f);
        
        dmui.render(sb);
        
        
        //key bindings
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
        }
        
        
        
        //descWindow
        /* Edited: 2/10/2016
        //Check DescriptionWindow for setSize() method
        //
        //Adding enlarge shrink graphic to descWindow on spawn
        //Start large (7 seconds) 
        //Then skrink (over final 3 secs)
        */
        if(descWindow != null){
            descWindow.render(sb, desc_x - descWindow.getWidth()/2, desc_y);
               
                
            if(descFC.complete)
                descWindow = null;
        }
        
        soulHud.render(sb);
        
        super.render(sb);
        
    }
    
    public void resetSlot(int n){
        switch(n){
            case 0:
                skillHud_light.reset();
                break;
            case 1:
                skillHud_heavy.reset();
                break;
            case 2:
                skillHud_special.reset();
                break;
            default:
                break;
        }
    }
    
    public boolean availableEnergy(float cost){
        return GameScreen.player.getEnergy() >= cost;
    }
    
    //todo: old
    public void addItem(Pickup p){
        //invenui.addItem(p);
    }
    
    public void removeItem(Pickup p){
        //invenui.removeItem(p);
    }
    
    
    public void addDescAlert(Skill s){
        descWindow = new DescriptionWindow(s.getName(),s.getDesc(), s.getType());
        descFC.start(fm);
    }
    
    
    private class SoulHud extends OverlayComponent {

        //private final SoulHud_BG soulHudBg;
        private final SoulHud_FG soulHudFg;
        private final SoulHud_Meter soulHudMeter;
        
        public SoulHud(float x, float y, float width, float height) {
            super(x, y, width, height);

            //soulHudBg = new SoulHud_BG(x,y,width,height);
            soulHudFg = new SoulHud_FG(x,y,width,height);
            soulHudMeter = new SoulHud_Meter(x,y,width,height);

        }

        @Override
        public void update() {
            soulHudMeter.update();
        }
        
        @Override
        public void render(SpriteBatch sb){
            //soulHudBg.render(sb);
            soulHudMeter.render(sb);
            soulHudFg.render(sb);
        }
        
        private class SoulHud_BG extends OverlayComponent {


            public SoulHud_BG(float x, float y, float width, float height) {
                super(x, y, width, height);

                texture = MainGame.am.get(ResourceManager.HUD_SOUL_BG_POE);

            }
            
            @Override
            public void update(){}

        }
        
        private class SoulHud_FG extends OverlayComponent {

            public SoulHud_FG(float x, float y, float width, float height) {
                super(x, y, width, height);

                texture = MainGame.am.get(ResourceManager.HUD_SOUL_FG_POE);
                
            }

            @Override
            public void update() {}

        }
        
        private class SoulHud_Meter extends OverlayComponent {

            private final float heightUnit;

            public SoulHud_Meter(float x, float y, float width, float height) {
                super(x, y, width, height);

                texture = MainGame.am.get(ResourceManager.HUD_SOUL_METER_POE);

                heightUnit = height * 0.01f;
                //this.height = heightUnit * GameScreen.player.getMaxHp();
                this.height = heightUnit * GameScreen.player.getCurrentLife();

            }

            @Override
            public void update() {
                //update meter
                if (height != heightUnit * GameScreen.player.getSoulCount()) {
                    height = heightUnit * GameScreen.player.getSoulCount();
                }
            }

        }
        

    }
    
}
