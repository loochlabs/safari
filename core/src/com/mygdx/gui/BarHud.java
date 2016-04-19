/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.skills.Skill;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import java.util.Stack;

/**
 *
 * @author looch
 */
@SuppressWarnings("FieldMayBeFinal")
public class BarHud extends OverlayComponent{

    //SOUL HUD
    private LogoHud logoHud;
    
    /********************
        HP BAR HUD
    ***********************/
    private final HpBarHud hpBarHud;
    
    
    //private SkillType [] types = { LIGHT, HEAVY, SPECIAL, PASSIVE, DEFENSE };
    //private final Texture redTexture;
   // private final float slotWidth, slotHeight, skillOffsetX, skillOffsetY, slotOffset;
    
    //skill hud (8/8/15)
    //private final ImageSprite 
            //skillHud_light, skillHud_heavy, skillHud_special, skillHud_passive, 
            //skillHud_empty0, skillHud_empty1, skillHud_empty2, skillHud_empty3, skillHud_empty4;
    //private final ImageSprite skillHud_def;
    //private ImageSprite skillSprite0, skillSprite1, skillSprite2, skillSprite3, skillSprite4;
    //private Array<ImageSprite> sprites = new Array<ImageSprite>();
    
    
    //key bindings
    private BitmapFont uifont;
    private String KEY_SKILLS;  //, KEY_ACT1, KEY_ACT2, KEY_ACT4;
    //private Array<String> keyStrings = new Array<String>();
    private boolean padEnabled = false;
    //private Texture PAD_A, PAD_B, PAD_X, PAD_LB;//, PAD_Y;
    
    //dm ui
    private InventoryUi invui;
    
    //desc window
    /*
    private DescriptionWindow descWindow;
    private final float desc_x, desc_y;
    private final float desc_time = 10f;
    private final FrameCounter descFC = new FrameCounter(desc_time);
    */
    private final FrameManager fm = new FrameManager();
    
    
    
    
    
