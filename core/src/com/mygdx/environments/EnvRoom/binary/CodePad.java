/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom.binary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 * 
 * Container for 8 code inputs for Binary Wall puzzle
 * 
 */
public class CodePad extends StaticEntity{
    
    private CodePanel[] panels = new CodePanel[8];
    private final Wall_Binary WALL;
    private final CodeMonitor cmon_left, cmon_right;
    
    public CodePad(Vector2 pos, float width, float height, Wall_Binary wall){
        super(pos,width,height);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "nwall_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        this.WALL = wall;
        
        Vector2 dv = new Vector2(pos.x - width + width/8, pos.y);
        Vector2 sv = new Vector2(width/4,0);
        for(int i = panels.length-1; i >= 0; i--){
            panels[i] = new CodePanel(dv.cpy(), width/8, height, (int)Math.pow(2, i));
            dv.add(sv);
        }
        
        cmon_left = new CodeMonitor(new Vector2(pos.x - width - 350f*RATIO, pos.y - height*0.5f), 200f*RATIO, 200f*RATIO, false);
        cmon_right = new CodeMonitor(new Vector2(pos.x + width + 175f*RATIO, pos.y - height*0.5f), 200f*RATIO, 200f*RATIO, true);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        for (CodePanel p : panels) {
            EnvironmentManager.currentEnv.spawnEntity(p);
        }
    }
    
    @Override
    public void update(){
        super.update();
        
        if(WALL.isLocked()
                && WALL.getLockCode() == calculatePanels()){
            WALL.openDoor();
        }
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        cmon_left.render(sb);
        cmon_right.render(sb);
    }

    public int calculatePanels() {
        int count = 0;
        
        for(CodePanel p : panels){
            if(p.isOn()){
                count += p.getValue();
            }
        }
        
        //update cmon
        cmon_left.setCodeValue(count);
        cmon_right.setCodeValue(count);
        
        return count;
    }
    
    //nested class for code display monitor
    private class CodeMonitor {

        private Vector2 pos;
        private float width, height;
        private boolean flip;
        private Texture bg, oneTex, tenTex, hundTex;
        private Texture 
                numTex_0, numTex_1,numTex_2,
                numTex_3,numTex_4,numTex_5,
                numTex_6,numTex_7,numTex_8,numTex_9,
                numTex_blank;

        private int code_value = 0;
        
        private float num_width, num_height,
                num_hun_x, num_ten_x, num_one_x, num_y;

        public void setCodeValue(int num) {
            this.code_value = num;
        }

        public CodeMonitor(Vector2 pos, float width, float height, boolean flip) {
            this.pos = pos;
            this.width = width;
            this.height = height;
            this.flip = flip;
            
            /*
            bg = MainGame.am.get(ResourceManager.ROOM_CODEMON);
            numTex_0 = MainGame.am.get(ResourceManager.ROOM_CODENUM_0);
            numTex_1 = MainGame.am.get(ResourceManager.ROOM_CODENUM_1);
            numTex_2 = MainGame.am.get(ResourceManager.ROOM_CODENUM_2);
            numTex_3 = MainGame.am.get(ResourceManager.ROOM_CODENUM_3);
            numTex_4 = MainGame.am.get(ResourceManager.ROOM_CODENUM_4);
            numTex_5 = MainGame.am.get(ResourceManager.ROOM_CODENUM_5);
            numTex_6 = MainGame.am.get(ResourceManager.ROOM_CODENUM_6);
            numTex_7 = MainGame.am.get(ResourceManager.ROOM_CODENUM_7);
            numTex_8 = MainGame.am.get(ResourceManager.ROOM_CODENUM_8);
            numTex_9 = MainGame.am.get(ResourceManager.ROOM_CODENUM_9);
            numTex_blank = MainGame.am.get(ResourceManager.ROOM_CODENUM_BLANK);
            */
            
            
            num_width = width*0.1732f;
            num_height = height * 0.4f;
            num_y = pos.y + height *0.335f;
            num_hun_x = pos.x + width*0.165f;
            num_ten_x = pos.x + width * 0.4f;
            num_one_x = pos.x + width*0.632f;
            
        }
        
        
        
        public void render(SpriteBatch sb){
            sb.draw(bg, flip ? pos.x + width : pos.x, pos.y, flip ? -width : width, height);
            
            //render numbers
            setTextures();
            
            if(hundTex != null){
                sb.draw(hundTex, num_hun_x, num_y, num_width, num_height);
            }
            if(tenTex != null){
                sb.draw(tenTex, num_ten_x, num_y, num_width, num_height);
            }
            if(oneTex != null){
                sb.draw(oneTex, num_one_x, num_y, num_width, num_height);
            }
            
        }
        
        private void setTextures(){
            //code_value
            //get indexes of value (x)(y)(z)
            //set texture for x, y, z
            int x, y, z;
            z = (code_value % 10);
            y = (code_value % 100 - z)/10;
            x = (code_value - y - z)/100;
            
            oneTex = getTexture(z);
            tenTex = getTexture(y);
            hundTex = getTexture(x);
            
            
        }
        
        private Texture getTexture(int n){
            switch(n){
                case 0:
                    return numTex_0;
                case 1:
                    return numTex_1;
                case 2:
                    return numTex_2;
                case 3:
                    return numTex_3;
                case 4:
                    return numTex_4;
                case 5:
                    return numTex_5;
                case 6:
                    return numTex_6;
                case 7:
                    return numTex_7;
                case 8:
                    return numTex_8;
                case 9:
                    return numTex_9;
                default:
                    return numTex_blank;
            }
        }

    }
    
}
