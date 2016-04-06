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
public class EndPad_Section_A3 extends EndPad_Section{

    public EndPad_Section_A3(Vector2 pos) {
        super(pos, 146f, 206f);
        
        piece = new EndPiece_A3();
        
        emptyTexture = MainGame.am.get(ResourceManager.ENDSECTION_A_3);
        fillTexture = MainGame.am.get(ResourceManager.ENDSECTION_A_3_FILL);
        
        texture = emptyTexture;
    }
    
    @Override
    public EndPad_Section copy(){
        return new EndPad_Section_A3(pos);
    }
    
    public class EndPiece_A3 extends EndPiece {

        public EndPiece_A3(Vector2 pos) {
            super(pos);

            texture = MainGame.am.get(ResourceManager.ENDPIECE_A_3);
            name = "Weird Piece " + id;
        }

        public EndPiece_A3() {
            this(new Vector2(0, 0));
        }

    }


}