    public BarHud(float x, float y, float width, float height){
        super(x,y,width,height);
        
        //soul hud
        //soulHud = new SoulHud(
                //x + skillOffsetX - slotOffset*1.25f, y+0.05f,
                //slotWidth,slotWidth);
        logoHud = new LogoHud(x,y, 150f*RATIO, 110f*RATIO);
        
        //hp bar hud
        hpBarHud = new HpBarHud(x + logoHud.getWidth(), y, 1000f*RATIO, 30f*RATIO);
        
        /***************
            SKILLS
        ****************/
        
        //redTexture = MainGame.am.get(ResourceManager.SKILL_RED);
        
        /*
        skillOffsetX = 10f*RATIO;
        skillOffsetY = logoHud.getY() - logoHud.getHeight();
        slotWidth = width * 0.25f; 
        slotHeight = slotWidth;
        slotOffset = slotWidth;
        */
        
        /*
        skillHud_def = new ImageSprite("skill-empty", true);
        skillHud_def.sprite.setBounds(x, skillOffsetY, slotWidth, slotHeight);
        sprites.add(skillHud_def);
        
        skillHud_light = new ImageSprite("light-rot", false);
        skillHud_light.sprite.setBounds(x, skillOffsetY - slotOffset, slotWidth, slotHeight);
        sprites.add(skillHud_light);
        
        skillHud_heavy = new ImageSprite("heavy-rot", false);
        skillHud_heavy.sprite.setBounds(x, skillOffsetY - slotOffset*2, slotWidth, slotHeight);
        sprites.add(skillHud_heavy);
        
        skillHud_special = new ImageSprite("special-rot", false);
        skillHud_special.sprite.setBounds(x, skillOffsetY - slotOffset*3, slotWidth, slotHeight);
        sprites.add(skillHud_special);
        
        skillHud_passive = new ImageSprite("passive-rotSlow", true);
        skillHud_passive.sprite.setBounds(x, skillOffsetY - slotOffset*4, slotWidth, slotHeight);
        sprites.add(skillHud_passive);
        skillHud_passive.setComplete(true);
        
        skillHud_empty0 = new ImageSprite("skill-empty", true);
        skillHud_empty0.sprite.setBounds(x, skillOffsetY, slotWidth, slotHeight);
        sprites.add(skillHud_empty0);
        skillHud_empty1 = new ImageSprite("skill-empty", true);
        skillHud_empty1.sprite.setBounds(x, skillOffsetY - slotOffset, slotWidth, slotHeight);
        sprites.add(skillHud_empty1);
        skillHud_empty2 = new ImageSprite("skill-empty", true);
        skillHud_empty2.sprite.setBounds(x, skillOffsetY - slotOffset*2, slotWidth, slotHeight);
        sprites.add(skillHud_empty2);
        skillHud_empty3 = new ImageSprite("skill-empty", true);
        skillHud_empty3.sprite.setBounds(x, skillOffsetY - slotOffset*3, slotWidth, slotHeight);
        sprites.add(skillHud_empty3);
        skillHud_empty4 = new ImageSprite("skill-empty", true);
        skillHud_empty4.sprite.setBounds(x, skillOffsetY - slotOffset*4, slotWidth, slotHeight);
        sprites.add(skillHud_empty4);
        
        */
        
        invui = new InventoryUi(x, y + logoHud.getHeight() + 50f*RATIO);
        //dmui = new InventoryUi(x + skillOffsetX + slotOffset*5, y, slotWidth * 1.5f, slotWidth);
        
        
        //key bindings
        if(GameInputProcessor.controller)   padEnabled = true;
        
        if(!padEnabled){
            uifont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
            uifont.setScale(0.525f * RATIO);
            KEY_SKILLS = Input.Keys.toString(GameInputProcessor.KEY_SKILL_SELECT);
        }else{/*
            PAD_A = MainGame.am.get(ResourceManager.GUI_PAD_A);
            PAD_B = MainGame.am.get(ResourceManager.GUI_PAD_B);
            PAD_X = MainGame.am.get(ResourceManager.GUI_PAD_X);
            PAD_LB = MainGame.am.get(ResourceManager.GUI_PAD_RB);   //todo: change to LB
                */
        }
       
        
        //desc window
        //desc_x = x - 400f*RATIO;
        //desc_y = y + 150f*RATIO;
        
        
        
        
    }
    
    
    @Override
    public void update(){
        fm.update();
        invui.update();
        logoHud.update();
        hpBarHud.update();
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        //*******************************
        //      SKILL ICONS
        
        //todo: dirty workaround (def skill is skill[4] in PlayerEntity)
        /*
        for(int i = 0; i < types.length-1; i++){
            Skill s = GameScreen.player.getCurrentSkill(types[i]);
            if (s != null) {
                sb.draw(s.getSkillIcon(), x + skillOffsetX, skillOffsetY - (slotOffset * (i+1)) + slotWidth*0.1f , slotWidth*0.8f, slotHeight*0.8f);

            }
        }
        
        Skill s = GameScreen.player.getCurrentSkill(types[4]);
        if (s != null) {
            sb.draw(s.getSkillIcon(), x + skillOffsetX, skillOffsetY + slotWidth * 0.1f, slotWidth * 0.8f, slotHeight * 0.8f);
        }
        //*******************************
        
        
        skillSprite0 = GameScreen.player.getCurrentSkill(types[4]) == null ? skillHud_empty0 : skillHud_def;
        skillSprite1 = GameScreen.player.getCurrentSkill(types[0]) == null ? skillHud_empty1 : skillHud_light;
        skillSprite2 = GameScreen.player.getCurrentSkill(types[1]) == null ? skillHud_empty2 : skillHud_heavy;
        skillSprite3 = GameScreen.player.getCurrentSkill(types[2]) == null ? skillHud_empty3 : skillHud_special;
        skillSprite4 = GameScreen.player.getCurrentSkill(types[3]) == null ? skillHud_empty4 : skillHud_passive;
        
        skillSprite0.render(sb);
        skillSprite1.render(sb);
        skillSprite2.render(sb);
        skillSprite3.render(sb);
        skillSprite4.render(sb);
        
        skillHud_passive.setComplete(GameScreen.player.getCurrentSkill(PASSIVE) == null);
        */
        
        //dmui.render(sb);
        
        
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
        }
        
        */
        
        //descWindow
        /* Edited: 2/10/2016
        //Check DescriptionWindow for setSize() method
        //
        //Adding enlarge shrink graphic to descWindow on spawn
        //Start large (7 seconds) 
        //Then skrink (over final 3 secs)
        */
        /*
        if(descWindow != null){
            descWindow.render(sb, desc_x - descWindow.getWidth()/2, desc_y);
               
                
            if(descFC.complete)
                descWindow = null;
        }*/
        
        //soul hud
        logoHud.render(sb);
        
        //hp bar
        hpBarHud.render(sb);
        
        //inventory
        invui.render(sb);
        
