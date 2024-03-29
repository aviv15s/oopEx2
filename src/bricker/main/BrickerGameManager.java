package bricker.main;

import bricker.gameobjects.*;
import danogl.GameManager;
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

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * BrickerGameManager is a class that allow us to play the game "Bricker".
 * This class extends GameManager and adds some functions to fit the interface for this game.
 * @author aviv.shemesh, ram3108_
 */
public class BrickerGameManager extends GameManager {
    private static final int DEFAULT_BRICKS_PER_ROW = 8;
    private static final int DEFAULT_NUMBER_OF_ROWS = 7;
    private final String PUCK_TAG = "puck";
    private final String MAIN_PADDLE_TAG = "main paddle";
    private final String ANOTHER_PADDLE_TAG = "another paddle";
    private final String MAIN_BALL_TAG = "main ball";
    private final String FALLING_HEART_TAG = "falling heart";
    private final int BALLS_LAYER = Layer.DEFAULT;
    private final int PADDLE_LAYER = 50;
    private final int WALLS_LAYER = Layer.STATIC_OBJECTS;
    private final int BRICKS_LAYER = Layer.STATIC_OBJECTS;
    private final int HEARTS_LAYER = -50;
    private final int WALL_THICKNESS = 2;
    private final int MAX_HITS_BEFORE_CAMERA = 4;
    private final float BALL_INITIAL_RELATIVE_TO_SCREEN = 0.5f;
    private final int BALL_SPEED = 250;
    private final Vector2 BALL_SIZE = new Vector2(20, 20);
    private final Vector2 PUCK_SIZE = BALL_SIZE.mult(0.75f);
    private final Vector2 PADDLE_SIZE = new Vector2(100, 15);
    private final int PADDLE_SPEED = 300;
    private final float FALLING_HEART_SPEED = 100;
    private final float FALLING_HEART_SIZE = 30;
    private final int MAX_HEARTS = 4;
    private final int INITIAL_HEARTS = 3;
    private final int MIN_NUM_HEARTS = 0;
    private final int MIN_NUM_BRICKS_FOR_WIN = 0;
    private int currentHearts = INITIAL_HEARTS;
    private Counter bricksRemaining;
    private int numberOfRows, bricksPerRow;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private ImageSoundFactory imageSoundFactory;
    private UserInputListener inputListener;
    private int ballCollisionCounter;
    private Ball mainBall;
    private boolean extraPaddle = false;
    private Graphics graphics;
    private UserInputListener userInputListener;
    private final float brickHeight = 15;

