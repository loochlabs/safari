/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author looch
 */
public class BarHud extends OverlayComponent{
    
    private HpBarBg hpbg;
    private HpBarFg hpfg;
    private EnergyBarFg energyfg;
    private EnergyBarBg energybg;
    
    
    
    //private final Texture hpicon, energyicon;
    //private final BitmapFont font;
    
    public BarHud(float x, float y, float width, float height){
        super(x,y,width,height);
        
        
        //hpbg = new HpBarBg(x + width*0.05f, y + height *0.55f, width * 0.5f, height);
        //hpfg = new HpBarFg(x + width*0.1f,  y + height *0.55f, width * 0.4f, height, hpbg);
        hpbg = new HpBarBg(x, y , width * 0.5f, height);
        hpfg = new HpBarFg(x, y + height * 0.05f, width * 0.5f, height*0.95f, hpbg);
        
        //energybg = new EnergyBarBg(x + width*0.60f, y + height *0.55f, width * 0.5f, height);
        //energyfg = new EnergyBarFg(x + width*0.65f, y + height *0.55f, width * 0.4f, height, energybg);
        energybg = new EnergyBarBg(x , y , width * 0.5f, height);
        energyfg = new EnergyBarFg(x , y + height*0.05f, width * 0.5f, height*0.95f, energybg);
        
        
        //hpicon = MainGame.am.get(ResourceManager.ICON_HP);
        //energyicon = MainGame.am.get(ResourceManager.ICON_ENERGY);
        
        //font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
    }

    @Override
    public void update() {
        
        hpfg.update();
        hpbg.update();
        energyfg.update();
        energybg.update();
        
    }

    @Override 
    public void render(SpriteBatch sb){
        super.render(sb);
        hpbg.render(sb);
        hpfg.render(sb);
        energybg.render(sb);
        energyfg.render(sb);
        
        
        //sb.draw(hpicon, x + width*0.05f, y + height *0.55f, width * 0.4f, width * 0.4f);
       // sb.draw(energyicon, energybg.getX() + width*0.125f, y+ height *0.55f, width * 0.25f, width * 0.5f);
        
        //font.draw(sb, "" + GameScreen.player.getSoulCount() + "", 5f*RATIO, font.getCapHeight());
    }
    
    private class EnergyBarFg extends OverlayComponent {

        private BitmapFont font = new BitmapFont();
        private OverlayComponent bg;

        public EnergyBarFg(float x, float y, float width, float height, OverlayComponent bg) {
            super(x, y, width, height);

            texture = MainGame.am.get(ResourceManager.HUD1_EXP_FG);
            font.setColor(Color.BLACK);
            this.bg = bg;

        }

        @Override
        public void update() {
            width = (float) (GameScreen.player.getEnergy())
                    / (float) GameScreen.player.getCurrentEnergy()
                    * bg.getWidth();
        }

        @Override
        public void render(SpriteBatch sb) {
            super.render(sb);

            if (MainGame.debugmode) {
                font.draw(sb, "" + (int) GameScreen.player.getEnergy()
                        + "/" + (int) GameScreen.player.getCurrentEnergy(),
                        x, y + font.getCapHeight() * 1.25f);
            }
        }

    }

    
    private class EnergyBarBg extends OverlayComponent {

        private final float widthUnit;

        public EnergyBarBg(float x, float y, float width, float height) {
            super(x, y, width, height);

            texture = MainGame.am.get(ResourceManager.HUD1_HP_BG);

            widthUnit = width * 0.01f;
            //this.height = heightUnit * GameScreen.player.getMaxEnergy();
            this.width = widthUnit * GameScreen.player.getCurrentEnergy();

        }

        @Override
        public void update() {

            //height = heightUnit * GameScreen.player.getMaxEnergy();
            width = widthUnit * GameScreen.player.getCurrentEnergy();

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

            width = (float) (GameScreen.player.getLife()
                    / GameScreen.player.getCurrentLife())
                    * bg.getWidth();

            //needs to be > 0 because negative width
            width = width > 0 ? 0 : width;

        }

    }

    private class HpBarBg extends OverlayComponent {

        private final float widthUnit;

        public HpBarBg(float x, float y, float width, float height) {
            super(x, y, width, height);

            texture = MainGame.am.get(ResourceManager.HUD1_HP_BG);

            widthUnit = -width * 0.025f;
            //this.height = heightUnit * GameScreen.player.getMaxHp();
            this.width = widthUnit * GameScreen.player.getCurrentLife();

        }

        @Override
        public void update() {

            if (width != widthUnit * GameScreen.player.getCurrentLife()) {
                width = widthUnit * GameScreen.player.getCurrentLife();
            }
        }

    }

    
}
