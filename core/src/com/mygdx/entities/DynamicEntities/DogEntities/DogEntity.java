/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.DogEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StreamUtils;
import com.mygdx.entities.DynamicEntities.DynamicEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author looch
 */
public class DogEntity extends DynamicEntity{

    protected BehaviorTree<DogEntity> dogbt;
    
    protected Texture dogTexture, alertTexture;
    protected float dogWidth, dogHeight;
    protected float alertWidth, alertHeight;
    
    protected ImageSprite moveSprite, alertSprite, idleSprite;
    protected float spriteScale;
    
    private Array<Sprite> trailSprites = new Array<Sprite>();
    private Array<Float> trailAlphas = new Array<Float>();
    
    private Body playerBody;
    private EnvVoid env;
    
    protected final float TEARRANGE = 6.0f;//In range of tear
    protected final float IDLERANGE = 0.2f;
    private final float PLAYER_IDLE_RANGE = 1.3f;//radius of idling around player
    private final float PLAYER_RANGE = 6.0f;//in range of player
    
    //idle movement
    private Vector2 rngv = new Vector2(0,0);
    private Random rng = new Random();
    private int[] rngset = {1,-1};
    private Vector2 destination = new Vector2(0,0);
    private final float MINDIST = 0.7f;
    
    //frame counters
    protected FrameManager fm = new FrameManager();
    private FrameCounter idleFC = new FrameCounter(2);
    
    //Edit: 2/11/16
    //Add special instance of Stella/Murphy for initial interaction
    
    
    public float getIdleRange() { return PLAYER_IDLE_RANGE; }
    public float getPlayerRange() { return PLAYER_RANGE; }
    
    public DogEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        userdata = "dog_";
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_WALL | BIT_EN;
        
        CHARSPEED = 100.0f;
        
        MAX_HP = 10000;
        CURRENT_HP = MAX_HP;
        
        dogTexture = MainGame.am.get(ResourceManager.STELLA_PH);
        dogWidth = iw;
        dogHeight = ih;
        alertTexture = MainGame.am.get(ResourceManager.STELLA_ALERT);
        alertWidth = iw * 3.3f * RATIO;
        alertHeight = ih * 3.3f * RATIO;
        
        texture = dogTexture;
        
        idleSprite = new ImageSprite("stella-idle", true);
        idleSprite.sprite.setScale(0.26f * RATIO);
        
