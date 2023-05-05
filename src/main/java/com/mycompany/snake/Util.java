/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author alu10191634
 */
public class Util {
        
    public static void drawSquare(Graphics g, int row, int col, int squareWidth ,int squareHeight, SquareType squareType) {
        
        Color colors[] = {new Color(64,81,59),
                            new Color(93,156,89),
                            new Color(24, 102, 10), 
                            new Color(10, 10, 10)};
        int x = col * squareWidth;
        int y = row * squareHeight;
        Color color = colors[squareType.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth - 2,
                squareHeight - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight - 1, x, y);
        g.drawLine(x, y, x + squareWidth - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight - 1,
                x + squareWidth - 1, y + squareHeight - 1);
        g.drawLine(x + squareWidth - 1,
                y + squareHeight - 1,
                x + squareWidth - 1, y + 1);
    }
    
    
    public static void drawImage(Graphics g, int row, int col, int squareWidth ,int squareHeight, Board b, Food food, String filename){
        int x = col * squareWidth;
        int y = row * squareHeight;
        Image image = new ImageIcon(b.getClass().getResource(filename)).getImage();
        //g.drawImage(food.getImage(), x, y, null);
    }
}
