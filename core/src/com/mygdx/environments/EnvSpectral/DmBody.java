/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSpectral;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.entities.esprites.Esprite_SpectralDm;
import static com.mygdx.game.MainGame.RATIO;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class DmBody extends Entity{
    
    
    public DmBody(Vector2 pos){
        super(pos, 25f, 25f);
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyDef.BodyType.DynamicBody;
        cshape.setRadius(width/PPM);
        fd.shape = cshape;
        userdata = "dm-body";
        
        esprite = new Esprite_SpectralDm();
        esprite.sprite.setScale(0.75f * RATIO);
    }

    @Override
    public void init(World world) {
        
        body = world.createBody(bd);
        body.createFixture(fd).setUserData(userdata);
        body.setUserData(userdata);
        body.setLinearDamping(8.0f);
        
    }
    
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        esprite.sprite.scale(-0.01f);
    }
    
    
}
