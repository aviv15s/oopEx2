package bricker.gameobjects;
import bricker.main.BrickerGameMananger;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;
public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter = 0;
    private BrickerGameMananger gameMananger;

    public Ball(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound, BrickerGameMananger gameMananger) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.gameMananger = gameMananger;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter++;
    }

    public int getCollisionCounter(){
        return collisionCounter;
    }

    public void onExitScreen(){
        System.out.println("Ball Exited Screen!");
        gameMananger.removeGameObject(this, Layer.DEFAULT);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getTopLeftCorner().y() > gameMananger.getWindowDimensions().y()){
            onExitScreen();
        }
    }
}
