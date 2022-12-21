package org.example.game;


public abstract class GameLoop {

    //protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected volatile GameStatus status;

    protected GameController controller;

    private Thread gameThread;

    public GameLoop() {
        controller = new GameController();
        status = GameStatus.STOPPED;
    }

    public void run() {
        status = GameStatus.RUNNING;
        gameThread = new Thread(this::processGameLoop);
        gameThread.start();
    }

    public void stop() {
        status = GameStatus.STOPPED;
    }

    public boolean isGameRunning() {
        return status == GameStatus.RUNNING;
    }

    protected void processInput() {
        try {
            //var lag = new Random().nextInt() + 50;
            var lag = 80;
            Thread.sleep(lag);
        } catch (InterruptedException e) {
            //logger.error(e.getMessage());
        }
    }

    protected void render() {
        //var position = controller.getBulletPosition();
        //logger.info("Current bullet position: " + position);
    }

    protected abstract void processGameLoop();
}
