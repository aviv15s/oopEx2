package bricker.main;

import bricker.gameobjects.Paddle;
import danogl.GameManager;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;

import java.util.Random;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BrickerGameMananger extends GameManager {
    private final int WALL_THICKNESS = 2;
    private final int BALL_SPEED = 250;
    public BrickerGameMananger() {
    }

    public BrickerGameMananger(String windowTitle) {
        super(windowTitle);
    }

    public BrickerGameMananger(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Renderable ballImage = imageReader.readImage("assets/ball.png",
                true);

        Sound collsiomSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        GameObject ball = new Ball(Vector2.ZERO,
                new Vector2(50,50),
                ballImage, collsiomSound);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()){
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);

        //paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",
                true);
        GameObject paddle = new Paddle(Vector2.ZERO,
                new Vector2(200, 20),
                paddleImage, inputListener);
        paddle.setCenter(new Vector2(windowDimensions.x() * 0.5f,
                windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);

        // initialize the walls colliders
        initializeWalls(windowDimensions);

        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        initializeBackground(windowDimensions, backgroundImage);
    }

    private void initializeWalls(Vector2 windowDimensions){
        GameObject wallLeft = new GameObject(new Vector2(-WALL_THICKNESS, 0),
                new Vector2(WALL_THICKNESS, windowDimensions.y()),
                null);
        gameObjects().addGameObject(wallLeft);

        GameObject wallUp = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), WALL_THICKNESS),
                null);
        gameObjects().addGameObject(wallUp);

        GameObject wallRight = new GameObject(new Vector2(windowDimensions.x(), 0),
                new Vector2(WALL_THICKNESS, windowDimensions.x()),
                null);
        gameObjects().addGameObject(wallRight);
    }

    private void initializeBackground(Vector2 windowDimensions, Renderable backgroundImage){
        GameObject background = new GameObject(Vector2.ZERO,
                windowDimensions,
                backgroundImage);
        gameObjects().addGameObject(background, -200);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

//    public void update(float deltaTime){
//        String s = "hi";
//    }
    public static void main(String[] args) {
        BrickerGameMananger brickerGameManager = new BrickerGameMananger("Bouncing Ball",
                new Vector2(700,500));
        brickerGameManager.run();
    }
}