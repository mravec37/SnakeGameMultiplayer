package org.example.game;

import org.example.graphics_objects.*;
import org.example.multiplayer.ClientHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class SnakeGame {

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int UNIT_SIZE = 20;
    private int numberOfCells = 5;
    private int foodEaten = 0;
    private boolean gameRunning;
    private Random random;
    private Snake snake;
    private Food food;
    private ArrayList<DrawableGameObject> drawableObjects;
    private ArrayList<ClientHandler> clients;
    private HashMap<ClientHandler, Snake> clientSnakes;
    private final int CELL_WIDTH = 20;
    private final int CELL_HEIGHT = 20;
    private final int NUMBER_OF_CELLS_AT_START;
    private final int NUMBER_OF_FOOD_ON_BOARD = 7;
    private ClientArrowKeyPressed clientArrowKeyPressed;
    private ArrayList<Food> snakeFood;
    public SnakeGame(int screenWidth, int screenHeight) {
        this.SCREEN_HEIGHT = screenHeight;
        this.snakeFood = new ArrayList<>();
        this.SCREEN_WIDTH = screenWidth;
        this.drawableObjects = new ArrayList<DrawableGameObject>();
        this.clientSnakes = new HashMap<>();
        this.NUMBER_OF_CELLS_AT_START = 5;
        this.gameRunning = false;
        this.random = new Random();
        this.clients = new ArrayList<>();

    }
    public void startGame() {
        this.setUpFood();
        this.gameRunning = true;
    }

    private void setUpFood() {
        for (int i = 0; i < this.NUMBER_OF_FOOD_ON_BOARD; i++) {
            Food newFood = this.newFood();
            this.snakeFood.add(newFood);
            this.drawableObjects.add(newFood);
        }
    }
    public Food newFood() {
        boolean valid = false;
        int foodY = 0;
        int foodX = 0;
        while(!valid) {
             foodX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
             foodY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
             valid = this.checkValidCoordinates(foodX, foodY).get();
        }
        return new Food(foodX, foodY, this.CELL_WIDTH,this.CELL_HEIGHT, Color.red, new Circle());

    }

    private AtomicBoolean checkValidCoordinates(int posX, int posY) {
        AtomicBoolean valid = new AtomicBoolean(true);
        this.drawableObjects.forEach(drawableObject -> {
            if (drawableObject.getX() == posX && drawableObject.getY() == posY)
                valid.set(false);
        });
        return valid;
    }

    public void newFood(Food snakeFood) {
        int foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        int foodY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        this.drawableObjects.remove(snakeFood);
        this.snakeFood.remove(snakeFood);
        snakeFood = new Food(foodX, foodY, this.CELL_WIDTH,this.CELL_HEIGHT, Color.red, new Circle());
        this.drawableObjects.add(snakeFood);
        this.snakeFood.add(snakeFood);
    }

    public void checkFoodCollision() {

        this.clients.forEach(client ->  {
            int positionXHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionX();
            int positionYHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionY();
            this.checkCellCollisionWithFood(positionXHead, positionYHead, client);
        });
    }
    private void checkCellCollisionWithFood(int positionX, int positionY, ClientHandler client) {
        ArrayList<Food>foodToRemove = new ArrayList<>();
        this.snakeFood.forEach(food -> {
            if (positionX >= food.getPositionX() -15 && positionX <=  food.getPositionX() + 15  && positionY >=
                    food.getPositionY() - 15 && positionY <= food.getPositionY() + 15) {
                client.setClientScore(client.getClientScore() + 1);
                System.out.println("Collision with food");
                this.drawableObjects.add(clientSnakes.get(client).addCellToTail());
                foodToRemove.add(food);
            }
        });
        foodToRemove.forEach(this::newFood);

    }
    public void checkCollisions() {

    ArrayList<ClientHandler> clientsToRemove = new ArrayList<>();

      this.clients.forEach(client -> {
            int positionXHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionX();
            int positionYHead = this.clientSnakes.get(client).getSnakeCells().get(0).getPositionY();
            String head = this.clientSnakes.get(client).getSnakeCells().get(0).toString();

          if (positionYHead >= SCREEN_HEIGHT) {
              clientsToRemove.add(client);
              System.out.println("Game over: Screen Height");
          }

          if (positionYHead <= -20) {
              clientsToRemove.add(client);
              System.out.println("Game over: Screen Height");
          }
          if (positionXHead >= SCREEN_WIDTH) {
              clientsToRemove.add(client);
              System.out.println("Game over: Screen Height");
          }
          if (positionXHead <= - 20) {
              System.out.println("Game over: Screen Height");
              clientsToRemove.add(client);
          }

            this.clients.forEach(client2 -> {
                this.clientSnakes.get(client2).getSnakeCells().forEach(cell -> {
                    if (positionXHead == cell.getPositionX() && positionYHead == cell.getPositionY() &&
                            this.clientSnakes.get(client).getSnakeCells().get(0) != cell) {
                        //tu bude vhodne skontrolovat ci narazil do hlavy protihraca a jeho direction aby sme vedeli
                        //kto bude zit a kto zomrie
                        clientsToRemove.add(client);
                        System.out.println("game over: snake collision: headX: " + positionXHead + " headY:" +
                                "cell X: " + cell.getPositionX() + " cell Y: " + cell.getPositionY() + " head: " +
                                head + " cell: " + cell.toString());
                    }
                });
            });
        });
      clientsToRemove.forEach(client -> {
          this.drawableObjects.removeAll(this.clientSnakes.get(client).getSnakeCells());
          this.clients.remove(client);
          client.setGameRunning(false);
          this.clientSnakes.remove(client);
      });

    }

    public void runGame() {
        if (gameRunning && !this.clients.isEmpty() && !this.clientSnakes.isEmpty()) {
            this.clientSnakes.forEach((client, snake) -> {
                this.setSnakeDirection(snake, client.getClientArrowKeyPressed());
                snake.move();
            });
            checkCollisions();
            checkFoodCollision();
            this.clients.forEach(client -> {
                client.setGameRunning(true);
                client.setSnakeHeadX(clientSnakes.get(client).getSnakeCells().get(0).getPositionX());
                client.setSnakeHeadY(clientSnakes.get(client).getSnakeCells().get(0).getPositionY());
                client.setSnakeDirection(clientSnakes.get(client).getSnakeDirection());
            });
        }
    }

    private void setSnakeDirection(Snake snake, ClientArrowKeyPressed arrowKeyPressed) {
        switch (arrowKeyPressed) {
            case UP_ARROW -> {
                if (snake.getSnakeDirection() != SnakeDirection.DOWN)
                    snake.setSnakeDirection(SnakeDirection.UP);
            }
            case LEFT_ARROW -> {
                if (snake.getSnakeDirection() != SnakeDirection.RIGHT)
                    snake.setSnakeDirection(SnakeDirection.LEFT);
            }
            case DOWN_ARROW -> {
                if (snake.getSnakeDirection() != SnakeDirection.UP)
                    snake.setSnakeDirection(SnakeDirection.DOWN);
            }
            case RIGHT_ARROW -> {
                if (snake.getSnakeDirection() != SnakeDirection.LEFT)
                    snake.setSnakeDirection(SnakeDirection.RIGHT);
            }
        }
    }

    public void addClient(ClientHandler client) {
        this.clients.add(client);
        SnakeDirection direction = SnakeDirection.RIGHT;
        int positionX = 150;
        int positionY = 200;
        if (client.getClientId() == 1) {
            positionX = 1520;
            direction = SnakeDirection.LEFT;
        }
        else if (client.getClientId() == 2) {
            positionY = 700;
            direction = SnakeDirection.RIGHT;
        }
        else if (client.getClientId() == 3) {
            positionX = 1520;
            positionY = 700;
            direction = SnakeDirection.LEFT;
        }
        Color colorSnake = new Color(random.nextInt(255),random.nextInt(255),
                random.nextInt(255));
        this.clientSnakes.put(client,new Snake(positionX, positionY, direction, this.NUMBER_OF_CELLS_AT_START,
                this.CELL_WIDTH, this.CELL_HEIGHT, colorSnake, this.CELL_WIDTH));
        this.drawableObjects.addAll(this.clientSnakes.get(client).getSnakeCells());
        client.setDrawableGameObjects(this.drawableObjects);
    }
}
