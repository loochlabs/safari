/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

/**
 *
 * @author looch
 */
public class Coordinate {
    
    private final int x,y;
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    //check if this and Coord c are equal
    public boolean compareTo(Coordinate c){
        return (x == c.getX() && y == c.getY());
    }
    
    //checks adjecent coordinates
    //@return:
    //      true - has atleast 1 
    public boolean isAdjacent(Coordinate c){
        
        return(
                (x+1 == c.getX() && y == c.getY())
                || (x-1 == c.getX() && y == c.getY())
                || (x == c.getX() && y+1 == c.getY())
                || (x == c.getX() && y-1 == c.getY())
                );
    }
    
    public boolean checkAdjacent(Coordinate c, int x, int y){
        return (this.x + x == c.getX() && this.y + y == c.getY());
    }
    
}
