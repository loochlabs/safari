/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid.pads.A;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvVoid.pads.EndPad_Section;
import com.mygdx.environments.EnvVoid.pads.EndPiece;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class EndPad_Section_A2 extends EndPad_Section{

    public EndPad_Section_A2(Vector2 pos) {
        super(pos, 244f, 227f);
        
        piece = new EndPiece_A2();
        
        emptyTexture = MainGame.am.get(ResourceManager.ENDSECTION_A_2);
        fillTexture = MainGame.am.get(ResourceManager.ENDSECTION_A_2_FILL);
        
        texture = emptyTexture;
    }
    
    @Override
    public EndPad_Section copy(){
        return new EndPad_Section_A2(pos);
    }
    
    private class EndPiece_A2 extends EndPiece {

        public EndPiece_A2(Vector2 pos) {
            super(pos);

            texture = MainGame.am.get(ResourceManager.ENDPIECE_A_2);
            name = "Weird Piece " + id;
        }

        public EndPiece_A2() {
            this(new Vector2(0, 0));
        }

    }

}
