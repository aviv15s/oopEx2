package bricker.main;

import danogl.GameManager;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BrickerGameMananger extends GameManager {
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
        Renderable ballImage = imageReader.readImage("",true);

        GameObject ball = new Ball(Vector2.ZERO,
                new Vector2(50,50),
                ballImage);
        ball.setVelocity(Vector2.DOWN.mult(100));
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);
        //paddle
        Renderable paddleImage = imageReader.readImage("",
                true);
        GameObject paddle = new GameObject(Vector2.ZERO,
                new Vector2(200,20),
                paddleImage);
        paddle.setCenter(new Vector2(windowDimensions.x()*0.5f,
                windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);
        //wall
        GameObject wallLeft = new GameObject(Vector2.ZERO,
                new Vector2(2,windowDimensions.y()),
                null);
        gameObjects().addGameObject(wallLeft);
        GameObject wallup = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(),2),
                null);
        gameObjects().addGameObject(wallup);
        GameObject walllright = new GameObject(windowDimensions,
                new Vector2(windowDimensions.x(),2),
                null);
        gameObjects().addGameObject(wallright);
    }

    public void update(float deltaTime){
        String s = "hi";
    }
    public static void main(String[] args) {
        BrickerGameMananger brickerGameManager = new BrickerGameMananger("Bouncing Ball",
                new Vector2(700,500));
        brickerGameManager.run();
    }
}