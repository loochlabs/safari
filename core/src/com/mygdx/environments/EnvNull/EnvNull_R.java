/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

/**
 *
 * @author looch
 */
public class EnvNull_R extends EnvNull{

    public EnvNull_R(int id, int linkid, int dif) {
        super(id, linkid, dif);
    }
    
    
    
    //GENERATE NULL SECTIONS
    @Override
    public void initSections(){
        /*
        switch(DIFFICULTY){
            case(0):
                sectionCount = 4;
                break;
            case(1):
                sectionCount = 6;
                break;
            default:
                sectionCount = 4;
                break;
        }
        
        /*
        this.createSections(sectionCount);
        
        for(NullSection sec: envSections){
            sec.init();
        }
        
        for(int i = 1 ; i < envSections.size; i++){
            spawnEnemyGroup(envSections.get(i));
        }
        
        for(NullSection sec: pitSections){
            sec.init();
        }
    */
    }
    
    
    public void createSections(){
        
        //CREATE THE SECITONS
        //generateSections();

    }
    
    
}
