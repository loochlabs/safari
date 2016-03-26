/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull.random;

import com.mygdx.environments.EnvNull.EnvNull;

/**
 *
 * @author saynt
 */
public class EnvNull_R extends EnvNull {

    public EnvNull_R(int id, int linkid, int difficulty) {
        super(id, linkid, difficulty);
    }

    @Override
    public void initSections() {
        super.initSections();

        for (EnvNull.LayerManager lm : layerManagers) {
            for (int i = 1; i < lm.layerSections.size; i++) {
                spawnEnemyGroup(lm.layerSections.get(i));
            }
        }

    }

}
