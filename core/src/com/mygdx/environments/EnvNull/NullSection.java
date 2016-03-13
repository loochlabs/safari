/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.DynamicEntities.enemies.EnemyEntity;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.StaticEntities.walls.NullWall;
import com.mygdx.entities.esprites.EntitySprite;
import static com.mygdx.environments.EnvNull.NullSection.WallType.WALL;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.Coordinate;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class NullSection {
    
    protected Texture bg;
    protected EntitySprite bgSprite;
    protected Vector2 pos;
    protected final float width, height;
    protected final EnvNull env;
    protected final Coordinate coord;
    protected final boolean [] availableSides = { true, true, true, true };
    protected final WallType [] sideTypes = { WALL, WALL, WALL, WALL };
    protected Array<EnemyEntity> enemyGroup;
    protected FallSensor fallSensor;
    protected int LAYER_DEPTH = 0;
    public NullSection childSection;
    
    public int getLayerDepth() { return LAYER_DEPTH; }
    public Vector2 getPos() { return pos; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Coordinate getCoord() { return coord; }
    public boolean[] getAvailableSides() { return availableSides; }
    public WallType[] getSideTypes() { return sideTypes; }
    
    
    public void setSide(int index, boolean value, WallType type) { 
        availableSides[index] = value; 
        sideTypes[index] = type;
    }
    
    
    
    //TODO: get rid of PIT, PITCONNECTED
    public enum WallType { WALL, CONNECTED, PIT_LOWER, PIT_HIGHER};
    
    
    public NullSection(Vector2 pos, float width, float height, EnvNull env, Coordinate coord, int depth){
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.env = env;
        this.coord = coord;
        this.LAYER_DEPTH = depth;
        
        bg = MainGame.am.get(ResourceManager.NULL_PH);
    }
    
    public void render(SpriteBatch sb){
        if(bgSprite != null){
            bgSprite.sprite.setPosition(bgSprite.x, bgSprite.y);//todo: not needed here, just set in constructor/init()
            bgSprite.step();
            bgSprite.sprite.draw(sb);
        }
        else if(bg != null){
            sb.draw(bg, pos.x, pos.y, width+1, height+1);
        }
    }
    
    
    
    public void init(){
        
        //north wall
        switch(sideTypes[0]){
            case WALL:
                env.spawnEntity(new NullWall(new Vector2(pos.x + width/2, pos.y + height - height*0.035f ),  width/2,  height*0.035f));
                break;
            case PIT_HIGHER:
                
                fallSensor = (FallSensor)env.spawnEntity(
                    new FallSensor(
                            new Vector2(pos.x + width/2, pos.y + height*0.10f + height ), 
                            width/2,  
                            15f*RATIO, 
                            childSection, 
                            this));
                
                break;
            case PIT_LOWER:
                
                //spawn SectionPad
                env.spawnEntity(new SectionPad(
                        new Vector2(pos.x + width/2, pos.y + height - height*0.07f), 
                        this));
            
                env.spawnEntity(new NullWall(new Vector2(pos.x + width/2, pos.y + height - height*0.035f ),  width/2,  height*0.035f));
                
                sideTypes[0] = WallType.WALL;
                
                break;
            default:
                break;
        }
       
        switch(sideTypes[1]){
            case WALL:
                env.spawnEntity(new NullWall(new Vector2(pos.x + width - width*0.05f, pos.y + height/2), width*0.05f, height/2));
                break;
            case PIT_HIGHER:
                
                fallSensor = (FallSensor)env.spawnEntity(
                    new FallSensor(
                            new Vector2(pos.x + width , pos.y + height/2),
                            15f*RATIO, 
                            height/2, 
                            childSection, 
                            this));
                
                break;
            case PIT_LOWER:
                
                //spawn SectionPad
                //spawn SectionPad
                env.spawnEntity(new SectionPad(
                        new Vector2(pos.x + width - width*0.085f, pos.y + height/2), 
                        this));
                
                env.spawnEntity(new NullWall(new Vector2(pos.x + width - width*0.05f, pos.y + height/2), width*0.05f, height/2));
                
                sideTypes[1] = WallType.WALL;
                
                break;
            default:
                break;
        }
        
        
        switch (sideTypes[2]) {
            case WALL:
                env.spawnEntity(new NullWall(new Vector2(pos.x + width / 2, pos.y + height * 0.1f), width / 2, height * 0.1f));
                break;
            case PIT_HIGHER:

                fallSensor = (FallSensor) env.spawnEntity(
                        new FallSensor(
                                new Vector2(pos.x + width / 2, pos.y + height * 0.11f),
                                width / 2,
                                15f * RATIO,
                                childSection,
                                this));

                break;
            case PIT_LOWER:

                //spawn SectionPad
                env.spawnEntity(new SectionPad(
                        new Vector2(pos.x + width / 2, pos.y + height * 0.15f), 
                        this));
                
                env.spawnEntity(new NullWall(new Vector2(pos.x + width / 2, pos.y + height * 0.1f), width / 2, height * 0.1f));
                
                sideTypes[2] = WallType.WALL;

                break;
            default:
                break;
        }
        
        
        switch (sideTypes[3]) {
            case WALL:
                env.spawnEntity(new NullWall(new Vector2(pos.x + width * 0.05f, pos.y + height / 2), width * 0.05f, height / 2));
                break;
            case PIT_HIGHER:

                fallSensor = (FallSensor) env.spawnEntity(
                        new FallSensor(
                                new Vector2(pos.x, pos.y + height / 2),
                                15f * RATIO,
                                height / 2,
                                childSection,
                                this));

                break;
            case PIT_LOWER:

                //spawn SectionPad
                env.spawnEntity(new SectionPad(
                        new Vector2(pos.x + width * 0.085f, pos.y + height / 2), 
                        this));
                
                env.spawnEntity(new NullWall(new Vector2(pos.x + width * 0.05f, pos.y + height / 2), width * 0.05f, height / 2));
                
                sideTypes[3] = WallType.WALL;

                break;
            default:
                break;
        }
        
        
        setTexture();
        
    }
    
    
    private class SectionPad extends StaticEntity {

        public NullSection parentSection;

        public SectionPad(Vector2 pos, NullSection parentSection) {
            super(pos, 40f * RATIO, 40f * RATIO);

            userdata = "action_" + id;
            fd.isSensor = true;
            fd.filter.categoryBits = BIT_WALL;
            fd.filter.maskBits = BIT_PLAYER;
            cshape.setRadius(width / PPM);
            fd.shape = cshape;

            esprite = new EntitySprite("red-alert", true);
            esprite.sprite.setBounds(pos.x - width / 2, pos.y - height / 2, width*2, height*2);
        }

        @Override
        public void actionEvent() {
            //send player to new section
            System.out.println("@SectionPad warp to section");
        }

        @Override
        public void alert(String str) {
            if (str.equals("active")) {
                GameScreen.player.inRangeForAction(this);
            }
            if (str.equals("inactive")) {
                GameScreen.player.outRangeForAction(this);
            }
        }

    }
    
    
    public void setTexture(){
        /*
        
        Sides
        0 - Wall
        1 - Connected
        2 - Pit
        
        1000 
        0100 
        0010 
        0001 
        1200 
        1020
        1002
        2100
        0120
        0102
        2010
        0210
        0012
        2001
        0201
        0021
        1100
        1010
        1001
        0110
        0101
        0011
        1120
        1102
        1210
        1012
        1201
        1021
        2110
        0112
        2101
        0121
        2011
        0211
        
        */
        
        
        if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1000);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1000);//2000
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0100);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0010);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0001);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1200);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1020);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1002);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2100);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0120);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0102);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2010);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0210);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0012);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2001);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0201);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0021);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1100);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1010);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1001);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0110);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0101);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0011);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1120);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1102);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1210);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1012);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1201);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1021);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2110);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0112);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2101);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0121);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2011);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0211);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1110);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1101);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1011);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0111);
        }
        
    }
    
    
    
    
}
