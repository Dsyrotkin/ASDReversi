package framework.board;

import framework.IMoveListener;
import framework.piece.Piece;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public abstract class PieceWrapper extends Group {

    protected Piece piece;

    public PieceWrapper(Piece piece, IMoveListener listener){
        this.piece = piece;
        setMoveListener(listener);
    }

    public void setMoveListener(IMoveListener listener){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listener.onMove(piece);
            }
        });
    }

    public Piece getPiece() {
        return piece;
    }
}
