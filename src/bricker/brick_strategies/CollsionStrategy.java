package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;

public interface CollsionStrategy {
    public void onCollision(GameObject gameObject1, GameObject gameObject2);
}
