/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StreamUtils;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import java.io.Reader;

/**
 *
 * @author looch
 */
public class En_RedMatter extends EnemyEntity{

    public En_RedMatter(Vector2 pos) {
        super(pos, 15f, 15f);
        
        userdata = "en_" + id;
        fd.restitution = 0.2f;
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL | BIT_EN;
        
        MAX_HP = 30f;
        CURRENT_HP = MAX_HP;
        //CHARSPEED = 10f; 
        
        texture = MainGame.am.get(ResourceManager.EN_REDMATTER);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        this.initBT();
    }
    
    @Override
    public void update(){
        super.update();
        enemybt.step();
    }
    
    @Override
    public void damage(float d){}
    
    @Override
    public void initBT(){
        Reader reader = null;
        try {
            reader = Gdx.files.internal("ai/enemies/en_redMatter.tree").reader();
            BehaviorTreeParser<EnemyEntity> parser = new BehaviorTreeParser<EnemyEntity>(BehaviorTreeParser.DEBUG_NONE);
            enemybt = parser.parse(reader,this);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }
    
    @Override
    public void patrol(){
        //body.applyForceToCenter(new Vector2(1.0f, 0).scl(CHARSPEED), true);
    }
}
