/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Graphics;
import java.util.Random;


/**
 *
 * @author alu10191634
 */
public class SpecialFood extends Food {


    private static final int POINTS = 15;
    
    public SpecialFood(Snake snake) {
        super(snake);
        Random r = new Random();
        //timeVisible = r.nextInt(10) * 1000 + 3000;
        //startingTime = Calendar.getInstance().getTimeInMillis();
        int row = r.nextInt(ConfigData.instance.setBoardRowCol());
        int col = r.nextInt(ConfigData.instance.setBoardRowCol());
        
        while (snake.containNode(row, col)) {
            row = r.nextInt(ConfigData.instance.setBoardRowCol());
            col = r.nextInt(ConfigData.instance.setBoardRowCol()); 
        }
        setRow(row);
        setCol(col);
    }
    
        
    @Override
    public void printFood(Graphics g, int squareWidht, int squareHeight) {
        Util.drawSquare(g, getRow(), getCol(), squareWidht, squareHeight, SquareType.SPECIALFOOD);
    }
    
    @Override
    public void remove() {
    }
    
    @Override
    public int getPoints(){
        return POINTS;
    }
    @Override
    public int nodesWhenEat(){
        return 3;
    }
}
