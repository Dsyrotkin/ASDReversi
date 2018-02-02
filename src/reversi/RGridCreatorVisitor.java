package reversi;

import framework.board.Grid;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.gridDriver.bridge.CustomGridDriver;
import framework.piece.Piece;
import framework.piece.RuleStrategy;

public class RGridCreatorVisitor extends GridCreatorVisitor {

    @Override
    protected Grid createGrid(CustomGridDriver strategy) {

        Piece[][] reversiPiece = new Piece[strategy.getRow()][strategy.getColumn()];

        RuleStrategy rule = new ReversiPieceRule(strategy);

        for (int i = 0; i < strategy.getRow(); i++){
            for (int j = 0; j < strategy.getColumn(); j++){
                reversiPiece[i][j] = new ReversiPiece(rule, i, j);
            }
        }

        Grid grid = new ReversiGrid(reversiPiece);

        return grid;
    }

}
