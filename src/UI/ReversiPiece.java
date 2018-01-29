package UI;

import engine.IOnClickListener;
import engine.IPlayer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

/**
 * Created by Kristijan Pajtasev
 * 31/10/2017.
 */
//class definition for a reversi piece
public class ReversiPiece extends Group {
	int row,col;
	
    // default constructor for the class
    public ReversiPiece(int player, int row, int col, int CELL_SIZE, 
    		IOnClickListener listener, IPlayer iPlayer) {
    	
    	this.row=row;
        this.col=col;
        
        createEclipse(CELL_SIZE,listener,iPlayer);
        
        setPiece(player);
        
        getChildren().add(createCell(CELL_SIZE));
        getChildren().add(piece);
        
    }
    private void createEclipse(int CELL_SIZE, 
    		IOnClickListener listener, IPlayer iPlayer){
        t = new Translate();
        piece = new Ellipse();
        resize(CELL_SIZE,CELL_SIZE);
        piece.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	
                listener.onClick(row, col);
            }
        });
        
        piece.getTransforms().add(t);
        
    }
    private Rectangle createCell(int CELL_SIZE){
        final double arcSize = CELL_SIZE / 6d;
        Rectangle cell = new Rectangle(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        // provide default style in case css are not loaded
        cell.setFill(Color.WHITE);
        cell.setStroke(Color.GREY);
        cell.setArcHeight(arcSize);
        cell.setArcWidth(arcSize);
        cell.getStyleClass().add("game-grid-cell");
        
        return cell;
    }
    // overridden version of the resize method to give the piece the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        double r=width / 2-4;
        piece.setCenterX(col * width+width/2);
        piece.setCenterY(row * width+width/2);

        piece.setRadiusX(r);
        piece.setRadiusY(r);
        
        
    }

    // overridden version of the relocate method to position the piece correctly
    @Override
    public void relocate(double x, double y) {
        super.relocate(x, y);
        t.setX(x);
        t.setY(y);
    }

    // public method that will swap the colour and type of this piece
    public void swapPiece() {
        if(player == 1) 
        	setPiece(2);
        else 
        	setPiece(1);
    }

    // method that will set the piece type
    public void setPiece(final int type) {
        player = type;

        switch(type) {
            case 0:
                piece.setFill(Color.TRANSPARENT);
                break;
            case 1:
                piece.setFill(Color.WHITE);
                break;
            case 2:
                piece.setFill(Color.BLACK);
                break;
        }
    }

    // returns the type of this piece
    public int getPiece() {
        return player;
    }

    // private fields
    private int player;		// the player that this piece belongs to
    private Ellipse piece;	// ellipse representing the player's piece
    private Translate t;	// translation for the player piece
}