package framework.gridDriver.strategy;

import framework.piece.GamePiece;
import framework.IMoveOutcome;
import framework.Move;
import framework.gridCreator.visitor.GridCreatorVisitor;

public class ReversiGridDriverStrategy extends CustomGridStrategy {

    public ReversiGridDriverStrategy(int x, int y){
        super(x,y);
    }

    @Override
    GamePiece[][] createGrid(int row, int column) {
        return new GamePiece[0][];
    }

    @Override
    boolean initializeGrid(GamePiece[][] grid) {
        return false;
    }

    @Override
    IMoveOutcome executeMove(Move move) {
        return null;
    }

    @Override
    public void accept(GridCreatorVisitor visitor) {
        visitor.visit(this);
    }
}
