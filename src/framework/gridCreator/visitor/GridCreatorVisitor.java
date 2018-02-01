package framework.gridCreator.visitor;

import framework.piece.GamePiece;
import framework.gridDriver.strategy.CustomGridStrategy;

public abstract class GridCreatorVisitor {

    public final void visit(CustomGridStrategy gridStrategy){
        gridStrategy.setGrid(createGrid(gridStrategy));
    }

    abstract protected GamePiece[][] createGrid(CustomGridStrategy strategy);
}
