package bricker.brick_strategies;
import java.util.Random;
public class CollisionStrategyFactory {
    private final int RANDOM_MAX = 10;
    private final int MAX_DOUBLE_BEHAVIORS = 3;
    private final int BALLS_CREATOR = 0;
    private final int PADDLE_CREATOR = 1;
    private final int CHANGE_CAMERA = 2;
    private final int LIFE_RECOVERY = 3;
    private final int DOUBLE_BEHAVIOR = 4;
    public CollisionStrategyFactory() {
    }
    public CollsionStrategy[] collisionStrategy(){
        CollsionStrategy[] arrCollisionStrategy = new CollsionStrategy[MAX_DOUBLE_BEHAVIORS];
        Random rand = new Random();
        int randInt = rand.nextInt(RANDOM_MAX);
        if(randInt == DOUBLE_BEHAVIOR){
            setCaseDouble(rand, arrCollisionStrategy);
        }
        else{
            setCollisionStrategyIndex(randInt,0,arrCollisionStrategy);
        }
        return arrCollisionStrategy;
    }
    private void setCaseDouble(Random rand,CollsionStrategy[] arrCollisionStrategy){
        int randInt;
        int index = 0;
        int numToFill = 2;
        boolean flagTwiceDouble = false;
        while(numToFill>0){
            randInt = rand.nextInt();
            if(randInt == DOUBLE_BEHAVIOR && !flagTwiceDouble){
                flagTwiceDouble = true;
                numToFill+=1;
            }
            else if(randInt != DOUBLE_BEHAVIOR){
                setCollisionStrategyIndex(randInt,index,arrCollisionStrategy);
                index += 1;
                numToFill -= 1;
            }
        }
    }
    private void setCollisionStrategyIndex(int randInt,int index,CollsionStrategy[] arrCollisionStrategy){
        if(randInt == BALLS_CREATOR){
            arrCollisionStrategy[index] = new BallsCreatorCollisionStrategy();
        } else if(randInt == PADDLE_CREATOR){
            arrCollisionStrategy[index] = new PaddleCreatorCollisionStrategy();
        } else if(randInt == CHANGE_CAMERA){
            arrCollisionStrategy[index] = new ChangeCameraCollisionStrategy();
        } else if (randInt == LIFE_RECOVERY) {
            arrCollisionStrategy[index] = new LifeRecoveryCollisionStrategy();
        }
        else {
            arrCollisionStrategy[index] = new BasicCollisionStrategy();
        }

    }
    
}
