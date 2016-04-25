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
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;

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
    
    
    //key bindings
    private BitmapFont uifont;
    private String KEY_SKILLS;  //, KEY_ACT1, KEY_ACT2, KEY_ACT4;
    private boolean padEnabled = false;
    
    //dm ui
    private InventoryUi invui;
    
    private final FrameManager fm = new FrameManager();
    
    
    public BarHud(float x, float y, float width, float height){
        super(x,y,width,height);
        
        logoHud = new LogoHud(x,y, 150f*RATIO, 110f*RATIO);
        
        //hp bar hud
        hpBarHud = new HpBarHud(x + logoHud.getWidth(), y, 1000f*RATIO, 30f*RATIO);
        
        
        invui = new InventoryUi(x, y + logoHud.getHeight() + 50f*RATIO);
        
        uifont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        uifont.setScale(0.525f * RATIO);
        
        //key bindings
        if(GameInputProcessor.controller)   padEnabled = true;
        
        if(!padEnabled){
            KEY_SKILLS = Input.Keys.toString(GameInputProcessor.KEY_SKILL_SELECT);
        }
       
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
        
        
        //soul hud
        logoHud.render(sb);
        
        //hp bar
        hpBarHud.render(sb);
        
        //inventory
        invui.render(sb);
        
        super.render(sb);
        
    }
    
    
    //todo: old
    public void addItem(Pickup p){
        //invenui.addItem(p);
    }
    
    public void removeItem(Pickup p){
        //invenui.removeItem(p);
    }
    
    //TODO: remove
    public void addDescAlert(Skill s){}
    
    
    
    /***********************************
    
        CONTAINS PLAYER NAME LOGO
        -also renders "[Q] skills" message
    
    ************************************/
    private class LogoHud extends OverlayComponent {

        private Texture logo, PAD_RB;
        private final float PADDING = 11f*RATIO;
        
        
        public LogoHud(float x, float y, float width, float height) {
            super(x, y, width, height);

            logo = MainGame.am.get(ResourceManager.SOUL_LOGO_POE);

            if(GameInputProcessor.controller) {
                PAD_RB = MainGame.am.get(ResourceManager.GUI_PAD_RB);
            }

        }

        @Override
        public void update() {
            //soulHudMeter.update();
        }
        
        @Override
        public void render(SpriteBatch sb){
            
            sb.draw(logo, x, y, width, height);
            
            if(GameInputProcessor.controller ){
                if(PAD_RB != null){
                    sb.draw(PAD_RB,  x + PADDING, y + height  + PADDING, 45f*RATIO, 30f*RATIO);
                }
                uifont.draw(sb, " - SKILLS", x + PADDING + 45f*RATIO, y + height + uifont.getCapHeight() + PADDING);
            }else{
                uifont.draw(sb, KEY_SKILLS + " - SKILLS", x + PADDING, y + height + uifont.getCapHeight() + PADDING);
            }
            
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
            
            if(GameScreen.player.getSkillSet()[2] != null){
                energyHud.render(sb);
            }

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
