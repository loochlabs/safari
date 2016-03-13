/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid;

/**
 *
 * @author looch
 */
public class VoidGrid {
    
    private final float x, y, width, height;
    
    private final int rows, cols;
    private final GridCell[][] grid;
    
    public float getX() {return x;}
    public float getY() {return y;}
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public int getRows() {return rows;}
    public int getCols() {return cols;}
    public GridCell[][] getGrid() {return grid;}
    
    public GridCell getCellById(int id) {
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(id == grid[r][c].getId())
                    return grid[r][c];
            }
        }
        return grid[0][0];
    }
    
    public VoidGrid(float x, float y, float w, float h, int rows, int cols, String type){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.rows = rows;
        this.cols = cols;
        
        grid = new GridCell[rows][cols];
        
        int count = 1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new GridCell(count, 
                        width/ cols, 
                        height/rows,
                        (c * width/cols + x),
                        (r * height/rows + y),
                        type
                    );
                
                count++;
            }
        }
    }
    
    
}
