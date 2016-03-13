/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities;

import com.mygdx.entities.esprites.EntitySprite;
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
import com.mygdx.entities.text.TextDamage;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.GameStats;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter_Attack;
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
    
    //animations
    protected Texture texture;
    protected EntitySprite esprite;
    protected boolean flaggedForRenderSort = true;
    
    //particle effects
    protected Array<PooledEffect> effects = new Array();
    protected boolean flagEffectComplete = false;
    
    //****************
    
    protected BodyDef bd = new BodyDef();
    protected FixtureDef fd = new FixtureDef();
    protected PolygonShape shape = new PolygonShape();
    protected CircleShape cshape = new CircleShape();
    protected Body body;
    
    protected FrameCounter_Attack attackFC;
    
    //frame counter
    protected FrameManager fm = new FrameManager();//todo: should be updating here in entity.update()
    
    protected boolean alive = true, dead = false;
    
    
    //stats
    protected float MAX_HP, CURRENT_HP;//todo: old, doesnt apply to player
    protected float DAMAGE;//todo old
    
    protected final Random rng = new Random();
    protected final Array<Integer> rngNegSet = new Array<Integer>();
    
    
    public Vector2 getPos() {return pos;}
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public float getIw() {return iw;}
    public float getIh() {return ih;}
    public int getId() {return id;}
    public Object getUserData() { return userdata; }
    public Texture getTexture() {return texture;}
    public EntitySprite getSprite() { return esprite; }
    public BodyDef getBodyDef() {return bd;}
    public FixtureDef getFixtureDef() {return fd;}
    public PolygonShape getShape() {return shape;}
    public CircleShape getCshape() {return cshape;}
    public Body getBody() {return body;}
    public void setBody(Body b) { body = b; }
    public FrameCounter_Attack getAttackFC() { return attackFC; }
    public boolean isAlive() {return alive;}
    public boolean isDead() { return dead; }
    public float getCurrentHp() {return CURRENT_HP;}
    public float getMaxHp() {return MAX_HP;}
    public float getDamage() { return DAMAGE; }
    public FrameManager getFrameManager() { return fm; }
    
    public void setPosition(Vector2 pos) { 
        this.pos = pos; 
        bd.position.set(pos.x/PPM, pos.y/PPM);
    
    }
    
    public void setUserData(Object data) { this.userdata = data; }
    public void setCurrentHp(float hp) { this.CURRENT_HP = hp; }
    public void setMaxHp(float hp) { this.MAX_HP = hp; }
    public void setDamage(float damage) { this.DAMAGE = damage; }
    
    
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
        if(!alive && !dead)
            death();
    }
    
    public void render(SpriteBatch sb){
        if(esprite != null){
            if (esprite.getXFlip()) {
                esprite.sprite.setPosition(
                        (body.getPosition().x * PPM + esprite.sprite.getWidth() / 2),
                        (body.getPosition().y * PPM - esprite.sprite.getHeight() / 2));
            } else {
                esprite.sprite.setPosition(
                        (body.getPosition().x * PPM - esprite.sprite.getWidth() / 2),
                        (body.getPosition().y * PPM - esprite.sprite.getHeight() / 2));
            }
            esprite.render(sb);
        }else if(texture != null)
            sb.draw(texture, body.getPosition().x*PPM-width, body.getPosition().y*PPM-height,iw,ih);
        
        
        renderEffects(sb);
    }
    
    public void offsetRender(SpriteBatch sb, float x, float y, float rotation){
        if(esprite != null){
            esprite.drawOffset(
                    sb, 
                    body.getPosition().x*PPM - esprite.sprite.getWidth()/2 + x, 
                    body.getPosition().y*PPM - esprite.sprite.getHeight()/2 + y);
        }else if(texture != null){
            sb.draw(
                    texture, 
                    body.getPosition().x*PPM-width + x, 
                    body.getPosition().y*PPM-height + y,
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
        e.setPosition(body.getPosition().x*PPM - e.getEmitters().peek().getXOffsetValue().getLowMax(), body.getPosition().y*PPM);
        effects.add(e);
    }
    
    public void removeEffect(PooledEffect e){
        effects.removeValue(e, false);
    }
    
    
    public void damage(float d){
        CURRENT_HP -= d;
        System.out.println("@Entity " + this.toString() + " damaged - hp: " + CURRENT_HP);
        
        if(CURRENT_HP <= 0){
            alive = false;
        }
        try {
            if (body != null) {
                EnvironmentManager.currentEnv.addDamageText(
                        "" + (int) (d + 1) + "",
                        new Vector2(
                                body.getPosition().x * PPM - width * rng.nextFloat() / 2,
                                body.getPosition().y * PPM + height * 2.0f));
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    
    public void damage(float dmg, boolean isCombo){
        CURRENT_HP -= dmg;
        System.out.println("@Entity " + this.toString() + " COMBO damaged - hp: " + CURRENT_HP);
        
        if(CURRENT_HP <= 0){
            alive = false;
        }
        
        EnvironmentManager.currentEnv.addDamageText(
                new TextDamage(
                        "" + (int)(dmg+1) + "",
                        new Vector2(
                            body.getPosition().x*PPM - width*rng.nextFloat()/2, 
                            body.getPosition().y*PPM + height * 1.35f), 
                        isCombo));
    }
    
    
    public void fall(){}
    
    public void death(){
        dead = true;
    }
    
    public void dispose(){
        EnvironmentManager.currentEnv.removeEntity(this);
        
        if(GameScreen.player.getAttTargets().contains(this)){
            GameScreen.player.getAttTargets().remove(this);
        }
    }
    
    //Description: generic alert method
    public void alert(){}
    public void alert(String string){}
    
    public void actionEvent(){}
    
    //compares the y-coord for two entities, returns 1 if this is lower than (param) Entity t1
    //Used for render sorting
    public static class EntityComp implements Comparator<Entity>{

        @Override
        public int compare(Entity t, Entity t1) {
            
            if(!t.flaggedForRenderSort && !t1.flaggedForRenderSort)
                return 0;
            else if(t.flaggedForRenderSort && !t1.flaggedForRenderSort)
                return 1;
            else if(!t.flaggedForRenderSort && t1.flaggedForRenderSort)
                return -1;
            else if(t.getBody().getPosition().y < t1.getBody().getPosition().y)
                return 1;
            else if(t.getBody().getPosition().y == t1.getBody().getPosition().y)
                return 0;
            else
                return -1;
        }
        
    }
}
