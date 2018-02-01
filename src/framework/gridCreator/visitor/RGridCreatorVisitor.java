package framework.gridCreator.visitor;

import framework.piece.GamePiece;
import framework.gridDriver.strategy.CustomGridStrategy;

public class RGridCreatorVisitor extends GridCreatorVisitor {
    @Override
    protected GamePiece[][] createGrid(CustomGridStrategy strategy) {
        //fixme create reversi pieces initialize some properties;
        return new GamePiece[0][];
    }
}
