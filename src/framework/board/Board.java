package framework.board;

import framework.board.decorator.PieceLayout;
import framework.board.decorator.PieceLayoutDecorator;
import framework.board.decorator.PieceLayoutDecoratorImpl;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.gridDriver.bridge.CustomGridDriver;
import framework.gridDriver.bridge.GridDriver;
import framework.gridDriver.bridge.GridDriverImpl;
import framework.piece.Move;
import javafx.scene.Group;

public abstract class Board extends Group implements GameBoard {

    protected PieceLayoutDecorator pieceLayoutDecorator;

    protected GridDriver gridDriver;

    public Board(PieceLayout pieceLayout, CustomGridDriver customGridDriver){
        this.pieceLayoutDecorator = new PieceLayoutDecoratorImpl(pieceLayout);
        this.gridDriver = new GridDriverImpl(customGridDriver);
    }

    public abstract void doMove(Move piece);

    public abstract void createGrid(GridCreatorVisitor visitor);

    public abstract void init();
}
