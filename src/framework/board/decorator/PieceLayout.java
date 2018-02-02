package framework.board.decorator;

import framework.IMoveListener;
import framework.board.Grid;
import framework.board.PieceWrapper;
import framework.piece.Piece;
import javafx.scene.Group;
import reversi.ReversiPieceWrapper;

public abstract class PieceLayout {

    protected IMoveListener moveListener;

    public PieceLayout(){}

    public PieceLayout(IMoveListener listener){
        this.moveListener = listener;
    }

    protected Group layoutPieces(Grid grid){

        PieceWrapper wrapper = null;
        Piece[][] pieces = grid.getPieces();
        PieceWrapper[][] pieceWrappers = new PieceWrapper[grid.getRow()][grid.getColumn()];
        Group gridGroup = new Group();

        for (int i = 0; i < grid.getRow(); i++){
            for (int j = 0; j < grid.getColumn(); j++){
                wrapper = wrapPiece(pieces[i][j]);
                gridGroup.getChildren().add(wrapper);
                pieceWrappers[i][j] = wrapper;
            }
        }

        grid.setPiecesUI(pieceWrappers);

        return gridGroup;
    }

    protected abstract PieceWrapper wrapPiece(Piece piece);

}
