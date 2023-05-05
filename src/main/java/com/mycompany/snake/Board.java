/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javax.swing.Timer;

/**
 *
 * @author alu10191634
 */
public class Board extends javax.swing.JPanel implements InitGamer {


    private Timer timer;
    private Snake snake;
    private Food food;
    private MyKeyAdapter keyAdapter;
    private int deltaTime;
    private Vector<Direction> movements;
    private FoodFactory foodFactory;
    private Incrementer incrementer;
    private int highScore;

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        myInit();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void myInit() {
        foodFactory = new FoodFactory();
        keyAdapter = new MyKeyAdapter();

        setFocusable(true);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        highScore = 0;
    }

    @Override
    public void initGame() {
        removeKeyListener(keyAdapter);
        snake = new Snake(Direction.RIGTH, 4);
        movements = new Vector<>(2);
        food = generateFood();
        incrementer.resetScore();
        addKeyListener(keyAdapter);
        setDeltaTime();
        timer.start();
        repaint();
    }

    private void tick() {

        if (!movements.isEmpty()) {
            Direction dir = movements.get(0);
            if (movements.equals(dir)) {
                movements.clear();
            }
            snake.setDirection(dir);
            movements.remove(0);
        }
        if (snake.canMove()) {
            snake.move();
            if (snake.eatFood(food)) {
                incrementer.incrementScore(food.getPoints());
                food = generateFood();
            }
        } else {
            processGameOver();
        }
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }


    
    
    public void setDeltaTime() {
        switch (ConfigData.instance.getlevel()) {
            case 0:
                deltaTime = 400;
                break;
            case 1:
                deltaTime = 300;
                break;
            case 2:
                deltaTime = 150;
                break;
            default:
                throw new AssertionError();
        }
        timer.setDelay(deltaTime);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareWidth = getWidth() / ConfigData.instance.setBoardRowCol();
        int squareHeight = getHeight() / ConfigData.instance.setBoardRowCol();

        for (int row = 0; row < ConfigData.instance.setBoardRowCol(); row++) {
            for (int col = 0; col < ConfigData.instance.setBoardRowCol(); col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(new Color(255, 247, 212));
                } else {
                    g.setColor(new Color(255, 238, 179));
                }
                g.fillRect(col * squareWidth, row * squareHeight, squareWidth, squareHeight);
            }
        }
        if (snake != null) {
            snake.printSnake(g, squareWidth(), squareHeight());

        }
        if (food != null) {
            food.printFood(g, squareWidth(), squareHeight());
        }
    }

    private Food generateFood() {
        return foodFactory.getFood(snake);
    }

    public int squareWidth() {
        return getWidth() / ConfigData.instance.setBoardRowCol();
    }

    public int squareHeight() {
        return getHeight() / ConfigData.instance.setBoardRowCol();
    }

    public void setIncrementer(Incrementer incrementer) {
        this.incrementer = incrementer;
    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != Direction.RIGTH) {
                        snake.setDirection(Direction.LEFT);
                        movements.add(Direction.LEFT);
                        break;
                    }
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT) {
                        snake.setDirection(Direction.RIGTH);
                        movements.add(Direction.RIGTH);
                        break;
                    }

                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) {
                        snake.setDirection(Direction.UP);
                        movements.add(Direction.UP);
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) {
                        snake.setDirection(Direction.DOWN);
                        movements.add(Direction.DOWN);
                        break;
                    }
                default:
                    break;
            }
            repaint();
        }
    }

    public void pausedGame() {
        timer.stop();
    }

    @Override
    public void continueGame() {
        timer.start();
    }

    public void processGameOver() {
        timer.stop();
        removeKeyListener(keyAdapter);
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        GameOverDialog gOverDialog = new GameOverDialog(topFrame, true);
        gOverDialog.setInitGamer(this);
        gOverDialog.setScore(incrementer.getScore());
        if (incrementer.getScore() > highScore) {
            highScore = incrementer.getScore();
        }
        gOverDialog.setName();
        gOverDialog.setHighScore(highScore);
        gOverDialog.setVisible(true);
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

