package engine;

import javafx.scene.Group;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public abstract class Piece extends Group {

    protected int column, row;

    protected IRule rule;

    protected Piece(IRule rule){
        this.rule = rule;
    }

    public final IMoveOutcome doMove(Move from, Move to){

        /*if (validateMove(from,to)){

            return action(from,to);

        }*/

        return null;
    }

    public void addOnMouseClickListener(IOnClickListener listener){
        this.setOnMouseClicked(event -> {
            listener.onClick(row,column);
        });
    };

    public void setRule(IRule rule){

    }

}
