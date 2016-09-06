/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.DynamicEntities.SteerableEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.utilities.Direction;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PICKUP;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class PlayerEntity extends SteerableEntity{
    
    //graphics
    
    protected ImageSprite frontSprite,backSprite, rightSprite, leftSprite;
    protected ImageSprite idleSprite, diveSprite, warpSprite, recovSprite;
    protected ImageSprite attackSprite, attackHeavySprite, playerBuffSprite;//player body animations
    protected ImageSprite beginSpectralSprite, deathSprite;
    
    protected float spriteScale = 1.0f;
    protected boolean warping = false;
    protected float endTime = 2.0f;
    
    protected String playerName = "player";
    
    public String getPlayerName() { return playerName; }
    
    //movement
    protected float CURRENT_SPEED = 10.0f;
    private boolean moveUp, moveDown,moveLeft,moveRight = false;
    private Vector2 currentDirection = new Vector2(0,0);
    private Vector2 prevLocation = new Vector2(0,0);
    private float currentAngle = 0;

    //player state
    private final StateManager sm;
    
    //items
    private BitmapFont font;
    private Texture actionTexture;
    
    public ImageSprite getBeginSpectralSprite() { return beginSpectralSprite; }
    public ImageSprite getDeathSprite() { return deathSprite; }
    public ImageSprite getRecovSprite() { return recovSprite; }
    public ImageSprite getDiveSprite() { return diveSprite; }
    public ImageSprite getBuffSprite() { return playerBuffSprite; }
    public StateManager getStateManager() { return sm; }
    public float getSpriteScale() { return spriteScale; }
    public float getCurrentAngle() { return currentAngle; } 
    public Vector2 getCurrentDirection() { return currentDirection; }
    
    
    public PlayerEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        userdata = "player_" + id;
        fd.filter.categoryBits = BIT_PLAYER;
        fd.filter.maskBits = BIT_WALL | BIT_EN | BIT_PICKUP;
        fd.restitution = 0.05f;
        fd.density = 5.0f;
        fd.shape = shape;
        bd.fixedRotation = true;
        sensordata = "playatt_norm";
        
        sm = new StateManager();
        
        //font
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setScale(RATIO);
        
        texture = MainGame.am.get(ResourceManager.DEFAULT_SQUARE);
        
        //action key
        //actionTexture = MainGame.am.get(ResourceManager.GUI_PAD_Y);
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        if(isprite != null){
            isprite.render(sb);
        }else
            super.render(sb);
        
        //particle effects
        renderEffects(sb);
        
        renderActionKey(sb);
    }
    
    
    
    @Override 
    public void renderEffects(SpriteBatch sb){
        
        for(PooledEffect e : effects){
            e.setPosition(
                    body.getPosition().x*PPM - e.getEmitters().peek().getXOffsetValue().getLowMax(), 
                    body.getPosition().y*PPM - height);
        }
        super.renderEffects(sb);
    }
    
    public void renderActionKey(SpriteBatch sb){
        if(actionEntities.size > 0){
            if (!GameInputProcessor.controller) {
                font.draw(sb,
                        Keys.toString(GameInputProcessor.KEY_ACTION_ACTION),
                        pos.x - width,
                        pos.y + height * 2.5f + font.getCapHeight());
            } else {
                sb.draw(
                        actionTexture,
                        pos.x - width,
                        pos.y + height * 2.5f,
                        50f * RATIO, 50f * RATIO);
            }
        }
    }
    
    
    

    @Override
    public void update() {
        super.update();

        moveUpdate();
        updateDirection();
    }

    private Vector2 dv = new Vector2(0,0);
    
    public void moveUpdate() {

        if (body == null) {
            return;
        }

        //applies movement and adjusts sprites
        dv.x = 0;
        dv.y = 0;

        if (moveDown && -CURRENT_SPEED < body.getLinearVelocity().y) {
            dv.y -= 1;
        }

        if (moveUp && CURRENT_SPEED > body.getLinearVelocity().y) {
            dv.y += 1;
        }

        if (moveRight && CURRENT_SPEED > body.getLinearVelocity().x) {
            dv.x += 1;
        }

        if (moveLeft && -CURRENT_SPEED < body.getLinearVelocity().x) {
            dv.x -= 1;
        }

        float dv_scale = CURRENT_SPEED;

        dv.nor().scl(dv_scale);
        body.applyForce(dv, body.getPosition(), true);

        if (!(moveUp || moveDown || moveRight || moveLeft)
                || ((moveUp && moveDown) || (moveLeft && moveRight))) {
            isprite = idleSprite;
        }

        float ang = body.getAngle();
        if (moveDown && !moveUp) {
            isprite = frontSprite;
            ang = (float) (180 * Math.PI / 180);
        }
        if (moveUp && !moveDown) {
            isprite = backSprite;
            ang = 0;
        }
        if (moveRight && !moveLeft) {
            isprite = rightSprite;
            ang = (float) (270 * Math.PI / 180);

        }
        if (moveLeft && !moveRight) {
            isprite = leftSprite;
            ang = (float) (90 * Math.PI / 180);
        }

    }
    
    //@param: Direction up,down,left,right (U,D,L,R)
    //responsible for getting input and setting direction states
    public void move(Direction d) {
        if (sm.getState() == State.PLAYING) {
            switch (d) {
                case UP:
                    moveUp = true;
                    
                    break;
                case DOWN:
                    moveDown = true;
                    
                    break;
                case LEFT:
                    moveLeft = true;
                    
                    break;
                case RIGHT:
                    moveRight = true;
                    
                    break;
                default:
                    break;
            }
        }
    }
    
    //@param: Direction up,down,left,right (U,D,L,R)
    public  void moveStop(Direction d) {
        switch (d) {
            case UP:
                moveUp = false;
                break;
            case DOWN:
                moveDown = false;
                break;
            case LEFT:
                moveLeft = false;
                break;
            case RIGHT:
                moveRight = false;
                break;
            default:
                break;
        }
    }
    
    private void updateDirection(){
        if(body == null) return;
        
        currentDirection = body.getPosition().cpy().sub(prevLocation.cpy()).nor();
        prevLocation = body.getPosition().cpy();
        currentAngle = currentDirection.angleRad();
    }
    
    public boolean isUserMoving() { return moveUp || moveRight || moveLeft || moveDown; }
    
    public void clearMovement(){
        moveUp = false;
        moveDown = false;
        moveRight = false;
        moveLeft = false;
    }
    
    
    @Override
    public void alert(String[] str) {
        try {
            if (str[0].equals("begin") && str[1].contains(sensordata.toString())) {
                for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
                    if (e.getUserData() != null
                            && e.getUserData().equals(str[2])) {

                        //b2d senesor data/action
                    }
                }
            } else if (str[0].equals("end") && str[1].contains(sensordata.toString())) {
                for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
                    if (e.getUserData() != null
                            && e.getUserData().equals(str[2])) {

                        //b2d senesor data/action
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    
    //Description:  sets player warp animation during env end state
    //@param: pos - position of tear
    public void warp(Vector2 pos){
        warping = true;
        warpSprite.reset();
        warpSprite.sprite.setPosition(
                pos.x*PPM - warpSprite.sprite.getWidth()/2, 
                pos.y*PPM - warpSprite.sprite.getHeight()/2);
        isprite = warpSprite;
    }
    
    public void action_down(int index){}
    public void action_up(int index){}
    
    //*****************************
    //Desctiption: for action events involving actionEntity
    //private Entity actionEntity;
    private Array<Entity> actionEntities = new Array<Entity>();
    
    public void inRangeForAction(Entity e){
        //show hot key
        //enable hotkey
        //actionEntity = e;
        actionEntities.add(e);
    }
    
    public void outRangeForAction(Entity e){
        //actionEntity = null;
        actionEntities.removeValue(e, false);
    }
    
    public void beginAction(){
        if(actionEntities.size > 0)
            actionEntities.peek().actionEvent();
    }
    
    public void clearActionEvents(){
        actionEntities.clear();
    }
}
