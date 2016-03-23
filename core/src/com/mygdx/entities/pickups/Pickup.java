/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.pickups;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.combat.skills.Skill.SkillType;
import static com.mygdx.combat.skills.Skill.SkillType.ITEM;
import com.mygdx.entities.Entity;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.gui.descriptions.DescriptionWindow;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PICKUP;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public abstract class Pickup extends Entity{
    
    protected Object name;
    protected String desc;
    protected SkillType type = ITEM;
    protected DescriptionWindow descWindow;
    protected boolean pickupComplete = false;
    
    protected FixtureDef solidfd;
    protected CircleShape sensorShape = new CircleShape();
    protected boolean canPickup = false;
    protected FrameCounter pickupFC = new FrameCounter(0.7f);
    protected boolean flagSpawnForce = false;
    protected final float spawnForceValue = 800f;
    
    protected final float Y_FLOATING_UP = 15f, Y_FLOATING_DOWN = 0;
    protected final float Y_AMMOUNT = 0.8f;
    protected float yfloat = 0;
    protected boolean floatUp = true;
    
    //protected Skill itemSkill;
    
    //sound
    protected SoundObject_Sfx pickupSound;
    
    //public Skill getSkill() { return itemSkill; }
    public Object getName() { return name; }
    public DescriptionWindow getDescWindow() { return descWindow; }
    public boolean getCanPickup() { return canPickup; }
    
    public Pickup(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        userdata = "fragment" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyDef.BodyType.DynamicBody;
        sensorShape.setRadius(width*1.50f/PPM);
        fd.shape = sensorShape;
        fd.filter.categoryBits = BIT_PICKUP;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        solidfd = new FixtureDef();
        cshape.setRadius(width/PPM);
        solidfd.shape = cshape;
        solidfd.filter.categoryBits = BIT_PICKUP;
        solidfd.filter.maskBits = BIT_WALL | BIT_PICKUP;
        
        
        //sound
        pickupSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP);
        
    }
    
    //copy of pickup
    public Pickup(Pickup pickup){
        this(pickup.getPos(), pickup.getWidth(), pickup.getHeight());
    }
    

    @Override
    public void init(World world) {
        
        try {

            bd.position.set(pos.x / PPM, pos.y / PPM);//todo: soft code this somewhere else
            body = world.createBody(bd);
            body.createFixture(fd).setUserData(userdata);
            body.setUserData(userdata);
            body.createFixture(solidfd);
            body.setLinearDamping(5.0f);

            pickupFC.start(fm);

            if (flagSpawnForce) {

                body.applyForceToCenter(
                        new Vector2(
                                (spawnForceValue * rng.nextFloat() * 0.5f + spawnForceValue) * rngNegSet.random(),
                                (spawnForceValue * rng.nextFloat() * 0.5f + spawnForceValue) * rngNegSet.random()),
                        true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        yfloat = floatUp ? yfloat+ Y_AMMOUNT: yfloat - Y_AMMOUNT;
        if(yfloat > Y_FLOATING_UP && floatUp){
            floatUp = false;
        }else if(yfloat < 0 && !floatUp){
            floatUp = true;
        }
        
        if(isprite != null){
            isprite.sprite.setPosition(
                    //(body.getPosition().x * PPM - esprite.sprite.getWidth() / 2),
                    //(body.getPosition().y * PPM - esprite.sprite.getHeight() / 2));
                    (pos.x - isprite.sprite.getWidth() / 2),
                    (pos.y - isprite.sprite.getHeight() / 2));
            isprite.step();
            isprite.sprite.draw(sb);
        }else if(texture != null)
            //sb.draw(texture, body.getPosition().x*PPM-iw/2, body.getPosition().y*PPM-ih/2 + yfloat,iw,ih);
            sb.draw(texture, pos.x-iw/2, pos.y -ih/2 + yfloat, iw, ih);
        
        if(pickupComplete){
            deathAnim();
        }
        
        if(pickupFC.complete && !canPickup)   canPickup = true;
    }
    
    @Override
    public void alert(String str){
        if(canPickup)
            death();
    }
    
    @Override
    public void death(){
        super.death();
        
        GameScreen.overlay.addAlertText("+" + name + "");
        canPickup = false;
        pickupComplete = true;
        
        //sound
        pickupSound.play(false);
        
        System.out.println("Pickup death");
        
    }
    
    public void deathAnim(){
        
        ih *= 0.7f;
        iw *= 1.3f;
        
        if(ih <= 5.0f){
            dispose();
        }
        
    }
    
    public void spawnForce(){
        flagSpawnForce = true;
    }
    
    public abstract Pickup cpy();
    
    
    
    
}
