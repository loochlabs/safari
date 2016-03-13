/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull.A;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvNull.EnvNull;
import com.mygdx.environments.EnvNull.NullSection;
import com.mygdx.environments.EnvNull.NullSection.WallType;
import com.mygdx.environments.EnvNull.NullSection_A;
import com.mygdx.utilities.Coordinate;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public abstract class EnvNull_A extends EnvNull{

    public EnvNull_A(int id, int linkid) {
        super(id, linkid, 0);
        
        startPos = new Vector2(sectionWidth/2/PPM, sectionHeight/2/PPM);
        this.setPlayerToStart();
    }

    @Override
    public void initSections() {
        
        createLayout();
        
        /*
        for(NullSection sec: envSections){
            sec.init();
        }
        */
        
    }
    
    public abstract void createLayout();
    
    public void createSection(Coordinate c){
        gridCoords.add(c);
        /*
        envSections.add(new NullSection_A(
                new Vector2(gridCoords.peek().getX()*sectionWidth, gridCoords.peek().getY()*sectionHeight),
                sectionWidth,
                sectionHeight,
                this,
                gridCoords.peek(),
                0));
        
        //adjust wall types
        for(NullSection e : envSections){
            
            for(Coordinate cd : gridCoords){
                if(e.getCoord().checkAdjacent(cd, 0, 1))
                    e.setSide(0, false, WallType.CONNECTED);
                if(e.getCoord().checkAdjacent(cd, 1, 0))
                    e.setSide(1, false, WallType.CONNECTED);
                if(e.getCoord().checkAdjacent(cd, 0, -1))
                    e.setSide(2, false, WallType.CONNECTED);
                if(e.getCoord().checkAdjacent(cd, -1, 0))
                    e.setSide(3, false, WallType.CONNECTED);
            }
            
        }
*/
    }
    
}