    /**
     * Constructor for BrickerGameManager class.
     * @param windowTitle - name of the window that will be opened.
     * @param windowDimensions - dimensions of the window that will be opened.
     * @param numberOfRows - num row of bricks.
     * @param bricksPerRow - num bricks in each row.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
                              int numberOfRows, int bricksPerRow) {
        super(windowTitle, windowDimensions);
        this.numberOfRows = numberOfRows;
        this.bricksPerRow = bricksPerRow;
    }

    /**
     * This function override the update function in GameManager and do several things.
     * 1.Cals the GameManager update. 2.Checks if there is a need to restart the camera view.
     * 3.Checks if heart is out of bound and cals function to handel. 4.Check if need to remove pucks and.
     * 5.Checks if User wants to exit Game.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */

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
                }
            }
        }

        // check if need to remove hearts
        for (GameObject gameObject: gameObjects().objectsInLayer(HEARTS_LAYER)){
            if (gameObject.getTag() == FALLING_HEART_TAG){
                if (gameObject.getTopLeftCorner().y() >= windowDimensions.y()){
                    gameObjects().removeGameObject(gameObject, HEARTS_LAYER);
                }
            }
        }

        // check if magic key "W" is pressed
        if (userInputListener != null &&
                userInputListener.wasKeyPressedThisFrame(KeyEvent.getExtendedKeyCodeForChar('w'))){
            boolean playAgain = graphics.showGameWonScreenAndReturnValue();
            restartGameOrExit(playAgain);
        }
    }

    /**
     * This method override the GameManager initialize.
     * Initial the game objects and several other important private variables for the class.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageSoundFactory = new ImageSoundFactory(imageReader, soundReader);
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.ballCollisionCounter = 0;
        this.windowController = windowController;
        this.userInputListener = inputListener;

        float widthFactor = 0.5f;
        float heightFactor = 30;
        //paddle
        Paddle mainPaddle = initializePaddle(new Vector2(
                windowDimensions.x() * widthFactor,
                windowDimensions.y() - heightFactor));
        mainPaddle.setTag(MAIN_PADDLE_TAG);


        // initialize the walls colliders
        initializeWalls();



        initializeBricks(numberOfRows, bricksPerRow);

        Ball ball = initializeBall();
        ball.setCenter(windowDimensions.mult(BALL_INITIAL_RELATIVE_TO_SCREEN));
        setObjectVelocityRandomDiagonal(ball, BALL_SPEED);
        mainBall = ball;

        gameObjects().layers().shouldLayersCollide(BRICKS_LAYER, BRICKS_LAYER, false);
        gameObjects().layers().shouldLayersCollide(BRICKS_LAYER, BALLS_LAYER, true);
        gameObjects().layers().shouldLayersCollide(BALLS_LAYER, PADDLE_LAYER, true);
        gameObjects().layers().shouldLayersCollide(HEARTS_LAYER, PADDLE_LAYER, true);

        // initialize graphics - hearts!
        graphics = new Graphics(windowController, imageSoundFactory, gameObjects());
        graphics.initializeLifeCounter(MAX_HEARTS, INITIAL_HEARTS);
        currentHearts = INITIAL_HEARTS;

        // initialize background
        graphics.initializeBackground(gameObjects(),
                windowDimensions, imageSoundFactory.getImageObject(ImageType.BACKGROUND));
    }

    /**
     * This method creates a Paddle object.
     * @param initialPlace -  the place to put the paddle.
     * @return paddle object.
     */
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

    /**
     * This method initial the walls using some constants of the class.
     */
    private void initializeWalls() {
        GameObject wallLeft = new GameObject(new Vector2(-WALL_THICKNESS, 0),
                new Vector2(WALL_THICKNESS, windowDimensions.y()),
                null);
        gameObjects().addGameObject(wallLeft,WALLS_LAYER);

        GameObject wallUp = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), WALL_THICKNESS),
                null);
        gameObjects().addGameObject(wallUp, WALLS_LAYER);

        GameObject wallRight = new GameObject(new Vector2(windowDimensions.x(), 0),
                new Vector2(WALL_THICKNESS, windowDimensions.x()),
                null);
        gameObjects().addGameObject(wallRight, WALLS_LAYER);
    }

    /**
     * Method to initial the bricks in the game, with amount in each axis.
     * @param numberOfRows - amount of row bricks.
     * @param bricksPerRow - amount of bricks in each row.
     */
    private void initializeBricks(int numberOfRows, int bricksPerRow) {
        bricksRemaining = new Counter(numberOfRows * bricksPerRow);
        Renderable brickImage = imageSoundFactory.getImageObject(ImageType.BRICK);
        int brickWidth = (int) windowDimensions.x() / bricksPerRow;

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < bricksPerRow; j++) {
                Vector2 brickLocation = new Vector2(brickWidth * j, brickHeight * i);
                GameObject brick = new Brick(brickLocation,
                        new Vector2(brickWidth, brickHeight),
                        brickImage,
                        this);
                gameObjects().addGameObject(brick, BRICKS_LAYER);
            }
        }
    }

    /**
     * Method that initial ball in game.
     * @return Ball created.
     */
    private Ball initializeBall() {
        Ball ball = new Ball(
                Vector2.ZERO,
                new Vector2(BALL_SIZE),
                imageSoundFactory.getImageObject(ImageType.BALL),
                imageSoundFactory.getSoundObject(SoundType.BLOP)
        );
        gameObjects().addGameObject(ball, BALLS_LAYER);
        ball.setTag(MAIN_BALL_TAG);
        return ball;
    }

    /**
     * Method that sets the given object velocity to random direction
     * (in positive circle side) with given speed.
     * @param object - object to change speed of.
     * @param speed - size of speed to change to.
     */
    private void setObjectVelocityRandomDirection(GameObject object, float speed) {
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * speed;
        float velocityY = (float) Math.sin(angle) * speed;
        Vector2 velocity = new Vector2(velocityX, velocityY);
        object.setVelocity(velocity);
    }

    /**
     * Method that sets the given object velocity to random direction (in diagonal) with given speed.
     * @param object - object to change speed of.
     * @param speed - size of speed to change to.
     */
    private void setObjectVelocityRandomDiagonal(GameObject object, float speed) {
        Random random = new Random();
        float speedOnEachAxis = speed / (float) Math.sqrt(2); // pythagoras!
        float velX = speedOnEachAxis, velY = speedOnEachAxis;
        if (random.nextBoolean()) {
            velX *= -1;
        }
        if (random.nextBoolean()) {
            velY *= -1;
        }
        object.setVelocity(new Vector2(velX, velY));
    }

    /**
     * Method called when ball left the screen.
     * Decrease heart and add ball if need to play the game.
     */
    private void doOnBallExitScreen(){
        decreaseHearts();
        mainBall.setCenter(windowDimensions.mult(BALL_INITIAL_RELATIVE_TO_SCREEN));
        setObjectVelocityRandomDiagonal(mainBall, BALL_SPEED);
    }

    /**
     * This method add heart to user if num hearts is less than MAX_HEARTS.
     */
    private void addHearts(){
        this.currentHearts++;
        if (this.currentHearts > MAX_HEARTS){
            this.currentHearts = MAX_HEARTS;
        }
        graphics.updateHeartCount(this.currentHearts);
    }

    /**
     * Method that create heart and put it on specic location.
     * @param center to put heart falling.
     */
    public void createFallingHeartObject(Vector2 center){
        Renderable heartImage = imageSoundFactory.getImageObject(ImageType.HEART);
        GameObject heartObject = new FallingHeart(
                Vector2.ZERO,
                Vector2.ONES.mult(FALLING_HEART_SIZE),
                heartImage,
                this);
        heartObject.setCenter(center);
        heartObject.setVelocity(Vector2.DOWN.mult(FALLING_HEART_SPEED));
        heartObject.setTag(FALLING_HEART_TAG);
        gameObjects().addGameObject(heartObject, HEARTS_LAYER);
    }

    /**
     * This Method decrease num of hearts and if life less-eq than
     * MIN_NUM_HEARTS, asks if to play again or quit.
     * Update the graphic accordingly.
     */
    private void decreaseHearts(){
        this.currentHearts--;
        if (this.currentHearts <= MIN_NUM_HEARTS){
            boolean playAgain = graphics.showGameOverScreenAndReturnValue();
            restartGameOrExit(playAgain);
        }
        graphics.updateHeartCount(this.currentHearts);
    }

    /**
     * Asks the user if wants to play again.
     * @param playAgain - true if wants another game, false otherwise.
     */
    private void restartGameOrExit(boolean playAgain){
        if (playAgain){
            windowController.resetGame();
        }
        else{
            windowController.closeWindow();
        }
    }

    /**
     * Destroy brick, and checks if num Bricks is less than MIN_NUM_BRICKS_FOR_WIN.
     * Callse
      * @param brick - to delete from game.
     */
    public void removeBrick(Brick brick) {
        gameObjects().removeGameObject(brick, BRICKS_LAYER);
        bricksRemaining.decrement();
        if (bricksRemaining.value() <= MIN_NUM_BRICKS_FOR_WIN) {
            restartGameOrExit(graphics.showGameWonScreenAndReturnValue());
        }
    }

    /**
     * method calles on object to follow with camera -
     * only does something to ball objects if not already focused on something.
     * @param gameObject - gameObject to follow via the camera.
     */
    public void cameraFollowBall(GameObject gameObject) {
        if (gameObject.getTag().equals(MAIN_BALL_TAG)) {
            Ball ball = (Ball) gameObject;
            if (this.camera() == null) {
                ballCollisionCounter = ball.getCollisionCounter();
                setCamera(new Camera(ball, Vector2.ZERO, getWindowDimensions().mult(1.2f),
                        getWindowDimensions()));
            }
        }
    }

    /**
     * Method that initialize  Puck.
     * @param center place center of Puck created.
     * @return puck created.
     */
    public Ball initializePuck(Vector2 center) {
        Ball puck = new Ball(
                Vector2.ZERO,
                new Vector2(PUCK_SIZE),
                imageSoundFactory.getImageObject(ImageType.PUCK),
                imageSoundFactory.getSoundObject(SoundType.BLOP)
        );
        puck.setCenter(center);
        setObjectVelocityRandomDirection(puck, BALL_SPEED);
        gameObjects().addGameObject(puck, BALLS_LAYER);
        puck.setTag(PUCK_TAG);
        return puck;
    }

    /**
     * Method that initialize extra Paddle if not exists one already.
     * @param initialPlace - place to put the paddle
     */
    public void addPaddle(Vector2 initialPlace) {
        if (!extraPaddle) {
            Paddle paddle = initializePaddle(initialPlace);
            paddle.setTag(ANOTHER_PADDLE_TAG);
            extraPaddle = true;
        }

    }

    /**
     * Remove game object.
     * @param gameObject - to remove
     * @param layerId layer from which to delete.
     * @return true if succeed else otherwise.
     */
    public boolean removeGameObject(GameObject gameObject, int layerId) {
        return gameObjects().removeGameObject(gameObject, layerId);
    }

    /**
     * When paddle hit heart delete heart and calls addHeart method.
     * @param heartObject - to remove
     */
    public void onPaddleHitHeart(GameObject heartObject) {
        gameObjects().removeGameObject(heartObject, HEARTS_LAYER);
        addHearts();
    }

    /**
     *main function that runs the Bricks game wia all his glory.
     * @param args argument given from the terminal.
     */
    public static void main(String[] args) {
        int bricksPerRow = DEFAULT_BRICKS_PER_ROW, numberOfRows = DEFAULT_NUMBER_OF_ROWS;
        if (args.length == 2) {
            bricksPerRow = Integer.parseInt(args[0]);
            numberOfRows = Integer.parseInt(args[1]);

        }
        String windowName = "Bricker";
        BrickerGameManager brickerGameManager = new BrickerGameManager(windowName,
                new Vector2(700, 500), numberOfRows, bricksPerRow);

        brickerGameManager.run();
    }

    /**
     * Get the PADDLE_LAYER.
     * @return PADDLE_LAYER
     */
    public int getPaddleLayer() {
        return PADDLE_LAYER;
    }

    /**
     * Set extraPaddle as b.
     * @param b - which value to change to
     */
    public void setExtraPaddle(boolean b) {
        extraPaddle = b;
    }

    /**
     * Get BRICKS_LAYER.
     * @return BRICKS_LAYER
     */
    public int getBricksLayer() {
        return BRICKS_LAYER;
    }

    /**
     * Get PADDLE_SPEED.
     * @return PADDLE_SPEED
     */
    public int getPaddleSpeed() {
        return PADDLE_SPEED;
    }

    /**
     * Get windowDimensions.
     * @return windowDimensions
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    /**
     * Get MAIN_BALL_TAG.
     * @return MAIN_BALL_TAG
     */
    public String getBallTag(){
        return MAIN_BALL_TAG;
    }

    /**
     * Get PUCK_TAG.
     * @return PUCK_TAG
     */
    public String getPuckTag(){
        return PUCK_TAG;
    }

    /** Get MAIN_PADDLE_TAG.
     * @return MAIN_PADDLE_TAG
     */
    public String getMainPaddleTag() {
        return MAIN_PADDLE_TAG;
    }

    /**
     * Get ANOTHER_PADDLE_TAG.
     * @return ANOTHER_PADDLE_TAG
     */
    public String getExtraPaddleTag() {
        return ANOTHER_PADDLE_TAG;
    }
}