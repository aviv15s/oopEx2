package bricker.main;

import bricker.gameobjects.*;
import danogl.GameManager;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollsionStrategy;

import java.awt.event.KeyEvent;
import java.util.Random;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BrickerGameManager extends GameManager {
    private final String PUCK_TAG = "puck";
    private final String MAIN_BALL_TAG = "main ball";
    private final int WALL_THICKNESS = 2;
    private final int MAX_HITS_BEFORE_CAMERA = 4;
    private final int BALL_SPEED = 250;
    private final Vector2 BALL_SIZE = new Vector2(50, 50);
    private final Vector2 PUCK_SIZE = BALL_SIZE.mult(0.75f);
    private final Vector2 PADDLE_SIZE = new Vector2(200, 20);
    private final int PADDLE_SPEED = 300;
    private final int BALLS_LAYER = Layer.DEFAULT;
    private final int PADDLE_LAYER = Layer.DEFAULT;
    private final int BRICKS_LAYER = Layer.STATIC_OBJECTS;
    private final String WALLS_TAG = "";
    private final int MAX_HEARTS = 4;
    private final int INITIAL_HEARTS = 3;
    private int currentHearts = INITIAL_HEARTS;
    private Counter bricksRemaining;
    private int numberOfRows, bricksPerRow;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private ImageSoundFactory imageSoundFactory;
    private UserInputListener inputListener;
    private int ballCollisionCounter;
    private Ball mainBall;
    public boolean extraPaddle = false;
    private Graphics graphics;
    private UserInputListener userInputListener;
    
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numberOfRows, int bricksPerRow) {
        super(windowTitle, windowDimensions);
        this.numberOfRows = numberOfRows;
        this.bricksPerRow = bricksPerRow;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ballCollisionCounter + MAX_HITS_BEFORE_CAMERA < mainBall.getCollisionCounter()) {
            this.setCamera(null);
        }

        // check if ball exited screen
        if (mainBall.getTopLeftCorner().y() > windowDimensions.y()){
            doOnBallExitScreen();
        }

        // check if need to remove pucks
        for (GameObject gameObject: gameObjects().objectsInLayer(BALLS_LAYER)){
            if (gameObject.getTag() == PUCK_TAG){
                if (gameObject.getTopLeftCorner().y() >= windowDimensions.y()){
                    gameObjects().removeGameObject(gameObject, BALLS_LAYER);
                    System.out.println("Deleted Puck!");
                }
            }
        }

        // check if magic key "W" is pressed
        if (userInputListener != null && userInputListener.wasKeyPressedThisFrame(KeyEvent.getExtendedKeyCodeForChar('w'))){
            boolean playAgain = graphics.showGameWonScreenAndReturnValue();
            restartGameOrExit(playAgain);
        }

    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageSoundFactory = new ImageSoundFactory(imageReader, soundReader);
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.ballCollisionCounter = 0;
        this.windowController = windowController;
        this.userInputListener = inputListener;
        //paddle
        Paddle paddle = initializePaddle(new Vector2(windowDimensions.x() * 0.5f, windowDimensions.y() - 30));


        // initialize the walls colliders
        initializeWalls();

        GameWrapper gameWrapper = new GameWrapper();
        gameWrapper.initializeBackground(gameObjects(), windowDimensions, imageSoundFactory.getImageObject(ImageType.BACKGROUND));

        initializeBricks(numberOfRows, bricksPerRow);

        Ball ball = initializeBall();
        ball.setCenter(windowDimensions.mult(0.5f));
        setObjectVelocityRandomDiagonal(ball, BALL_SPEED);
        mainBall = ball;

        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.STATIC_OBJECTS, false);
        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.DEFAULT, true);

        // initialize graphics - hearts!
        graphics = new Graphics(windowController, imageSoundFactory, gameObjects());
        graphics.initializeLifeCounter(MAX_HEARTS, INITIAL_HEARTS);

        currentHearts = INITIAL_HEARTS;
    }
    
    private Paddle initializePaddle(Vector2 initialPlace) {
        Renderable paddleImage = imageSoundFactory.getImageObject(ImageType.PADDLE);
        Paddle paddle = new Paddle(
                initialPlace,
                new Vector2(PADDLE_SIZE),
                paddleImage,
                inputListener,
                this);
        gameObjects().addGameObject(paddle, PADDLE_LAYER);
        return paddle;
    }
    
    private void initializeWalls() {
        GameObject wallLeft = new GameObject(new Vector2(-WALL_THICKNESS, 0),
                new Vector2(WALL_THICKNESS, windowDimensions.y()),
                null);
        gameObjects().addGameObject(wallLeft, Layer.STATIC_OBJECTS);

        GameObject wallUp = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), WALL_THICKNESS),
                null);
        gameObjects().addGameObject(wallUp, Layer.STATIC_OBJECTS);

        GameObject wallRight = new GameObject(new Vector2(windowDimensions.x(), 0),
                new Vector2(WALL_THICKNESS, windowDimensions.x()),
                null);
        gameObjects().addGameObject(wallRight, Layer.STATIC_OBJECTS);
    }

    private void initializeBricks(int numberOfRows, int bricksPerRow) {
        bricksRemaining = new Counter(numberOfRows * bricksPerRow);
        CollsionStrategy collsionStrategy = new BasicCollisionStrategy(this);
        Renderable brickImage = imageSoundFactory.getImageObject(ImageType.BRICK);

        int brickWidth = (int) windowDimensions.x() / bricksPerRow;
        int brickHeight = 15;

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < bricksPerRow; j++) {
                Vector2 brickLocation = new Vector2(brickWidth * j, brickHeight * i);
                GameObject brick = new Brick(brickLocation,
                        new Vector2(brickWidth, brickHeight),
                        brickImage,
                        collsionStrategy, this);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private Ball initializeBall() {
        Ball ball = new Ball(
                Vector2.ZERO,
                new Vector2(BALL_SIZE),
                imageSoundFactory.getImageObject(ImageType.BALL),
                imageSoundFactory.getSoundObject(SoundType.BLOP),
                this
        );
        gameObjects().addGameObject(ball, BALLS_LAYER);
        ball.setTag(MAIN_BALL_TAG);
        return ball;
    }

    private void setObjectVelocityRandomDirection(GameObject object, float speed) {
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * speed;
        float velocityY = (float) Math.sin(angle) * speed;
        Vector2 velocity = new Vector2(velocityX, velocityY);
        object.setVelocity(velocity);
    }

    private void setObjectVelocityRandomDiagonal(GameObject object, float speed) {
        Random random = new Random();
        float speedOnEachAxis = speed / (float) Math.sqrt(2);
        float velX = speedOnEachAxis, velY = speedOnEachAxis;
        if (random.nextBoolean()) {
            velX *= -1;
        }
        if (random.nextBoolean()) {
            velY *= -1;
        }
        object.setVelocity(new Vector2(velX, velY));
    }

    private void doOnBallExitScreen(){
        currentHearts--;
        if (currentHearts == 0){
            boolean playAgain = graphics.showGameOverScreenAndReturnValue();
            restartGameOrExit(playAgain);
        }
        graphics.updateHeartCount(currentHearts);
        mainBall.setCenter(windowDimensions.mult(0.5f));
        setObjectVelocityRandomDiagonal(mainBall, BALL_SPEED);
    }

    private void restartGameOrExit(boolean playAgain){
        if (playAgain){
            windowController.resetGame();
        }
        else{
            windowController.closeWindow();
        }
    }

    
    public void removeBrick(Brick brick) {
        gameObjects().removeGameObject(brick, BRICKS_LAYER);
        bricksRemaining.decrement();
        if (bricksRemaining.value() <= 0) {
            boolean playAgain = graphics.showGameWonScreenAndReturnValue();
            if (playAgain){
                windowController.resetGame();
            }
            else{
                windowController.closeWindow();
            }
        }
    }

    public void cameraFollowBall(GameObject gameObject) {
        if (gameObject.getTag().equals(MAIN_BALL_TAG)) {
            Ball ball = (Ball) gameObject;
            if (this.camera() == null) {
                ballCollisionCounter = ball.getCollisionCounter();
                setCamera(new Camera(ball, Vector2.ZERO, getWindowDimensions().mult(1.2f), getWindowDimensions()));
            }
        }
    }
    
    public Ball initializePuck(Vector2 center) {
        Ball puck = new Ball(
                Vector2.ZERO,
                new Vector2(PUCK_SIZE),
                imageSoundFactory.getImageObject(ImageType.PUCK),
                imageSoundFactory.getSoundObject(SoundType.BLOP),
                this
        );
        puck.setCenter(center);
        setObjectVelocityRandomDirection(puck, BALL_SPEED);
        gameObjects().addGameObject(puck, BALLS_LAYER);
        puck.setTag(PUCK_TAG);
        return puck;
    }

    public void addPaddle(Vector2 initialPlace) {
        if (!extraPaddle) {
            initializePaddle(initialPlace).setLabelAnotherPaddle();
            extraPaddle = true;
        }

    }

    public boolean removeGameObject(GameObject gameObject, int layerId) {
        return gameObjects().removeGameObject(gameObject, layerId);
    }

    public static void main(String[] args) {
        int numberOfRows = 7, bricksPerRow = 8;
        if (args.length >= 1) {
            bricksPerRow = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            numberOfRows = Integer.parseInt(args[1]);
        }
        BrickerGameManager brickerGameManager = new BrickerGameManager("Bouncing Ball",
                new Vector2(700, 500), numberOfRows, bricksPerRow);
        brickerGameManager.run();
    }

    public int getPaddleLayer() {
        return PADDLE_LAYER;
    }

    public void setExtraPaddle(boolean b) {
        extraPaddle = b;
    }

    public int getBricksLayer() {
        return BRICKS_LAYER;
    }

    public int getPaddleSpeed() {
        return PADDLE_SPEED;
    }

    public String getWallsTag() {
        return WALLS_TAG;
    }
    
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }
}