package src.bricker.brick_strategies;

import danogl.GameObject;

public interface CollsionStrategy {
    public void onCollision(GameObject gameObject1, GameObject gameObject2);
}
