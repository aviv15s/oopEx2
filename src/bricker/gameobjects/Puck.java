package bricker.gameobjects;

import bricker.main.BrickerGameMananger;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball{


    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound, BrickerGameMananger gameMananger) {
        super(topLeftCorner, dimensions, renderable, collisionSound, gameMananger);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }

    @Override
    public void onExitScreen() {
        super.onExitScreen();
        System.out.println("Puck Exited Screen");
    }
}
