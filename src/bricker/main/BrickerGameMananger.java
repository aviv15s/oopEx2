package src.bricker.main;

import danogl.GameManager;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.bricker.brick_strategies.BasicCollisionStrategy;
import src.bricker.brick_strategies.CollsionStrategy;
import src.bricker.gameobjects.Ball;
import src.bricker.gameobjects.Brick;
import src.bricker.gameobjects.GameWrapper;


// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BrickerGameMananger extends GameManager {
    private final int WALL_THICKNESS = 2;
    private int numberOfRows, bricksPerRow;
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
        Renderable ballImage = imageReader.readImage("assets/ball.png",
                true);

        GameObject ball = new Ball(Vector2.ZERO,
                new Vector2(50,50),
                ballImage);
        ball.setVelocity(Vector2.DOWN.mult(100));
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);

        //paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",
                true);
        GameObject paddle = new GameObject(Vector2.ZERO,
                new Vector2(200, 20),
                paddleImage);
        paddle.setCenter(new Vector2(windowDimensions.x() * 0.5f,
                windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);

        // initialize the walls colliders
        initializeWalls(windowDimensions);

        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameWrapper gameWrapper = new GameWrapper();
        gameWrapper.initializeBackground(gameObjects(), windowDimensions, backgroundImage);

        initializeBricks(imageReader, windowDimensions, numberOfRows, bricksPerRow);

        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.STATIC_OBJECTS, false);
        gameObjects().layers().shouldLayersCollide(Layer.STATIC_OBJECTS, Layer.DEFAULT, true);
    }

    private void initializeWalls(Vector2 windowDimensions){
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

    private void initializeBricks(ImageReader imageReader, Vector2 windowDimensions, int numberOfRows, int bricksPerRow){
        CollsionStrategy collsionStrategy = new BasicCollisionStrategy(this);
        Renderable brickImage = imageReader.readImage("assets/brick.png", true);

        int brickWidth = (int) windowDimensions.x() / bricksPerRow;
        int brickHeight = 15;

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j <bricksPerRow; j++) {
                Vector2 brickLocation = new Vector2(brickWidth * j, brickHeight * i);
                GameObject brick = new Brick(brickLocation,
                        new Vector2(brickWidth, brickHeight),
                        brickImage,
                        collsionStrategy);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }


    }


//    public void update(float deltaTime){
//        String s = "hi";
//    }
    public static void main(String[] args) {
        int numberOfRows = 7, bricksPerRow = 8;
        if (args.length >= 1){
            bricksPerRow = Integer.parseInt(args[0]);
        }
        if (args.length >= 2){
            numberOfRows = Integer.parseInt(args[1]);
        }
        BrickerGameMananger brickerGameManager = new BrickerGameMananger("Bouncing Ball",
                new Vector2(700,500), numberOfRows, bricksPerRow);
        brickerGameManager.run();
    }

    public boolean removeGameObject(GameObject gameObject, int layerId){
        return gameObjects().removeGameObject(gameObject, layerId);
    }
}