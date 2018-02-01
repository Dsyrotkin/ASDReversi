package framework.gridDriver.strategy;

import framework.piece.GamePiece;
import framework.Move;

public abstract class AbstractGridDriver {
    protected CustomGridStrategy customGridDriver;
    public abstract void executeMove(Move move);
    public abstract void initializeGame();
    public abstract GamePiece[][] createGrid(int row, int column);
}