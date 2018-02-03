package UI;

import engine.ReversiEngine;
import framework.GameEngine;
import framework.board.builder.GameBoardDirectorNew;
import framework.board.decorator.PieceLayout;
import framework.gridCreator.visitor.GridCreatorVisitor;
import framework.gridDriver.bridge.CustomGridDriver;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Group;
import reversi.*;

/**
 *
 * @author bruno
 */
public class GameManagerNew extends Group {

    public static final int DEFAULT_GRID_SIZE=8;



    ReversiBoard1 board;
    ReversiEngine engine=new ReversiEngine();

    GameEngine gameEngine = null;

    public GameManagerNew() {
        this(DEFAULT_GRID_SIZE);
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
    public GameManagerNew(int gridSize) {

        gameEngine = new GameEngine();

        CustomGridDriver customGridDriver = new ReversiGridDriver(gridSize,gridSize,gridSize);
        PieceLayout pieceLayout = new ReversiPieceLayout(gameEngine);
        GridCreatorVisitor visitor = new RGridCreatorVisitor();

        ReversiBoardBuilderNew reversiBoardBuilderNew = new ReversiBoardBuilderNew(this,pieceLayout,customGridDriver,visitor);

        GameBoardDirectorNew directorNew = new GameBoardDirectorNew(reversiBoardBuilderNew);

        board = (ReversiBoard1) directorNew.getBoard();

        gameEngine.setBoard(board);
        gameEngine.addScoreObserver(board);

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
        board.undoProperty().addListener((ov, b, b1) -> {
            if (b1) {
                performUndo();
            }
        });
        
        initializeGameGrid();
        startGame();
    }

    /**
     * Initializes all cells in gameGrid map to null
     */
    private void initializeGameGrid() {

    }

    /**
     * Starts the game by adding 1 or 2 tiles at random locations
     */
    private void startGame() {
    	gameEngine.startGame();
        board.startGame();
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
    public void undo() {
        board.undo();
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
        GameState currentState = null; // gridOperator.getCurrentState();
        RecordManager recordManager = RecordManager.getInstance();
        recordManager.saveRecordToFile(currentState);
    }

    /** 
     * Ask to restore the game from a properties file with confirmation
     */
    public void restoreSession() {
        board.restoreSession();
    }

    private void performUndo() {
        RecordManager recordManager = RecordManager.getInstance();
        GameState previousState = recordManager.getPreviousState();
        if(restoreGameState(previousState)) {
            recordManager.removeLastState();
        }
    }
    
    /** 
     * Restore the game from a properties file, without confirmation
     */
    private void doRestoreSession() {
        RecordManager recordManager = RecordManager.getInstance();
        GameState savedState = recordManager.getSavedRecord();
        recordManager.clearStoredStates();
        restoreGameState(savedState);
        //gridOperator.storeGameState();
    }

    private boolean restoreGameState(GameState state) {
        if(state != null) {
            //gridOperator.restoreGridState(state.getPieces());
            if (state.getCurrentPlayer() != engine.getCurrentPlayer()) engine.swapPlayers();
            engine.updateScores();
            return true;
        }
        return false;
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
    
    public void setHostServices(HostServices hostServices){
        board.setHostServices(hostServices);
    }
}
