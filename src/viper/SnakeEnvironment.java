/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viper;

import environment.Environment;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author kimberlygilson
 */
class SnakeEnvironment extends Environment {

    private Grid grid;
    private int score = 0;
    private Snake snake;
    
    private int delay = 5;
    private int moveCounter = delay;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/black_woodbg.jpg"));
        //grid  
        this.grid = new Grid();
        this.grid.setPosition(new Point(50, 100));
        this.grid.setColor(new Color(249, 130, 171));
        this.grid.setColumns(40);
        this.grid.setRows(20);
        this.grid.setCellHeight(20);
        this.grid.setCellWidth(20);
        //snake
        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(4, 3));

    }

    @Override
    public void timerTaskHandler() {
        //gets called twenty times a second 
        //    System.out.println("Timer");
        //.move is a method
        if (snake != null){
            if (moveCounter <= 0){
                snake.move();
                moveCounter = delay;
            } else {
                moveCounter --;
            }
            
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.score += 50;
        } //       else if (e.getKeyCode() == KeyEvent.VK_M){
        //           snake.move();
        //       }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.snake.setDirection(Direction.UP);
            snake.move();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.snake.setDirection(Direction.DOWN);
            snake.move();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.snake.setDirection(Direction.RIGHT);
            snake.move();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.snake.setDirection(Direction.LEFT);
            snake.move();
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
            snake.setGrowthCounter(1);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null) {
            this.grid.paintComponent(graphics);

            //snake
            Point cellLocation;
            graphics.setColor(Color.WHITE);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    if (i == 0){
                        graphics.setColor(Color.red);
                    } else {
                        graphics.setColor(Color.WHITE);
                    }
                    
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }
        }
        graphics.setFont(new Font("Calibri", Font.BOLD, 30));
        graphics.drawString("Score: " + this.score, 50, 50);

    }
}
