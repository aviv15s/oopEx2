package bricker.main;

import bricker.gameobjects.*;
import danogl.GameManager;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import danogl.GameObject;
import danogl.gui.*;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollsionStrategy;

import java.util.Random;
import java.util.Vector;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BrickerGameMananger extends GameManager {
    private int bricksRemaining;
    private final int WALL_THICKNESS = 2;
    private final int BALL_SPEED = 250;
    private final Vector2 BALL_SIZE = new Vector2(50, 50);
    private final Vector2 PUCK_SIZE = BALL_SIZE.mult(0.75f);
    private final Vector2 PADDLE_SIZE = new Vector2(200,20);
    private int numberOfRows, bricksPerRow;
    public final int BRICKS_LAYER = Layer.STATIC_OBJECTS;
    private Vector2 windowDimensions;
    private ImageSoundFactory imageSoundFactory;

    public BrickerGameMananger() {
    }

    public BrickerGameMananger(String windowTitle) {
        super(windowTitle);
    }

    public BrickerGameMananger(String windowTitle, Vector2 windowDimensions, int numberOfRows, int bricksPerRow) {
        super(windowTitle, windowDimensions);
        this.numberOfRows = numberOfRows;
        this.bricksPerRow = bricksPerRow;
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.imageSoundFactory = new ImageSoundFactory(imageReader, soundReader);
        this.windowDimensions = windowController.getWindowDimensions();

        //paddle
        Paddle paddle = initializePaddle(inputListener);
        paddle.setCenter(new Vector2(
                windowDimensions.x() * 0.5f,
                windowDimensions.y() - 30)
        );

        // initialize the walls colliders
        initializeWalls();

        GameWrapper gameWrapper = new GameWrapper();
        gameWrapper.initializeBackground(gameObjects(), windowDimensions, imageSoundFactory.getImageObject(ImageType.BACKGROUND));

        initializeBricks(numberOfRows, bricksPerRow);

        Ball ball = initializeBall();
        ball.setCenter(windowDimensions.mult(0.5f));
        setObjectVelocityRandomDirection(ball, BALL_SPEED);

        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.STATIC_OBJECTS, false);
        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.DEFAULT, true);
    }

    private void setObjectVelocityRandomDirection(GameObject object, float speed){
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * speed;
        float velocityY = (float) Math.sin(angle) * speed;
        Vector2 velocity = new Vector2(velocityX, velocityY);
        object.setVelocity(velocity);
    }

    private void setObjectVelocityRandomDiagonal(GameObject object, float speed){
        Random random = new Random();
        float speedOnEachAxis = speed / (float) Math.sqrt(2);
        float velX = speedOnEachAxis, velY = speedOnEachAxis;
        if (random.nextBoolean()){
            velX *= -1;
        }
        if (random.nextBoolean()){
            velY *= -1;
        }
        object.setVelocity(new Vector2(velX, velY));
    }

    private Paddle initializePaddle(UserInputListener inputListener){
        Renderable paddleImage = imageSoundFactory.getImageObject(ImageType.PADDLE);
        Paddle paddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_SIZE),
                paddleImage,
                inputListener,
                this);
        gameObjects().addGameObject(paddle);
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
        bricksRemaining = numberOfRows * bricksPerRow;
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
                        collsionStrategy,this);
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
        gameObjects().addGameObject(ball);
        return ball;
    }

    public Puck initializePuck(Vector2 center) {
        Puck puck = new Puck(
                Vector2.ZERO,
                new Vector2(PUCK_SIZE),
                imageSoundFactory.getImageObject(ImageType.PUCK),
                imageSoundFactory.getSoundObject(SoundType.BLOP),
                this
        );
        puck.setCenter(center);
        setObjectVelocityRandomDirection(puck,BALL_SPEED);
        gameObjects().addGameObject(puck);
        return puck;
    }

    public void onBrickRemoved(){
        bricksRemaining--;
        if (bricksRemaining <= 0){
            // TODO do something because the player WON!
            System.out.println("Player WINSSSSSS");
        }
    }


    public static void main(String[] args) {
        int numberOfRows = 7, bricksPerRow = 8;
        if (args.length >= 1) {
            bricksPerRow = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            numberOfRows = Integer.parseInt(args[1]);
        }
        BrickerGameMananger brickerGameManager = new BrickerGameMananger("Bouncing Ball",
                new Vector2(700, 500), numberOfRows, bricksPerRow);
        brickerGameManager.run();
    }

    public boolean removeGameObject(GameObject gameObject, int layerId) {
        return gameObjects().removeGameObject(gameObject, layerId);
    }

    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }
}