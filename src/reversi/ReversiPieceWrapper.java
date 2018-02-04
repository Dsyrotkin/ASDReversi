package reversi;

import framework.IMoveListener;
import framework.board.PieceWrapper;
import framework.piece.Move;
import framework.piece.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class ReversiPieceWrapper extends PieceWrapper {

    private Ellipse ellipse; // ellipse representing the player's ellipse

    private Translate t; // translation for the player ellipse

    private Rectangle cell;

    public static final int CELL_SIZE = 64;

    public ReversiPieceWrapper(Piece piece, IMoveListener listener) {

        super(piece, listener);
        createEclipse();
        clearPiece();
        getChildren().add(createCell());
        getChildren().add(ellipse);

    }

    private void createEclipse(){

        t = new Translate();

        ellipse = new Ellipse();

        resize(CELL_SIZE,CELL_SIZE);

        ellipse.getTransforms().add(t);

    }
    private Rectangle createCell(){

        final double arcSize = CELL_SIZE / 6d;

        cell = new Rectangle(piece.getColumn() * CELL_SIZE, piece.getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // provide default style in case css are not loaded
        cell.setFill(Color.WHITE);

        cell.setStroke(Color.GREY);

        cell.setArcHeight(arcSize);

        cell.setArcWidth(arcSize);

        cell.getStyleClass().add("game-grid-cell");

        return cell;
    }

    // overridden version of the resize method to give the ellipse the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        double r=width / 2-4;
        ellipse.setCenterX(piece.getColumn() * width+width/2);
        ellipse.setCenterY(piece.getRow() * width+width/2);

        ellipse.setRadiusX(r);
        ellipse.setRadiusY(r);


    }

    // method that will set the piece type
    public void decoratePiece(final Move move) {

        int playerId = move.getCurrentPlayer().getId();
        setPiece(move.getPiece());
        piece.setPlayerId(move.getCurrentPlayer().getId());

        switch(playerId) {
            case 1:
                ellipse.setFill(Color.WHITE);
                break;
            case 2:
                ellipse.setFill(Color.BLACK);
                break;
        }

    }

    // method that will set the piece type
    public void decoratePiece(final Piece piece) {

        int playerId = piece.getPlayerId();
        setPiece(piece);
        piece.setPlayerId(playerId);

        switch(playerId) {
            case 1:
                ellipse.setFill(Color.WHITE);
                break;
            case 2:
                ellipse.setFill(Color.BLACK);
                break;
        }

    }

    // method that will set the piece type
    public void clearPiece() {

        piece.setPlayerId(0);

        ellipse.setFill(Color.TRANSPARENT);

    }

}
