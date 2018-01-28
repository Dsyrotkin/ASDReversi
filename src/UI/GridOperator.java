package UI;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sun.javafx.geom.Shape;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import application.ReversiPiece;
/**
 *
 * @author jpereda
 */
public class GridOperator {
    
    public static final int DEFAULT_GRID_SIZE = 8;

    private final int gridSize;
    private final List<Integer> traversalX;
    private final List<Integer> traversalY;
    
    private ReversiPiece[][] pieces;
    
    public GridOperator(){
        this(DEFAULT_GRID_SIZE);
    }
    
    public GridOperator(int gridSize){
        this.gridSize=gridSize;
        this.traversalX = IntStream.range(0, gridSize).boxed().collect(Collectors.toList());
        this.traversalY = IntStream.range(0, gridSize).boxed().collect(Collectors.toList());
    }
    
    public void sortGrid(Direction direction){
    }
    
    public int traverseGrid(IntBinaryOperator func) {
        AtomicInteger at = new AtomicInteger();
        traversalX.forEach(t_x -> {
            traversalY.forEach(t_y -> {
                at.addAndGet(func.applyAsInt(t_x, t_y));
            });
        });

        return at.get();
    }
    
    public int getGridSize(){ return gridSize; }
    
    public boolean isValidLocation(Location loc){
        return loc.getX() >= 0 && loc.getX() < gridSize && loc.getY() >= 0 && loc.getY() < gridSize;
    }
    
    public void create(Group group, int CELL_SIZE)
    {
    	pieces=new ReversiPiece[DEFAULT_GRID_SIZE][DEFAULT_GRID_SIZE];
    	for (int i=0;i<DEFAULT_GRID_SIZE;i++)
    		for (int j=0;j<DEFAULT_GRID_SIZE;j++)
    		{
    			pieces[i][j]=createCell(j,i, CELL_SIZE);
    			group.getChildren().add(pieces[i][j]);
    		}
    }
    private ReversiPiece createCell(int row, int col, int CELL_SIZE){
        
        return new ReversiPiece(0,row,col,CELL_SIZE);
    }
    
}
