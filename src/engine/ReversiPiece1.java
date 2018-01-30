package engine;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class ReversiPiece1 extends Group implements GamePiece {

    // private fields
    protected Player player;		// the player that this piece belongs to
    protected Translate t;	// translation for the player piece
    protected Rectangle cell;

    protected Ellipse piece;// ellipse representing the player's piece

    int row, column;

    private IRule rule;

    public ReversiPiece1(IRule iRule, Player player, int row, int col, int CELL_SIZE,
                        IOnClickListener listener) {

        this.rule = iRule;

        this.row=row;

        this.column=col;

        createEclipse(CELL_SIZE);

        setPiece(player);

        getChildren().add(createCell(CELL_SIZE));

        getChildren().add(piece);

    }

    private void createEclipse(int CELL_SIZE){
        t = new Translate();
        piece = new Ellipse();
        resize(CELL_SIZE,CELL_SIZE);
        piece.getTransforms().add(t);

    }

    private Rectangle createCell(int CELL_SIZE){
        final double arcSize = CELL_SIZE / 6d;
        cell = new Rectangle(column * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        // provide default style in case css are not loaded
        cell.setFill(Color.WHITE);
        cell.setStroke(Color.GREY);
        cell.setArcHeight(arcSize);
        cell.setArcWidth(arcSize);
        cell.getStyleClass().add("game-grid-cell");

        return cell;
    }

    @Override
    public IMoveOutcome action(Move from, Move to) {
        return rule.move(from, to);
    }

    @Override
    public boolean validateMove(Move from, Move to) {
        return rule.validateMove(from, to);
    }

    @Override
    public void setPiece(Player player) {

    }

    @Override
    public void clearCell() {

    }

    @Override
    public void addOnMouseClickListener(IOnClickListener listener) {
        this.setOnMouseClicked(event -> {
            listener.onClick(row,column);
        });
    }
}
