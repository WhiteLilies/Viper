/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viper;

import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author kimberlygilson
 */
class SnakeEnvironment extends Environment {

    private GameState gameState = GameState.PAUSED;
    private Grid grid;
    private int score = 0;
    private Snake snake;
    private ArrayList<Point> apples;
    private ArrayList<Point> poison;
    private int delay = 2;
    private int moveCounter = delay;
    private Image yoshiEgg;
    private Image yoshi;
    //private ArrayList<Point> goldenSnitches;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/brick_block.png"));
        
        this.yoshiEgg = (ResourceTools.loadImageFromResource("resources/yoshi_egg.png"));
        this.yoshi = (ResourceTools.loadImageFromResource("resources/yoshi.png"));

        //grid  
        this.grid = new Grid();
        this.grid.setPosition(new Point(50, 100));
        this.grid.setColor(new Color(0, 0, 0));
        this.grid.setColumns(40);
        this.grid.setRows(20);
        this.grid.setCellHeight(20);
        this.grid.setCellWidth(20);

        //   goldenSnitches = newArrayList<Point>();
        //   godlenSnitches.add(getRandomGridLocation());
        //apples that are not apples
        this.apples = new ArrayList<Point>();
        for (int i = 0; i < 2; i++) {
            this.apples.add(getRandomGridPoint());
        }
        //poison bottles
        this.poison = new ArrayList<Point>();
        for (int i = 0; i < 100; i++) {
            this.poison.add(getRandomGridPoint());
        }

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
        if (this.gameState == GameState.RUNNING) {
         
        if (snake != null) {
            if (moveCounter <= 0) {
                snake.move();
                moveCounter = delay;
                checkAppleSnakeIntersect();
                checkPoisonSnakeIntersect();
            } else {
                moveCounter--;
            }

        }
        }
    }

    private void checkAppleSnakeIntersect() {
        //if snake.head is in the same grid coordinate as ANY apple, then...
        // move apple
        // add to score
        // make a noise?
        for (int i = 0; i < this.apples.size(); i++) {
            if (this.apples.get(i).equals(this.snake.getHead())) {
                System.out.println("CHooommmmppppp!!!!!!!!!!!!!");
//                this.apples.get(i).x = (int) (Math.random() * this.grid.getColumns());
                this.apples.get(i).setLocation(getRandomGridPoint());
                this.score += 50;
                this.snake.grow(1);
            }
        }
    }

    private void checkPoisonSnakeIntersect() {
        for (int i = 0; i < this.poison.size(); i++) {
            if (this.poison.get(i).equals((this.snake.getHead()))) {
                System.out.println("Ew no");
                this.poison.get(i).setLocation(getRandomGridPoint());
                gameState = GameState.FINISHED;
            }
        }
    }

    private Point getRandomGridPoint() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//            this.score += 50;
//        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;    
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
                }
        //       else if (e.getKeyCode() == KeyEvent.VK_M){
        //           snake.move();
        //       }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.snake.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.snake.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.snake.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
            snake.setGrowthCounter(1);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.FINISHED;     
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

            //apples that are not apples
            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
                    this.apples.get(i);
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());
                }
                //poison
                if (this.poison != null) {
                    for (int i = 0; i < this.poison.size(); i++) {
                        this.poison.get(i);
                        GraphicsPalette.drawPoisonBottle(graphics, this.grid.getCellPosition(this.poison.get(i)), this.grid.getCellSize(), Color.BLACK);

                    }

                }


            }
            //snake
            Point cellLocation;
            graphics.setColor(Color.WHITE);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    if (i == 0) {
                        graphics.setColor(Color.red);
                        cellLocation= grid.getCellPosition(snake.getBody().get(i));
                        graphics.drawImage(yoshi, cellLocation.x, cellLocation.y, grid.getCellWidth(),grid.getCellHeight() , this);
                    } else {
                        graphics.setColor(Color.WHITE);
                        cellLocation = grid.getCellPosition(snake.getBody().get(i));
                        graphics.drawImage(yoshiEgg,cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight(), this);

                    }


//                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }
        }
        
        if (gameState == GameState.RUNNING) {
        graphics.setColor(new Color(255, 215, 0));
        graphics.setFont(new Font("Calibri", Font.BOLD, 30));
        graphics.drawString("Score: " + this.score, 50, 50);
        }
        

   //     graphics.drawImage(blackout, 200, 300, 50, 50, this);
        
        if (gameState == GameState.FINISHED) {
        this.setBackground(ResourceTools.loadImageFromResource("resources/black_bkg.jpg"));
        graphics.setColor(new Color(255, 0, 0));
        graphics.setFont(new Font("Calibri", Font.BOLD, 150));
        graphics.drawString("Dead", 260, 150);
        graphics.setColor(new Color(255, 215, 0));
        graphics.setFont(new Font("Calibri", Font.BOLD, 110));
        graphics.drawString("Score: " + this.score, 240, 250);
        }
    }
}
