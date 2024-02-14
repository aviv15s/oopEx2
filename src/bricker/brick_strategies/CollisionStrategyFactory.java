package bricker.brick_strategies;
import bricker.main.BrickerGameManager;

import java.util.Random;

/**
 * This class generates Collision Strategies based on the probabilities given.
 * @author aviv.shemesh, ran3108_
 */
public class CollisionStrategyFactory {
    private final int RANDOM_MAX = 10;
    private final int MAX_DOUBLE_BEHAVIORS = 3;
    private final int BALLS_CREATOR = 0;
    private final int PADDLE_CREATOR = 1;
    private final int CHANGE_CAMERA = 2;
    private final int LIFE_RECOVERY = 3;
    private final int DOUBLE_BEHAVIOR = 4;

    /**
     * Basic constructor
     */
    public CollisionStrategyFactory() {
    }

    /**
     * This function generates a set of collision behaviours to be used by a brick object.
     * @param gameManager the game manager
     * @return an array of Collision Strategies, such that each cell is either a collision strategy or null.
     */
    public CollisionStrategy[] collisionStrategy(BrickerGameManager gameManager){
        CollisionStrategy[] arrCollisionStrategy = new CollisionStrategy[MAX_DOUBLE_BEHAVIORS];
        Random rand = new Random();
        int randInt = rand.nextInt(RANDOM_MAX);

        if(randInt == DOUBLE_BEHAVIOR){
            setCaseDouble(rand, arrCollisionStrategy,gameManager);
        }
        else{
            setCollisionStrategyIndex(randInt,0,arrCollisionStrategy, gameManager);
        }
        return arrCollisionStrategy;

    }

    /**
     * a helper function for the factory function
     * @param rand random int generated before
     * @param arrCollisionStrategy the array in which to fill
     * @param gameManager the game manager
     */
    private void setCaseDouble(Random rand,CollisionStrategy[] arrCollisionStrategy,
                               BrickerGameManager gameManager){
        int randInt;
        int index = 0;
        int numToFill = 2;
        boolean flagTwiceDouble = false;
        while(numToFill>0){
            randInt = rand.nextInt(RANDOM_MAX);
            if(randInt == DOUBLE_BEHAVIOR && !flagTwiceDouble){
                flagTwiceDouble = true;
                numToFill+=1;
            }
            else if(randInt != DOUBLE_BEHAVIOR){
                setCollisionStrategyIndex(randInt,index,arrCollisionStrategy,gameManager);
                index += 1;
                numToFill -= 1;
            }
        }
    }

    /**
     * a helper function for the factory function
     * @param randInt a random number generated before
     * @param index the index in the array in which to fill
     * @param arrCollisionStrategy the array to fill
     * @param gameManager the game manager
     */
    private void setCollisionStrategyIndex(int randInt,int index,CollisionStrategy[] arrCollisionStrategy,
                                           BrickerGameManager gameManager){
        switch (randInt){
            case BALLS_CREATOR:
                arrCollisionStrategy[index] = new BallsCreatorCollisionStrategy(gameManager);
                break;
            case PADDLE_CREATOR:
                arrCollisionStrategy[index] = new PaddleCreatorCollisionStrategy(gameManager);
                break;
            case CHANGE_CAMERA:
                arrCollisionStrategy[index] = new ChangeCameraCollisionStrategy(gameManager);
                break;
            case LIFE_RECOVERY:
                arrCollisionStrategy[index] = new LifeRecoveryCollisionStrategy(gameManager);
                break;
            default:
                arrCollisionStrategy[index] = new BasicCollisionStrategy(gameManager);
                break;
        }
    }
}
