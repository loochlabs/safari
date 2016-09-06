/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.projectiles.ProjectileEntity;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.FrameCounter;

/**
 *
 * @author saynt
 */
public class Player_Test extends PlayerEntity{
    
    private final float SPEED_DEFAULT = 4.5f;
    private final float PROJ_SPEED = 90f;
    private final FrameCounter throwChargeFC = new FrameCounter(1);
    private final ChargeUI chargeUI;
    
    public Player_Test(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        CURRENT_SPEED = SPEED_DEFAULT;
        
        chargeUI = new ChargeUI(this, throwChargeFC);
    }
    
    @Override
    public void update(){
        super.update();
        chargeUI.update();
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        chargeUI.render(sb);
    }
    
    @Override
    public void action_down(int index) {
        switch (index) {
            case 1:
                if(!newCharge){
                    throwCharge();
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    public void action_up(int index) {
        switch (index) {
            case 1:
                if(newCharge){
                    throwRelease();
                }
                break;
            default:
                break;
        }
    }
    
    
    private boolean newCharge = false;
    
    private void throwCharge() {
        throwChargeFC.start(fm);
        chargeUI.start();
        newCharge = true;
        CURRENT_SPEED = 0.35f; 
        System.out.println("@Player_Test begin charge: ");
    }
    
    private void throwRelease() {
        float dx = Gdx.input.getX() - MainGame.WIDTH/2;
        float dy = MainGame.HEIGHT/2 - Gdx.input.getY();
        float sv = (float)throwChargeFC.CURRENT_FRAME / (float)throwChargeFC.MAX_FRAME;
        Vector2 dv = new Vector2(dx, dy).nor().scl(PROJ_SPEED * sv);
        
        throwChargeFC.reset();
        chargeUI.reset();
        newCharge = false;
        CURRENT_SPEED = SPEED_DEFAULT;
        
        //create projectile 
        EnvironmentManager.currentEnv.spawnEntity(new Projectile_TestRock(this, dv));
    }
    
    
    
    private class Projectile_TestRock extends ProjectileEntity{
        
        public Projectile_TestRock(PlayerEntity p, Vector2 dir) {
            super(p.getPos().cpy(), 10f, 10f, dir, 0.7f);
            
            texture = MainGame.am.get(ResourceManager.DEFAULT_SQUARE);
        }
        
        @Override
        public void alert(String [] str){
            durFC.setTime(2.0f);
            durFC.start(fm);
            dv = new Vector2(0,0);
        }
        
    }
    
    
    private class ChargeUI{
        
        private boolean active = false;
        private PlayerEntity p;
        private FrameCounter fc;
        private ImageSprite uiSprite;
        public boolean complete;
        
        private float xoffset = 20f*RATIO;
        
        public ChargeUI(PlayerEntity p, FrameCounter fc){
            this.p = p;
            this.fc = fc;
            uiSprite = new ImageSprite("charge", false);
            complete = uiSprite.isComplete();
        }
        
        //Set uiSprite to frame based on FrameCounter fc complete %
        public void update(){
            float pct = (float)fc.CURRENT_FRAME / (float)fc.MAX_FRAME;
            
            int index = 
                    pct < 0.2f ? 1 : 
                    pct < 0.4f ? 2 : 
                    pct < 0.6f ? 3 : 
                    pct < 0.8f ? 4 : 
                    5;
            
            uiSprite.step(index);
        }
        
        public void render(SpriteBatch sb){
            if(!active) return;
            
            uiSprite.sprite.setPosition(p.getPos().x + xoffset, p.getPos().y);
            uiSprite.render(sb);
        }
        
        public void start(){
            active = true;
        }
        
        public void reset(){
            uiSprite.reset();
            active = false;
        }
        
    }
    
}
