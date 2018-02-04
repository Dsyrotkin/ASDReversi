package framework.piece;

import framework.IMoveOutcome;
import framework.gridDriver.bridge.CustomGridDriver;

public abstract class RuleStrategy {
    protected CustomGridDriver gridDriver;
    public RuleStrategy(CustomGridDriver driver){
        this.gridDriver = driver;
    }
    public abstract boolean validateMove(Move from, Move to);
    public abstract IMoveOutcome move(Move from, Move to);
}
