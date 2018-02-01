package framework.gridDriver.strategy;

import framework.piece.GamePiece;
import framework.Move;

public class GridDriver extends AbstractGridDriver {

    public GridDriver(CustomGridStrategy gridStrategy){
        this.customGridDriver = gridStrategy;
    }

    @Override
    public void executeMove(Move move) {

    }

    @Override
    public void initializeGame() {

    }

    @Override
    public GamePiece[][] createGrid(int row, int column) {
        return new GamePiece[0][];
    }
}
