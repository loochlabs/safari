/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub.pads.A;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSub.pads.EndPad_Section;
import com.mygdx.environments.EnvSub.pads.EndPiece;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class EndPad_Section_A4 extends EndPad_Section{

    public EndPad_Section_A4(Vector2 pos) {
        super(pos, 267f, 216f);
        
        piece = new EndPiece_A4();
        
        emptyTexture = MainGame.am.get(ResourceManager.ENDSECTION_A_4);
        fillTexture = MainGame.am.get(ResourceManager.ENDSECTION_A_4_FILL);
        
        texture = emptyTexture;
    }
    
    @Override
    public EndPad_Section copy(){
        return new EndPad_Section_A4(pos);
    }
    
    private class EndPiece_A4 extends EndPiece {

        public EndPiece_A4(Vector2 pos) {
            super(pos);

            texture = MainGame.am.get(ResourceManager.ENDPIECE_A_4);
            name = "Weird piece " + id;
        }

        public EndPiece_A4() {
            this(new Vector2(0, 0));
        }

    }

}
