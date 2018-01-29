package UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 *
 * @author bruno
 */
public class GameManager extends Group {

    public static final int FINAL_VALUE_TO_WIN = 2048;
    
    private static final Duration ANIMATION_EXISTING_TILE = Duration.millis(65);
    private static final Duration ANIMATION_NEWLY_ADDED_TILE = Duration.millis(125);
    private static final Duration ANIMATION_MERGED_TILE = Duration.millis(80);
    
    private volatile boolean movingTiles = false;
    private final List<Location> locations = new ArrayList<>();

    private final Board board;
    private final GridOperator gridOperator;

    public GameManager() {
        this(GridOperator.DEFAULT_GRID_SIZE);
    }
    
    /**
     * GameManager is a Group containing a Board that holds a grid and the score
     * a Map holds the location of the tiles in the grid
     * 
     * The purpose of the game is sum the value of the tiles up to 2048 points
     * Based on the Javascript version: https://github.com/gabrielecirulli/2048
     * 
     * @param gridSize defines the size of the grid, default 4x4
     */
    public GameManager(int gridSize) {
        
        gridOperator=new GridOperator(gridSize);
        board = new Board(gridOperator);
        this.getChildren().add(board);

        board.clearGameProperty().addListener((ov, b, b1) -> {
            if (b1) {
                initializeGameGrid();
            }
        });
        board.resetGameProperty().addListener((ov, b, b1) -> {
            if (b1) {
                startGame();
            }
        });
        board.restoreGameProperty().addListener((ov, b, b1) -> {
            if (b1) {
                doRestoreSession();
            }
        });
        board.saveGameProperty().addListener((ov, b, b1) -> {
            if (b1) {
                doSaveSession();
            }
        });
        
        initializeGameGrid();
        startGame();
    }

    /**
     * Initializes all cells in gameGrid map to null
     */
    private void initializeGameGrid() {
        locations.clear();
        gridOperator.traverseGrid((x, y) -> {
            Location thisloc = new Location(x, y);
            locations.add(thisloc);
            return 0;
        });
    }

    /**
     * Starts the game by adding 1 or 2 tiles at random locations
     */
    private void startGame() {
        List<Location> randomLocs = new ArrayList<>(locations);
        Collections.shuffle(randomLocs);
        Iterator<Location> locs = randomLocs.stream().limit(2).iterator();

       
        redrawTilesInGameGrid();

        board.startGame();
    }
    
    /**
     * Redraws all tiles in the <code>gameGrid</code> object
     */
    private void redrawTilesInGameGrid() {
        //gameGrid.values().stream().filter(Objects::nonNull).forEach(t->board.addTile(t));
    }


    




    /**
     * Set gameManager scale to adjust overall game size
     * @param scale 
     */
    public void setScale(double scale) {
        this.setScaleX(scale);
        this.setScaleY(scale);
    }

    /**
     * Check if overlay covers the grid or not
     * @return 
     */
    public BooleanProperty isLayerOn() {
        return board.isLayerOn();
    }
    
    /**
     * Pauses the game time, covers the grid
     */
    public void pauseGame() {
        board.pauseGame();
    }

    /**
     * Quit the game with confirmation
     */
    public void quitGame() {
        board.quitGame();
    }

    /** 
     * Ask to save the game from a properties file with confirmation
     */
    public void saveSession() {
        board.saveSession();
    }
    /**
     * Save the game to a properties file, without confirmation
     */
    private void doSaveSession() {
       // board.saveSession(gameGrid);
    }

    /** 
     * Ask to restore the game from a properties file with confirmation
     */
    public void restoreSession() {
        board.restoreSession();
    }
    
    /** 
     * Restore the game from a properties file, without confirmation
     */
    private void doRestoreSession() {
        initializeGameGrid();
//        if (board.restoreSession(gameGrid)) {
//            redrawTilesInGameGrid();
//        }
    }

    /**
     * Save actual record to a properties file
     */
    public void saveRecord() {
        board.saveRecord();
    }

    public void tryAgain() {
        board.tryAgain();
    }
    
    public void aboutGame() {
        board.aboutGame();
    }
    
    public void setToolBar(HBox toolbar){
        board.setToolBar(toolbar);
    }
    
    public void setHostServices(HostServices hostServices){
        board.setHostServices(hostServices);
    }
}
