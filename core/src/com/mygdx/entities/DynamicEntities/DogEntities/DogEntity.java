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
import com.mygdx.entities.DynamicEntities.SteerableEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.io.Reader;
import java.util.ArrayList;

/**
 *
 * @author looch
 */
public class DogEntity extends SteerableEntity{

    
    
    protected Texture dogTexture, alertTexture;
    protected float dogWidth, dogHeight;
    protected float alertWidth, alertHeight;
    
    protected ImageSprite moveSprite, alertSprite, idleSprite;
    protected float spriteScale;
    
    private Array<Sprite> trailSprites = new Array<Sprite>();
    private Array<Float> trailAlphas = new Array<Float>();
    
    
    private EnvVoid env;        //needed to find closest bodies in EnvVoid
    
    /*******************
            AI
    *******************/
    protected float CHARSPEED;
    protected BehaviorTree<DogEntity> bt;
    protected final float IDLE_AT_TEAR_RANGE = 0.2f;
    protected final float PLAYER_IDLE_RANGE = 1.5f;   //radius of idling around player
    protected final float PLAYER_RANGE_FOR_TEARS = 5f;        //in range of player
    protected String tearString = "tear";
    
    
    
    public DogEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        userdata = "dog_";
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_WALL | BIT_EN;
        
        MAX_HP = 10000;
        CURRENT_HP = MAX_HP;
        
        dogTexture = MainGame.am.get(ResourceManager.STELLA_ALERT);
        dogWidth = iw;
        dogHeight = ih;
        alertTexture = MainGame.am.get(ResourceManager.STELLA_ALERT);
        alertWidth = iw * 3.3f * RATIO;
        alertHeight = ih * 3.3f * RATIO;
        
        //todo: remove
        texture = dogTexture;
        
        idleSprite = new ImageSprite("stella-idle", true);
        idleSprite.sprite.setScale(0.26f * RATIO);
        
        isprite = idleSprite;
        
        CHARSPEED = 100.0f * RATIO;
        
        //ai
        /*
        this.maxLinearSpeed = 500f;
        this.maxLinearAcceleration = 3000f;
        this.maxAngularSpeed = 2000f;
        this.maxAngularAcceleration = 500f;
        
        
        moveToPlayerSB = new Arrive<Vector2>(this, tearTarget)
                .setTimeToTarget(0.01f)
                .setArrivalTolerance(2f)
                .setDecelerationRadius(10);
        
        
        moveToTearSB = new Seek<Vector2>(this, tearTarget);
        */
        
    }
    
    @Override
    public void init(World world) {
        super.init(world);
        body.setLinearDamping(5.0f);
        
        
        env = (EnvVoid)EnvironmentManager.currentEnv;
        
        //ai
        Reader reader = null;
        try {
            reader = Gdx.files.internal("ai/stella.tree").reader();
            BehaviorTreeParser<DogEntity> parser = new BehaviorTreeParser<DogEntity>(BehaviorTreeParser.DEBUG_NONE);
            bt = parser.parse(reader,this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
        
        
    }
    
    @Override
    public void update(){
        super.update();
        
        dogUpdate();
        
    }
    
    
    public void dogUpdate() {
        bt.step();

        if (isprite.equals(moveSprite) && trailSprites.size < 20) {
            trailSprites.add(new Sprite(moveSprite.sprite));
            trailAlphas.add(1.0f);
        }

        if (trailSprites.size > 0) {
            boolean clearAlphas = true;
            for (Float alpha : trailAlphas) {
                if (alpha >= 0.1f) {
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
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (isprite != null) {
            isprite.sprite.setPosition(
                    (body.getPosition().x * PPM - isprite.sprite.getWidth() / 2),
                    (body.getPosition().y * PPM - isprite.sprite.getHeight() / 2));

            isprite.render(sb);

            for (int i = 0; i < trailSprites.size; i++) {
                trailSprites.get(i).draw(sb);
            }

        }
        else if(texture.equals(alertTexture))
            
            offsetRender(sb,width - iw/2, height -ih/2);
        else
            super.render(sb);
    
    }
    
    
    //@param: movement to destination, (catch up)
    public void moveTo(Vector2 dest){
        Vector2 dirv = this.getBody().getPosition().sub(dest);
        dirv = dirv.nor();
        this.getBody().applyForce(dirv.scl(-CHARSPEED), dest, true);
        
    }
    
    
    
    
    public boolean inRange(float range){
        float dist = body.getPosition().dst(findClosestBody(body.getPosition()).getPosition());
        return dist <= range;
    }
    
    //In range of player to find tears
    public boolean inRangeOfPlayer(){
        return inRange(PLAYER_RANGE_FOR_TEARS);
    }
    
    public boolean inIdleRange(){
        return inRange(PLAYER_IDLE_RANGE);   
    }
    
    
    public void moveToPlayer(){
        if(GameScreen.player.getBody() == null) return;
        
        moveTo(findClosestBody().getPosition());
        
        
        isprite = moveSprite;
    }
    
    
    public void moveToTear(){
        isprite = moveSprite;
        Vector2 tv = isNearTear();
        
        moveTo(tv);
        
        idleAtTear(tv);
    }
    
    public void idle(){
        isprite = idleSprite;
    }
    
    //Gets closest player body from current EnvVoid playerGhostBodies 
    //
    //@param : Vector2 point - point to compare to closest player body
    
    public Body findClosestBody(Vector2 point){
    
        Array<Body> bodies = env.getPlayerBodies();
        Body closestBody = bodies.peek();
        float dv = point.dst(closestBody.getPosition());
        
        for(Body pbody: bodies){
            float dc = point.dst(pbody.getPosition());
            if(dc < dv){
                closestBody = pbody;
                dv = dc;
            }
        }
        
        return closestBody;
        
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
    
    
    
    public Vector2 isNearTear(){
        
        if(GameScreen.player.getBody() == null) return null;
        
        
        Array<TearPortal> tears = new Array<TearPortal>();
        ArrayList<Entity> entities = EnvironmentManager.currentEnv.getEntities();
        for(Entity e: entities){
            if(e.getUserData().toString().contains(tearString)){
                try{
                    tears.add((TearPortal)e);
                }catch(Exception ex){
                    System.out.println("@DogEntity: Current tear is not of type <TearPortal>");
                }
            }
        }
        
        float closest_dst = PLAYER_RANGE_FOR_TEARS; 
        Vector2 return_tv = null;
        
        for(TearPortal tp: tears){
            
            float dst = findClosestBody(body.getPosition()).getPosition().dst(tp.getBody().getPosition());
               
            if (dst < PLAYER_RANGE_FOR_TEARS
                    && dst < closest_dst
                    && !tp.isComplete()) {

                closest_dst = dst;
                return_tv = tp.getBody().getPosition();
            }   
        }
        
        return return_tv;
        
    }
    
    
    
    public boolean idleAtTear(Vector2 tv){
        
        if (tv != null && IDLE_AT_TEAR_RANGE > body.getPosition().dst(tv)) {
            body.setLinearVelocity(new Vector2(0, 0));

            isprite = alertSprite;
            isprite.sprite.rotate(-0.25f);

            return true;
        }

        return false;
    }
    
}

