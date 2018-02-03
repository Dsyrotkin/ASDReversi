package framework.board.decorator;

import framework.board.Grid;
import javafx.scene.Group;
import javafx.scene.Parent;

public abstract class PieceLayoutDecorator extends PieceLayout {

    protected PieceLayout pieceLayout;
    protected Group gridContainer;

    public PieceLayoutDecorator(PieceLayout pieceLayout){
        this.pieceLayout = pieceLayout;
    }

    @Override
    public Group layoutPieces(Grid pieces) {
        gridContainer = pieceLayout.layoutPieces(pieces);
        return getGridContainer();
    }

    public void setGridContainer(Group gridContainer) {
        this.gridContainer = gridContainer;
    }

    public Group getGridContainer() {
        return gridContainer;
    }
}
