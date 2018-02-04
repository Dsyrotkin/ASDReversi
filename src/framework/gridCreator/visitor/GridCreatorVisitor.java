package framework.gridCreator.visitor;

import framework.board.Grid;
import framework.gridDriver.bridge.CustomGridDriver;

public abstract class GridCreatorVisitor {

    public final void visit(CustomGridDriver gridStrategy){
        gridStrategy.setGrid(createGrid(gridStrategy));
    }

    abstract protected Grid createGrid(CustomGridDriver strategy);
}
