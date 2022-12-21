package org.example.game;

public class GameController {
    private Snake snake;
    public GameController() {

    }

    public void playGame() {
         this.snake = new Snake(100,300, Direction.UP, 10, 10,10,
                "green");

    }

    public void moveSnake() {
        this.snake.move();
    }

}