        isprite = idleSprite;
    }
    
    @Override
    public void init(World world) {
        super.init(world);
        body.setLinearDamping(5.0f);
        
        //playerBody = GameScreen.player.getBody();
        //body.setTransform(playerBody.getPosition().add(new Vector2(5*rng.nextFloat(), 5*rng.nextFloat())), 0);
        
        env = (EnvVoid)EnvironmentManager.currentEnv;
        
        //ai
        Reader reader = null;
        try {
            reader = Gdx.files.internal("ai/stella.tree").reader();
            BehaviorTreeParser<DogEntity> parser = new BehaviorTreeParser<DogEntity>(BehaviorTreeParser.DEBUG_NONE);
            dogbt = parser.parse(reader,this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }
    
    @Override
    public void update(){
        super.update();
        
        dogUpdate();
        
        fm.update();
    }
    
    public void dogUpdate(){
        dogbt.step(); 
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(isprite != null){
            isprite.sprite.setPosition(
                    (body.getPosition().x * PPM - isprite.sprite.getWidth() / 2),
                    (body.getPosition().y * PPM - isprite.sprite.getHeight() / 2));
            
            isprite.render(sb);
            //esprite.sprite.draw(sb);
            //esprite.step();
            
            if(isprite.equals(moveSprite)){
                trailSprites.add(new Sprite(isprite.sprite));
                trailAlphas.add(1.0f);

                if (trailSprites.size > 0) {
                    boolean clearAlphas = true;
                    for (Float alpha : trailAlphas) {
                        if (alpha > 0) {
                            clearAlphas = false;
                        }
                    }

                    if (clearAlphas) {
                        trailSprites.clear();
                        trailAlphas.clear();
                    }
                }

                for (int i = 0; i < trailSprites.size; i++) {
                    trailSprites.get(i).setAlpha(trailAlphas.get(i));
                    trailAlphas.set(i, trailAlphas.get(i) - 0.15f < 0 ? 0 : trailAlphas.get(i) - 0.15f);
                    trailSprites.get(i).draw(sb);
                }
            }
            
            
        }
        else if(texture.equals(alertTexture))
            offsetRender(sb,width - iw/2, height -ih/2, 0.5f);
        else
            super.render(sb);
    
    }
    
    
    //@param: movement to destination, (catch up)
    public void moveTo(Vector2 dest, float speed){
        if(!atTear()){
            isprite = moveSprite;
            //idleRun.reset();
            idleFC.reset();
        }
        
        Vector2 dirv = this.getBody().getPosition().sub(dest);
        dirv = dirv.nor();
        this.getBody().applyForce(dirv.scl(-speed), dest, true);
        
    }
    
    
    
    public void idle(){
        if(PLAYER_IDLE_RANGE < destination.dst(GameScreen.player.getBody().getPosition())){
            float idledist = 0.0f;
            while (idledist < MINDIST){
                idledist = rng.nextFloat() * PLAYER_IDLE_RANGE ;
            }
            /*
            rngv = GameScreen.player.getBody().getPosition().cpy().nor();
            rngv.x *= idledist * rngset[(int)rng.nextInt(2)];
            rngv.y *= idledist * rngset[(int)rng.nextInt(2)];
            destination = body.getPosition().sub(rngv);
                    */
            
            
            
            if(!idleFC.complete){
                idleFC.start(fm);
                /*
                idleRun.reset();
                Thread thread = new Thread(idleRun);
                thread.start();
                */
            }else{
                isprite = idleSprite;
            }
            
            //esprite = null;
            //texture = dogTexture;
            //iw = dogWidth;
            //ih = dogHeight;
            
        }
        
        //todo: clean up
        //set new idle destination,  old code
        if(isNearTear() == null && !idleFC.running && body.getPosition().dst(destination) < 0.2f){
            float idledist = 0.0f;
            while (idledist < MINDIST){
                idledist = rng.nextFloat() * PLAYER_IDLE_RANGE ;
            }
            
            //rngv = playerBody.getPosition().cpy().nor();
            rngv = findClosestBody().getPosition().cpy().nor();
            rngv.x *= idledist * rngset[(int)rng.nextInt(2)];
            rngv.y *= idledist * rngset[(int)rng.nextInt(2)];
            destination = body.getPosition().cpy().sub(rngv);
            
            idleFC.start(fm);
            /*
            idleRun.reset();
            Thread thread = new Thread(idleRun);
            thread.start();
            */
            //idleThread.start(1500);
            
            
            
        }
        
        /*
        if( body.getPosition().dst(destination) >= 0.2f)
                moveTo(destination, CHARSPEED * 0.1f);
        */
        
        
    }
    
    //Gets clostest player body from current EnvVoid playerGhostBodies
    public Body findClosestBody(){
    
        Array<Body> bodies = env.getPlayerBodies();
        Body closestBody = bodies.peek();
        float dv = body.getPosition().dst(closestBody.getPosition());
        
        for(Body pbody: bodies){
            float dc = body.getPosition().dst(pbody.getPosition());
            if(dc < dv){
                closestBody = pbody;
                dv = dc;
            }
        }
        
        return closestBody;
        
    }
    
    
    //is Stella in range of player to idle??
    public boolean inRange(float range){
        //float dist = body.getPosition().dst(playerBody.getPosition());
        float dist = body.getPosition().dst(findClosestBody().getPosition());
        return dist < range;
    }
    
    
    
    public TearPortal isNearTear(){
        Array<TearPortal> tears = new Array<TearPortal>();
        //Array<Entity> entities = EnvironmentManager.currentEnv.getEntities();
        ArrayList<Entity> entities = EnvironmentManager.currentEnv.getEntities();
        for(Entity e: entities){
            if(e.getUserData().toString().contains("tear")){
                try{
                    tears.add((TearPortal)e);
                }catch(Exception ex){
                    System.out.println("@DogEntity: Current tear is not of type <TearPortal>");
                }
            }
        }
        
        for(TearPortal tp: tears){
            //if(TEARRANGE > playerBody.getPosition().dst(tp.getBody().getPosition())){
            if(TEARRANGE > findClosestBody().getPosition().dst(tp.getBody().getPosition())){
                isprite = alertSprite;
                isprite.sprite.rotate(-0.25f);
                //texture = alertTexture;
                //iw = alertWidth;
                //ih = alertHeight;
                return tp;
            }    
        }
        
        return null;
    }
    
    
    
    public boolean atTear(){
        TearPortal tp = isNearTear();
        
        if(tp != null && IDLERANGE > body.getPosition().dst(tp.getBody().getPosition())){
            body.setLinearVelocity(new Vector2(0,0));
            return true;
        }
        
        return false;
    }
}
