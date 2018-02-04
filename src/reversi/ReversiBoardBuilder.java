package reversi;

import UI.GameManager;
import framework.board.builder.GameBoardBuilder;
import framework.board.decorator.PieceLayout;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.gridDriver.bridge.CustomGridDriver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class ReversiBoardBuilder extends GameBoardBuilder {

    GameManager manager;
    GridCreatorVisitor visitor;
    public ReversiBoardBuilder(GameManager manager, PieceLayout pieceLayout,
                               CustomGridDriver customGridDriver, GridCreatorVisitor visitor) {
        super(new ReversiBoard(pieceLayout, customGridDriver));
        this.manager=manager;
        this.visitor = visitor;
    }

    @Override
    public void createToolBar() {
        HBox toolbar=new HBox();
        toolbar.setAlignment(Pos.CENTER);
        toolbar.setPadding(new Insets(10.0));
        Button btItem1 = createButtonItem("mSave", "Save Session", t->manager.saveSession());
        Button btItem2 = createButtonItem("mRestore", "Restore Session", t->manager.restoreSession());
        Button btItem3 = createButtonItem("mUndo", "Undo", t->manager.undo());
        Button btItem4 = createButtonItem("mReplay", "Try Again", t->manager.tryAgain());
        Button btItem5 = createButtonItem("mInfo", "About the Game", t->manager.aboutGame());
        toolbar.getChildren().setAll(btItem1, btItem2, btItem3, btItem4, btItem5);
        Button btItem6 = createButtonItem("mQuit", "Quit Game", t->manager.quitGame());
        toolbar.getChildren().add(btItem6);
        board.setToolBar(toolbar);
    }


    @Override
    public void createScore() {
        board.createScore();
    }

    @Override
    public void createBoard() {
        board.createGrid(visitor);
    }

    private Button createButtonItem(String symbol, String text, EventHandler<ActionEvent> t){
        Button g=new Button();
        g.setPrefSize(40, 40);
        g.setId(symbol);
        g.setOnAction(t);
        g.setTooltip(new Tooltip(text));
        return g;
    }
}