        super.render(sb);
        
    }
    /*
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
    */
    
    //public boolean availableEnergy(float cost){
        //return GameScreen.player.getEnergy() >= cost;
    //}
    
    //todo: old
    public void addItem(Pickup p){
        //invenui.addItem(p);
    }
    
    public void removeItem(Pickup p){
        //invenui.removeItem(p);
    }
    
    //TODO: remove
    public void addDescAlert(Skill s){
        //descWindow = new DescriptionWindow(s.getName(),s.getDesc(), s.getType());
        //descFC.start(fm);
    }
    
    
    
    /***********************************
    
        CONTAINS PLAYER NAME LOGO
        -also renders "[Q] skills" message
    
    ************************************/
    private class LogoHud extends OverlayComponent {

        private Texture logo;
        private final float PADDING = 11f*RATIO;
        
        public LogoHud(float x, float y, float width, float height) {
            super(x, y, width, height);

            logo = MainGame.am.get(ResourceManager.SOUL_LOGO_POE);

        }

        @Override
        public void update() {
            //soulHudMeter.update();
        }
        
        @Override
        public void render(SpriteBatch sb){
            
            sb.draw(logo, x, y, width, height);
            
            uifont.draw(sb, KEY_SKILLS + " - SKILLS", x + PADDING, y + height + uifont.getCapHeight() + PADDING);
        }
        
    }
    
    
    /**
     * **********************************************
     *          HP BAR HUD
     *
     * -Handles the HP bar 
     * -Also handles the Energy slots (empty/filled)
     *
     ***********************************************
     */
    
    private class HpBarHud extends OverlayComponent {

        private HpBarBg hpbg;
        private HpBarFg hpfg;
        
        //energy slots
        private EnergyHud energyHud;

        public HpBarHud(float x, float y, float width, float height) {
            super(x + 10f*RATIO, y + 10f*RATIO, width, height);

            //hpbg = new HpBarBg(x + width*0.05f, y + height *0.55f, width * 0.5f, height);
            //hpfg = new HpBarFg(x + width*0.1f,  y + height *0.55f, width * 0.4f, height, hpbg);
            //hpbg = new HpBarBg(x, y , width * 0.5f, height);
            //hpfg = new HpBarFg(x, y + height * 0.05f, width * 0.5f, height*0.95f, hpbg);
            hpbg = new HpBarBg(this.x, this.y, width * 0.5f, height);
            hpfg = new HpBarFg(this.x, this.y + height * 0.05f, width * 0.5f, height * 0.95f, hpbg);

            energyHud = new EnergyHud(this.x, hpbg.getY() + hpbg.getHeight() + 10f*RATIO, 55f*RATIO, 55f*RATIO);
            
        }

        @Override
        public void update() {

            hpfg.update();
            hpbg.update();
            
            energyHud.update();

        }

        @Override
        public void render(SpriteBatch sb) {
            super.render(sb);

            hpbg.render(sb);
            hpfg.render(sb);
            
            energyHud.render(sb);

        }
        
        

        private class HpBarFg extends OverlayComponent {

            private OverlayComponent bg;

            public HpBarFg(float x, float y, float width, float height, OverlayComponent bg) {
                super(x, y, width, height);

                texture = MainGame.am.get(ResourceManager.HUD1_HP_FG);

                this.bg = bg;

            }

            @Override
            public void update() {

                width = (float) (GameScreen.player.getLife())
                        / (float) GameScreen.player.getCurrentLife()
                        * bg.getWidth();

                //needs to be > 0 because negative width
                //width = width > 0 ? 0 : width;
            }

        }

        private class HpBarBg extends OverlayComponent {

            private final float widthUnit;

            public HpBarBg(float x, float y, float width, float height) {
                super(x, y, width, height);

                texture = MainGame.am.get(ResourceManager.HUD1_HP_BG);

                widthUnit = width * 0.01f;
                this.width = widthUnit * GameScreen.player.getCurrentLife();

            }

            @Override
            public void update() {

                if (width != widthUnit * GameScreen.player.getCurrentLife()) {
                    width = widthUnit * GameScreen.player.getCurrentLife();
                }
            }

        }
        
        private class EnergyHud extends OverlayComponent{
            
            //energy count = #Energy slots
            private int energyCount_max = 0;
            
            private Array<EnergySlot> energySlots = new Array<EnergySlot>();
            
            public EnergyHud(float x, float y, float width, float height){
                super(x,y,width,height);
            }
            
            @Override
            public void render(SpriteBatch sb){
                for(EnergySlot s : energySlots){
                    s.render(sb);
                }
            }

            @Override
            public void update() {
                
                energyCount_max = GameScreen.player.getCurrentEnergyMax();
                
                updateSlots();
                
            }
            
            private void updateSlots(){
                
                //Adjust number of slots if needed
                while(energySlots.size != energyCount_max){
                    
                    if(energyCount_max > energySlots.size){
                        int s = energySlots.size;
                        energySlots.add(new EnergySlot(x + width*s, y, width,height));
                    }else{
                        energySlots.pop();
                    }
                    
                }
                
                //Adjust slots' fill
                //int ecount = 0;
                //for(EnergySlot s : energySlots){
                    //if(s.filled)    ecount++;
                //}
                
                //if(ecount != GameScreen.player.getEnergy()){
                    for(int i = 0; i < energySlots.size; i++){
                        energySlots.get(i).filled = i < GameScreen.player.getEnergy();
                    }
                //}
                
                
            }
            
        }
        
        private class EnergySlot extends OverlayComponent{
            
            private final Texture fillTexture, emptyTexture;
            
            public boolean filled = true;
            
            public EnergySlot(float x, float y, float width, float height){
                super(x,y,width,height);
                
                fillTexture = MainGame.am.get(ResourceManager.HUD_ENERGY_FILL);
                emptyTexture = MainGame.am.get(ResourceManager.HUD_ENERGY_EMPTY);
            }

            @Override
            public void update() {
            }
            
            @Override
            public void render(SpriteBatch sb){
                sb.draw(filled ? fillTexture : emptyTexture, x, y, width, height);
            }
            
        }

    }
}
