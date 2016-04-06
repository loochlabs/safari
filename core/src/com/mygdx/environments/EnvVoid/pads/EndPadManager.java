/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid.pads;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.environments.EnvVoid.pads.A.EndPad_Section_A1;
import com.mygdx.environments.EnvVoid.pads.A.EndPad_Section_A2;
import com.mygdx.environments.EnvVoid.pads.A.EndPad_Section_A3;
import com.mygdx.environments.EnvVoid.pads.A.EndPad_Section_A4;
import com.mygdx.environments.EnvVoid.pads.A.EndPad_Section_C1;

/**
 *
 * @author looch
 */
public class EndPadManager {
    
    public static Array<EndPad_Section> sections;
    
    public static void init(){
        sections = new Array<EndPad_Section>();
        
        sections.add(new EndPad_Section_A1(new Vector2(0,0)));
        sections.add(new EndPad_Section_A2(new Vector2(0,0)));
        sections.add(new EndPad_Section_A3(new Vector2(0,0)));
        sections.add(new EndPad_Section_A4(new Vector2(0,0)));
        sections.add(new EndPad_Section_C1(new Vector2(0,0)));
                
    }
    
    public static Array<EndPad_Section> createSections(int n){
        Array<EndPad_Section> s = new Array<EndPad_Section>();
        
        int amount = n;
        do{
            s.add(sections.random().copy());
            amount--;
        }while(amount > 0);
        
        return s;
    }
    
}
