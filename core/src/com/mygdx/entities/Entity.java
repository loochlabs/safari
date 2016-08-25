/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.GameStats;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author looch
 */
public abstract class Entity{
    
    protected Vector2 pos;
    protected float width, height, iw, ih;
    protected int id;
    protected Object userdata = "default";
    protected Object sensordata = null;
    public boolean active = true;
    
    //animations
    protected Texture texture;
    protected ImageSprite isprite;
    protected boolean flaggedForRenderSort = true;
    protected boolean flaggedForRenderTop = false;
    
    //particle effects
    protected Array<PooledEffect> effects = new Array();
    protected boolean flagEffectComplete = false;
    
    //b2d
    protected BodyDef bd = new BodyDef();
    protected FixtureDef fd = new FixtureDef();
    protected PolygonShape shape = new PolygonShape();
    protected CircleShape cshape = new CircleShape();
    protected Body body;
    
    //frame counter
    protected FrameManager fm = new FrameManager();
    
    protected final Random rng = new Random();
    protected final Array<Integer> rngNegSet = new Array<Integer>();
    
    
    public Vector2 getPos() {return pos;}
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public float getIw() {return iw;}
    public float getIh() {return ih;}
    public int getId() {return id;}
    public Object getUserData() { return userdata; }
    public Object getSensorData() { return sensordata; }
    public Texture getTexture() {return texture;}
    public ImageSprite getSprite() { return isprite; }
    public BodyDef getBodyDef() {return bd;}
    public FixtureDef getFixtureDef() {return fd;}
    public PolygonShape getShape() {return shape;}
    public CircleShape getCshape() {return cshape;}
    public Body getBody() {return body;}
    public FrameManager getFrameManager() { return fm; }
    public boolean getFlaggedForRenderSort() { return flaggedForRenderSort; }
    public boolean getFlaggedForRenderTop() { return flaggedForRenderTop; }
    
    
    public void setPosition(Vector2 pos) {
        if(body != null){
            body.setTransform(new Vector2(pos.x/PPM, pos.y/PPM), body.getAngle());
        }
        this.pos = pos; 
        bd.position.set(pos.x/PPM, pos.y/PPM);
    }
    
    public void setBody(Body b) { body = b; }
    public void setUserData(Object data) { this.userdata = data; }
    
    public Entity(Vector2 pos, float w, float h){
        this.pos = pos;
        this.width = w;
        this.height = h;
        this.id = ++GameStats.idcount;
        this.iw = width*2;
        this.ih = height*2;
        
        rngNegSet.add(1);
        rngNegSet.add(-1);
        
    }
    
    public abstract void init(World world);
    
    public void update(){
        
        fm.update();
            
        if(body != null){
            pos.x = body.getPosition().x * PPM;
            pos.y = body.getPosition().y * PPM;
        }
        
        if(isprite != null && !isprite.getPause()){
            isprite.step();
        }
    }
    
    public void render(SpriteBatch sb){
        if(isprite != null){
            if (isprite.getXFlip()) {
                isprite.sprite.setPosition((pos.x + isprite.sprite.getWidth()/2),
                        (pos.y - isprite.sprite.getHeight()/2));
            } else {
                isprite.sprite.setPosition((pos.x - isprite.sprite.getWidth()/2),
                        (pos.y - isprite.sprite.getHeight()/2));
            }
            isprite.render(sb);
        }else if(texture != null)
            sb.draw(texture, pos.x - width, pos.y - height, iw, ih);
        
        
        renderEffects(sb);
    }
    
    public void offsetRender(SpriteBatch sb, float x, float y){
        if(isprite != null){
            isprite.drawOffset(sb, x, y);
        }else if(texture != null){
            sb.draw(
                    texture, pos.x -width + x, pos.y -height + y, 
                    iw,ih);
        }
    }
    
    //particle effect rendering
    public void renderEffects(SpriteBatch sb){
        for (int i = effects.size - 1; i >= 0; i--) {
            PooledEffect effect = effects.get(i);
            effect.draw(sb, MainGame.STEP);
            if (effect.isComplete()) {
                effect.free();
                effects.removeIndex(i);
            }
        }
        
    }
    
    public void addEffect(PooledEffect e){
        e.setPosition(pos.x - e.getEmitters().peek().getXOffsetValue().getLowMax(), pos.y);
        effects.add(e);
    }
    
    public void removeEffect(PooledEffect e){
        effects.removeValue(e, false);
    }
    
    public void dispose(){
        EnvironmentManager.currentEnv.removeEntity(this);
    }
    
    //Description: generic alert method
    public void alert(String [] string){}
    
    public void actionEvent(){}
    
    //compares the y-coord for two entities, returns 1 if this is lower than (param) Entity t1
    //Used for render sorting
    public static class EntityComp implements Comparator<Entity>{

        @Override
        public int compare(Entity e1, Entity e2) {
            
            if(!e1.flaggedForRenderSort && !e2.flaggedForRenderSort)
                return 0;
            else if(e1.flaggedForRenderSort && !e2.flaggedForRenderSort)
                return 1;
            else if(!e1.flaggedForRenderSort && e2.flaggedForRenderSort)
                return -1;
            else if(e1.getPos().y < e2.getPos().y)
                return 1;
            else if(e1.getPos().y == e2.getPos().y)
                return 0;
            else
                return -1;
        }
        
    }
}
