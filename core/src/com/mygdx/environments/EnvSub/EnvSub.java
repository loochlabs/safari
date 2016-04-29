/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public abstract class EnvSub extends Environment{
    
    private final float x, y;
    private Texture bgWhite;
    private float bgWx, bgWy, bgWw, bgWh;
    
    private BoundingEntity boundingEntity;
    
    private final EndWarp endWarp;
    protected EndPad pad;
    
    private EntitySprite mistSprite;
    
    public EndPad getEndPad() { return pad; }
    
    public EnvSub(int id, int linkid, EndWarp endPad) {
        super(id);
        
        this.linkid = linkid;
        this.endWarp = endPad;
        
        //frame counters
        beginFC.setTime(0);
        endFC.setTime(0);
        
        width = 3000f*RATIO;
        height = 3000f*RATIO;
        this.x = width/2;
        this.y = height/2;
        
        bg = MainGame.am.get(ResourceManager.ENVSUB_END_FG);
        
        bgWhite = MainGame.am.get(ResourceManager.ENVSUB_END_BG);
        bgWw = width*3;
        bgWh = width*3;
        bgWx = -bgWw/2 + width/2;
        bgWy = -bgWh/2 + width/2;
        
        
        
        mistSprite = new EntitySprite(new Vector2(0,0), 1000,1000,"endPad-mist",true, false,
                false, false, 3.0f, false, false, true, true);
        
          createPad();
        
    }
    
    @Override
    public void init(){
        super.init();
        
        /*
        boundingBody = world.createBody(bd);
        boundingBody.createFixture(fd).setUserData(userdata);
        */
        
        boundingEntity = (BoundingEntity)this.spawnEntity(new BoundingEntity(new Vector2(width/2, height/2), width, height));
        
        
        this.spawnEntity(pad);
        pad.createSections(this);
        
        
        createBgSprites();
        
        //mist needs to be spawned after bgSprites to be on top
        //todo: add some sort of render sort for esprites
        mistSprite = new EntitySprite(
                mistSprite, 
                playerPos.x - mistSprite.getWidth()/2, 
                playerPos.y - mistSprite.getHeight()/2,
                1000,1000f,
                false, true);
        
        //mistSprite.sprite.setScale(3.0f * RATIO);
        
        this.spawnEntity(mistSprite);
        
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(bgWhite != null){
            sb.draw(bgWhite, bgWx, bgWy, bgWw, bgWh);
        }
        
        updateMist();
        
        super.render(sb);
    }
    
    /*
    @Override
     public void envTransition() {
        if(sm.getState() == StateManager.State.BEGIN ){
            
            //call Overlay transition out
            play();
            
        }
        
        //TRANSITION SCENES
        if(sm.getState() == StateManager.State.END ){
            EnvironmentManager.setCurrent(idwarp);
            
        }
    }*/
    
    
    @Override
    public void end(int id, float time){
        
        //set player position back to normal position in EnvVoid(linkid)
        
        Vector2 p = GameScreen.player.getBody().getPosition().cpy().sub(boundingEntity.getBody().getPosition()).nor();
        p.x *= 1.4f * endWarp.getWidth()/PPM;
        p.y *= 1.4f * endWarp.getWidth()/PPM;
        p.add(endWarp.getBody().getPosition());
        
        EnvironmentManager.get(linkid).setPlayerPos(p);
        
        super.end(id,time);
        
    }
    
    @Override
    public void setStartPos(Vector2 pos){
        //startPos = new Vector2(
                //boundingEntity.getBody().getPosition().x + pos.x * width/2/PPM, 
                //boundingEntity.getBody().getPosition().y + pos.y * width/2/PPM);
        startPos = new Vector2(
                x/PPM + pos.x * width/2/PPM, 
                y/PPM + pos.y * width/2/PPM);        
        playerPos = startPos;
        
    }
    
    public void updateMist(){
        /*
        Vector2 dv = boundingBody.getPosition().cpy().sub(GameScreen.player.getBody().getPosition());
        dv.nor();
        dv.scl(-width/2);
        dv.add(bd.position.x * PPM, bd.position.y * PPM);
        
        mistSprite.setPosition(new Vector2(dv.x - mistSprite.getWidth()/2, dv.y - mistSprite.getHeight()/2));
        */
    }
    
    private void createBgSprites(){
        //bg pieces
        /*
        int count = 185;
        for(int i = 0; i < count; i++){
            
            Vector2 v = new Vector2(
                    boundingBody.getPosition().x * PPM + (width/2) * rng.nextFloat() * rngNegSet.random(), 
                    boundingBody.getPosition().y * PPM + (width/2) * rng.nextFloat() * rngNegSet.random());
            
            
            //this.spawnEntity(new SubBgSprite(v,boundingBody.getPosition().cpy().scl(PPM),width/2));
        }*/
        
        /*
        int wcount = 100;
        for(int i = 0; i < wcount; i++){
            
            Vector2 v = new Vector2(
                    boundingBody.getPosition().x * PPM + (width/2) * rng.nextFloat() * rngNegSet.random(), 
                    boundingBody.getPosition().y * PPM + (width/2) * rng.nextFloat() * rngNegSet.random());
            
            
            this.spawnSprite(new SubWebSprite(v,boundingBody.getPosition().cpy().scl(PPM),width/2));
        }*/
    }
    
    
    public abstract void createPad();
    
    
    private class BoundingEntity extends StaticEntity{
        
        
        public BoundingEntity(Vector2 pos, float w, float h) {
            super(pos, w, h);

            userdata = "envEnd_wall_" + id;
            bd.position.set(width / PPM / 2, height / PPM / 2);
            cshape.setRadius(width / 2 / PPM);
            fd.shape = cshape;
            fd.isSensor = true;
            fd.filter.categoryBits = BIT_WALL;
            fd.filter.maskBits = BIT_PLAYER;
        }

        @Override
        public void alert(String[] str) {
            //warp to EnvSub-end

            try {
                if (str[0].equals("begin")) {
                    System.out.println("@EndSub begin player contact");
                    
                }

                if (str[0].equals("end")) {
                    System.out.println("@EndSub end contact");
                    end(linkid, 0);
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }

        }

    }
    
}
