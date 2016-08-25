/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.game.MainGame;
import com.mygdx.utilities.SteeringUtils;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class SteerableEntity extends Entity implements Steerable<Vector2>{

    protected boolean tagged;
    protected float boundingRadius;
    protected float maxLinearSpeed, maxLinearAcceleration;
    protected float maxAngularSpeed, maxAngularAcceleration;
    
    protected SteeringBehavior<Vector2> behavior;
    protected SteeringAcceleration<Vector2> steeringOutput;
    
    protected float RANGE;
    
    public float getRange() { return this.RANGE; }
    
    public SteerableEntity(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyDef.BodyType.DynamicBody;
        cshape.setRadius(width/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = cshape;
        userdata = "steerable_" + id;
        
        this.maxLinearSpeed = 500f;
        this.maxLinearAcceleration = 500f;
        this.maxAngularSpeed = 30f;
        this.maxAngularAcceleration = 5f;
        
        boundingRadius = cshape.getRadius();
        
        this.tagged = false;
        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        
    }
    
    //Needed for getting player closest body in DogEntity AI
    public SteerableEntity(Body b){
        this(new Vector2(0,0),0f,0f);
        
        this.body = b;
    }
    
    @Override
    public void init(World world) {
        body = world.createBody(bd);
        body.createFixture(fd).setUserData(userdata);
        body.setUserData(userdata);
        body.setLinearDamping(8.0f);
    }

    @Override
    public void update(){
        super.update();
        
        if(behavior != null){
            behavior.calculateSteering(steeringOutput);
            applySteering();
        }
    }
    
    private void applySteering(){
        boolean anyAccelerations = false;
        
        if(!steeringOutput.linear.isZero()){
            Vector2 force = steeringOutput.linear.scl(MainGame.STEP);
            body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }
        
        if(steeringOutput.angular != 0){
            body.applyTorque(steeringOutput.angular * MainGame.STEP, true);
            anyAccelerations = true;
        }else{
            Vector2 lv = getLinearVelocity();
            if(!lv.isZero()){
                float o = vectorToAngle(lv);
                body.setAngularVelocity((o - getAngularVelocity()) * MainGame.STEP);
                body.setTransform(body.getPosition(), o);
            }
        }
        
        if(anyAccelerations){
            //linear capping
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSq = velocity.len2();
            if(currentSpeedSq > maxLinearSpeed*maxLinearSpeed){
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSq)));
            }
            
            //angular capping
            if(body.getAngularVelocity() > maxAngularSpeed){
                body.setAngularVelocity(maxAngularSpeed);
            }
        }
    }
    
    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Vector2 newVector() {
        return new Vector2();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public float getMaxLinearSpeed() {
        return this.maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }
    
    public void setBehavior(SteeringBehavior<Vector2> behavior){
        this.behavior = behavior;
    }
    
    
    public SteeringBehavior<Vector2> getBehavior(){
        return behavior;
    }

    
    
}
