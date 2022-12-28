package org.example.game;

import org.example.graphics_objects.*;
import org.example.multiplayer.ClientHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SnakeGame implements ActionListener {

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int UNIT_SIZE = 20;
    private int numberOfCells = 6;
    private int foodEaten = 0;
    private boolean gameRunning = false;
    private Random random;
    private Snake snake;
    private Food food;
    private ArrayList<DrawableGameObject> drawableObjects;
    private ArrayList<ClientHandler> clients;
    private HashMap<ClientHandler, Snake> clientSnakes;
    private final int CELL_WIDTH = 20;
    private final int CELL_HEIGHT = 20;
    private final int NUMBER_OF_CELLS_AT_START;
    public SnakeGame(int screenWidth, int screenHeight) {
        this.SCREEN_HEIGHT = screenHeight;
        this.SCREEN_WIDTH = screenWidth;
        this.drawableObjects = new ArrayList<DrawableGameObject>(this.snake.getSnakeCells());
        this.clientSnakes = new HashMap<>();
        this.NUMBER_OF_CELLS_AT_START = 10;
        this.random = new Random();

    }
    public void startGame() {
        this.newApple();
        this.gameRunning = true;
    }

    public Snake getSnake() {
        return this.snake;
    }
    public void move() {
        this.snake.move();
    }

    public void newApple() {
        int foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        int foodY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        this.drawableObjects.remove(this.food);
        this.food = new Food(foodX, foodY, this.CELL_WIDTH,this.CELL_HEIGHT, Color.red, new Circle());
        this.drawableObjects.add(this.food);
    }

    public void checkFoodCollision() {

        this.clients.forEach(client ->  {
            int positionXHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionX();
            int positionYHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionY();
            this.checkCellCollisionWithFood(positionXHead, positionYHead, this.clientSnakes.get(client));
        });

    }

    private void checkCellCollisionWithFood(int positionX, int positionY, Snake snake) {
        if (positionX == this.food.getPositionX() && positionY == this.food.getPositionY()) {
            numberOfCells++;
            foodEaten++;

            snake.addCellToTail();
            newApple();
        }
    }


    public void checkCollisions() {

       this.clients.forEach(client -> {
            int positionXHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionX();
            int positionYHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionY();

           if (positionYHead >= SCREEN_HEIGHT)
               client.setGameOver(true);
           if (positionYHead <= -this.snake.getCELL_HEIGHT())
               client.setGameOver(true);
           if (positionXHead >= SCREEN_WIDTH)
               client.setGameOver(true);
           if (positionXHead <= - this.snake.getCELL_WIDTH())
               client.setGameOver(true);
            this.clients.forEach(client2 -> {
                this.clientSnakes.get(client2).getSnakeCells().forEach(cell -> {
                    if (positionXHead == cell.getPositionX() && positionYHead == cell.getPositionY() &&
                            this.clientSnakes.get(client).getSnakeCells().get(0) != cell) {
                        //tu bude vhodne skontrolovat ci narazil do hlavy protihraca a jeho direction aby sme vedeli
                        //kto bude zit a kto zomrie
                        client.setGameOver(true);
                    }
                });
            });
        });



      /*  for (int i = this.clients.size()-1; i > -1; i++) {
            int positionXHead = this.clientSnakes.get(clients.get(i)).getSnakeCells().get(0).getPositionX();
            int positionYHead = this.clientSnakes.get(clients.get(i)).getSnakeCells().get(0).getPositionY();
            this.clientSnakes.get(clients.get(i));
        }*/



//        int positionXHead = this.snake.getSnakeCells().get(0).getPositionX();
//        int positionYHead = this.snake.getSnakeCells().get(0).getPositionY();
//
//        if (positionYHead >= SCREEN_HEIGHT)
//            gameRunning = false;
//        if (positionYHead <= -this.snake.getCELL_HEIGHT())
//            gameRunning = false;
//        if (positionXHead >= SCREEN_WIDTH)
//            gameRunning = false;
//        if (positionXHead <= - this.snake.getCELL_WIDTH())
//            gameRunning = false;
//
//        Cell cellHead = this.snake.getSnakeCells().get(0);
//        boolean collisionWithYourself = this.snake.getSnakeCells().stream()
//                .anyMatch(cell -> {
//                    int positionX = cell.getPositionX();
//                    int positionY = cell.getPositionY();
//                    if (positionX == positionXHead && positionY == positionYHead &&
//                            cell != cellHead) {
//                        return true;
//                    }
//                    else return false;
//                });
//
//        if (collisionWithYourself)
//            gameRunning = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning && !this.clients.isEmpty() && !this.clientSnakes.isEmpty()) {
            this.clientSnakes.forEach((client,snake) -> {
                move();
                checkFoodCollision();
                checkCollisions();
                client.setDrawableGameObjects(this.drawableObjects);
            });

        }
    }

    public void addClient(ClientHandler client) {
        this.clients.add(client);
        this.clientSnakes.put(client,new Snake(100,300, SnakeDirection.RIGHT, 10,
                this.CELL_WIDTH, this.CELL_HEIGHT, Color.green, this.CELL_WIDTH));
        this.drawableObjects.addAll(this.clientSnakes.get(client).getSnakeCells());
    }
    public boolean getGameRunning() {return this.gameRunning;};

    public int getBodyParts() {
        return numberOfCells;
    }

    public int getScreenWidth() {
        return this.SCREEN_WIDTH;
    }
    public int getScreenHeight() {
        return this.SCREEN_HEIGHT;
    }
    public int getApplesEaten() {
        return foodEaten;
    }

    public boolean isRunning() {
        return gameRunning;
    }

    public void leftArrowPressed() {
        if (this.snake.getSnakeDirection() != SnakeDirection.RIGHT) {
            this.snake.setSnakeDirection(SnakeDirection.LEFT);
        }
    }
    public void rightArrowPressed() {
        if (this.snake.getSnakeDirection() != SnakeDirection.LEFT) {
            this.snake.setSnakeDirection(SnakeDirection.RIGHT);
        }
    }
    public void upArrowPressed() {
        if (this.snake.getSnakeDirection() != SnakeDirection.DOWN) {
            this.snake.setSnakeDirection(SnakeDirection.UP);
        }
    }
    public void downArrowPressed() {
        if (this.snake.getSnakeDirection() != SnakeDirection.UP) {
            this.snake.setSnakeDirection(SnakeDirection.DOWN);
        }
    }

    public ArrayList<DrawableGameObject> getDrawableObjects() {
        return this.drawableObjects;
    }
}
