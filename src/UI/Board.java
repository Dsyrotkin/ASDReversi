package UI;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import application.Reversi;
import framework.board.GameBoard;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 *
 * @author jpereda
 */
public class Board extends Group implements GameBoard{
    public static final int CELL_SIZE = 64;
    private static final int BORDER_WIDTH = (14 + 2) / 2;
    private static final int TOP_HEIGHT = 92;
    private static final int GAP_HEIGHT = 50;
    private static final int TOOLBAR_HEIGHT = 80;
    private static final int TOOLBAR_GAP_HEIGHT = 10;

    private final IntegerProperty gameScoreProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty gameBestProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty gameMovePoints = new SimpleIntegerProperty(0);
    private final BooleanProperty gameWonProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameOverProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameAboutProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameUndoProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameTryAgainProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameSaveProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameRestoreProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty gameQuitProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty layerOnProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty resetGame = new SimpleBooleanProperty(false);
    private final BooleanProperty clearGame = new SimpleBooleanProperty(false);
    private final BooleanProperty restoreGame = new SimpleBooleanProperty(false);
    private final BooleanProperty saveGame = new SimpleBooleanProperty(false);
    private final BooleanProperty undo = new SimpleBooleanProperty(false);

    private LocalTime time;
    private Timeline timer;
    private final StringProperty clock = new SimpleStringProperty("00:00:00");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
    
    // User Interface controls
    private final VBox vGame = new VBox(0);
    private final Group gridGroup = new Group();

    private final HBox hTop = new HBox(0);
    private final HBox vScore = new HBox(0);
    Circle circle1, circle2;
    
    private final Label lblScore1 = new Label("0");
    private final Label lblScore2 = new Label("0");
    private final Label lblPoints = new Label();
    
    private final HBox overlay = new HBox();
    private final VBox txtOverlay = new VBox(10);
    private final Label lOvrText= new Label();
    private final Label lOvrSubText= new Label();
    private final HBox buttonsOverlay = new HBox();
    private final Button bTry = new Button("Try again");
    private final Button bContinue = new Button("Keep going");
    private final Button bContinueNo = new Button("No, keep going");
    private final Button bSave = new Button("Save");
    private final Button bRestore = new Button("Restore");
    private final Button bQuit = new Button("Quit");
    private final Button bUndo = new Button("Undo");
        
    private final HBox hToolbar = new HBox();
    private HostServices hostServices;
        
    private final Label lblTime=new Label();
    private Timeline timerPause;
    
    private final int gridWidth;
    private final GridOperator gridOperator;

    public Board(GridOperator grid){
        this.gridOperator=grid;
        gridWidth = CELL_SIZE * grid.getGridSize() + BORDER_WIDTH * 2;
        
        //createScore();
        //createGrid();
        
        initGameProperties();
    }

    public void createScore() {
        Label lblSubtitle = new Label("Reversi");
        lblSubtitle.getStyleClass().addAll("game-label","game-subtitle");
        HBox hFill = new HBox();
        HBox.setHgrow(hFill, Priority.ALWAYS);
        hFill.setAlignment(Pos.CENTER);
        
        VBox vScores = new VBox();
        HBox hScores=new HBox(5);
        
        vScore.setAlignment(Pos.CENTER);
        vScore.getStyleClass().add("game-vbox");
        
        circle1 = new Circle();
        circle1.setStrokeWidth(4);
        circle1.setFill(Color.WHITE);
        final double PADDING = 20;
        circle1.setRadius(PADDING);
        
        lblScore1.getStyleClass().addAll("game-label","game-score");
        //lblScore1.textProperty().bind(gameScoreProperty.asString());
        vScore.getChildren().addAll(circle1,lblScore1);

        HBox vRecord = new HBox(0);
        vRecord.setAlignment(Pos.CENTER);
        vRecord.getStyleClass().add("game-vbox");
        
        circle2 = new Circle();
        circle2.setStrokeWidth(4);
        circle2.setFill(Color.BLACK);
        circle2.setRadius(PADDING);
        
        lblScore2.getStyleClass().addAll("game-label","game-score");
        //lblScore2.textProperty().bind(gameBestProperty.asString());
        vRecord.getChildren().addAll(circle2, lblScore2);
        hScores.getChildren().addAll(vScore,vRecord);
        
        
        VBox vFill = new VBox();
        VBox.setVgrow(vFill, Priority.ALWAYS);
        vScores.getChildren().addAll(hScores,vFill);
                
        hTop.getChildren().addAll(lblSubtitle, hFill,vScores);
        hTop.setMinSize(gridWidth, TOOLBAR_HEIGHT);
        hTop.setPrefSize(gridWidth, TOOLBAR_HEIGHT);
        hTop.setMaxSize(gridWidth, TOOLBAR_HEIGHT);

        vGame.getChildren().add(hTop);

        HBox hTime=new HBox();
        hTime.setMinSize(gridWidth, TOOLBAR_GAP_HEIGHT);
        hTime.setAlignment(Pos.BOTTOM_RIGHT);
        lblTime.getStyleClass().addAll("game-label","game-time");
        lblTime.textProperty().bind(clock);
        timer=new Timeline(new KeyFrame(Duration.ZERO, e->{
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }),new KeyFrame(Duration.seconds(1)));
        timer.setCycleCount(Animation.INDEFINITE);
        hTime.getChildren().add(lblTime);
        
        //vGame.getChildren().add(hTime);
        getChildren().add(vGame);
        
        lblPoints.getStyleClass().addAll("game-label","game-points");
        lblPoints.setAlignment(Pos.CENTER);
        lblPoints.setMinWidth(100);
        getChildren().add(lblPoints);
    }
    

    
    public void createGrid() {
        
    	gridOperator.create(gridGroup, CELL_SIZE);


        gridGroup.getStyleClass().add("game-grid");
        gridGroup.setManaged(false);
        gridGroup.setLayoutX(BORDER_WIDTH);
        gridGroup.setLayoutY(BORDER_WIDTH);

        HBox hBottom = new HBox();
        hBottom.getStyleClass().add("game-backGrid");
        hBottom.setMinSize(gridWidth, gridWidth);
        hBottom.setPrefSize(gridWidth, gridWidth);
        hBottom.setMaxSize(gridWidth, gridWidth);
        
        // Clip hBottom to keep the dropshadow effects within the hBottom
        Rectangle rect = new Rectangle(gridWidth, gridWidth);
        hBottom.setClip(rect);
        hBottom.getChildren().add(gridGroup);
        
        vGame.getChildren().add(hBottom);
        
        // toolbar
        
        HBox hPadding= new HBox();
        hPadding.setMinSize(gridWidth, TOOLBAR_GAP_HEIGHT);
        hPadding.setPrefSize(gridWidth, TOOLBAR_GAP_HEIGHT);
        hPadding.setMaxSize(gridWidth, TOOLBAR_GAP_HEIGHT);
        
        hToolbar.setAlignment(Pos.CENTER);
        hToolbar.getStyleClass().add("game-backGrid");
        hToolbar.setMinSize(gridWidth, TOOLBAR_HEIGHT);
        hToolbar.setPrefSize(gridWidth, TOOLBAR_HEIGHT);
        hToolbar.setMaxSize(gridWidth, TOOLBAR_HEIGHT);
        
        vGame.getChildren().add(hPadding); 
        vGame.getChildren().add(hToolbar); 
    }

    public void setToolBar(HBox toolbar){
        toolbar.disableProperty().bind(layerOnProperty);
        toolbar.spacingProperty().bind(Bindings.divide(vGame.widthProperty(), 10));
        hToolbar.getChildren().add(toolbar);        
    }
    
    public void tryAgain(){
        if(!gameTryAgainProperty.get()){
            gameTryAgainProperty.set(true);
        }
    }
    
    private void btnTryAgain(){
        timerPause.stop();
        layerOnProperty.set(false);
        doResetGame();
    }

    private void btnSaveState() {
        timerPause.stop();
        layerOnProperty.set(false);
        saveGame.set(true);
        gameSaveProperty.set(false);
        saveGame.set(false);
    }

    private void btnRestoreState() {
        timerPause.stop();
        layerOnProperty.set(false);
        restoreGame.set(true);
        gameRestoreProperty.set(false);
        restoreGame.set(false);
    }
    
    private void keepGoing(){
        timerPause.stop();
        layerOnProperty.set(false);
        gameUndoProperty.set(false);
        gameTryAgainProperty.set(false);
        gameSaveProperty.set(false);
        gameRestoreProperty.set(false);
        gameAboutProperty.set(false);
        gameQuitProperty.set(false);
        timer.play();
    }

    public void btnUndo() {
        timerPause.stop();
        layerOnProperty.set(false);
        undo.set(true);
        gameUndoProperty.set(false);
        undo.set(false);
    }

    private void quit() {
        timerPause.stop();
        Platform.exit();
    }
    
    private Overlay wonListener= new Overlay("You win!","",bContinue, bTry, "game-overlay-pause", "game-lblPause",true);

    private class Overlay implements ChangeListener<Boolean> {

        private final Button btn1, btn2;
        private String message, warning;
        private final String style1, style2;
        private final boolean pause;
        
        public Overlay(String message, String warning, Button btn1, Button btn2, String style1, String style2, boolean pause){
            this.message=message;
            this.warning=warning;
            this.btn1=btn1;
            this.btn2=btn2;
            this.style1=style1;
            this.style2=style2;
            this.pause=pause;
        }
        
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                timer.stop();
                if(pause){
                    timerPause.play();
                }
                overlay.getStyleClass().setAll("game-overlay",style1);
                lOvrText.setText(message);
                lOvrText.getStyleClass().setAll("game-label",style2);
                lOvrSubText.setText(warning);
                lOvrSubText.getStyleClass().setAll("game-label","game-lblWarning");
                txtOverlay.getChildren().setAll(lOvrText,lOvrSubText);
                buttonsOverlay.getChildren().setAll(btn1);
                if(btn2!=null){
                    buttonsOverlay.getChildren().add(btn2);
                }
                if(!layerOnProperty.get()){
                    Board.this.getChildren().addAll(overlay,buttonsOverlay);
                    layerOnProperty.set(true);
                }
            }
        }
    }
    
    private void initGameProperties() {
        
        overlay.setMinSize(gridWidth, gridWidth);
        overlay.setAlignment(Pos.CENTER);
        overlay.setTranslateY(TOP_HEIGHT + GAP_HEIGHT - 60);
        
        overlay.getChildren().setAll(txtOverlay);
        txtOverlay.setAlignment(Pos.CENTER);
        
        buttonsOverlay.setAlignment(Pos.CENTER);
        buttonsOverlay.setTranslateY(TOP_HEIGHT + GAP_HEIGHT + gridWidth / 2);
        buttonsOverlay.setMinSize(gridWidth, gridWidth / 2);
        buttonsOverlay.setSpacing(10);
        
        bTry.getStyleClass().add("game-button");
        bTry.setOnTouchPressed(e -> btnTryAgain());
        bTry.setOnAction(e -> btnTryAgain());
        bTry.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.SPACE)){
                btnTryAgain();
            }
        });

        bContinue.getStyleClass().add("game-button");
        bContinue.setOnTouchPressed(e -> keepGoing());
        bContinue.setOnMouseClicked(e -> keepGoing());
        bContinue.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.SPACE)){
                keepGoing();
            }
        });

        bContinueNo.getStyleClass().add("game-button");
        bContinueNo.setOnTouchPressed(e -> keepGoing());
        bContinueNo.setOnMouseClicked(e -> keepGoing());
        bContinueNo.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.SPACE)){
                keepGoing();
            }
        });
        
        bSave.getStyleClass().add("game-button");
        bSave.setOnTouchPressed(e -> btnSaveState());
        bSave.setOnMouseClicked(e -> btnSaveState());
        bSave.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.SPACE)){
                btnSaveState();
            }
        });

        bRestore.getStyleClass().add("game-button");
        bRestore.setOnTouchPressed(e -> btnRestoreState());
        bRestore.setOnMouseClicked(e -> btnRestoreState());
        bRestore.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.SPACE)){
                btnRestoreState();
            }
        });

        bQuit.getStyleClass().add("game-button");
        bQuit.setOnTouchPressed(e -> quit());
        bQuit.setOnMouseClicked(e -> quit());
        bQuit.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.SPACE)){
                quit();
            }
        });

        bUndo.getStyleClass().add("game-button");
        bUndo.setOnTouchPressed(e -> btnUndo());
        bUndo.setOnMouseClicked(e -> btnUndo());
        bUndo.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.SPACE)){
                btnUndo();
            }
        });
      
        timerPause=new Timeline(new KeyFrame(Duration.seconds(1), 
                e->time=time.plusNanos(1_000_000_000)));
        timerPause.setCycleCount(Animation.INDEFINITE);
        
        gameWonProperty.addListener(wonListener);
        gameOverProperty.addListener(new Overlay("Game over!","",bTry, null, "game-overlay-over", "game-lblOver",false));
        gameUndoProperty.addListener(new Overlay("Perform Undo?","",bUndo, bContinueNo, "game-overlay-pause", "game-lblPause",true));
        gameTryAgainProperty.addListener(new Overlay("Try Again?","Current game will be deleted",bTry, bContinueNo, "game-overlay-pause", "game-lblPause",true));
        gameSaveProperty.addListener(new Overlay("Save?","Previous saved data will be overwritten",bSave, bContinueNo, "game-overlay-pause", "game-lblPause",true));
        gameRestoreProperty.addListener(new Overlay("Restore?","Current game will be deleted",bRestore, bContinueNo, "game-overlay-pause", "game-lblPause",true));
        gameAboutProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                timer.stop();
                timerPause.play();
                overlay.getStyleClass().setAll("game-overlay","game-overlay-quit");
                TextFlow flow = new TextFlow();
                flow.setTextAlignment(TextAlignment.CENTER);
                flow.setPadding(new Insets(10,0,0,0));
                flow.setMinSize(gridWidth, gridWidth);
                flow.setPrefSize(gridWidth, gridWidth);
                flow.setMaxSize(gridWidth, gridWidth);
                flow.setPrefSize(BASELINE_OFFSET_SAME_AS_HEIGHT, BASELINE_OFFSET_SAME_AS_HEIGHT);
                Text t00 = new Text("Reversi");
                t00.getStyleClass().setAll("game-label","game-lblAbout");
//                Text t01 = new Text("FX");
//                t01.getStyleClass().setAll("game-label","game-lblAbout2");
                Text t02 = new Text(" Game\n");
                t02.getStyleClass().setAll("game-label","game-lblAbout");
                Text t1 = new Text("JavaFX game - Desktop version\n\n");
                t1.getStyleClass().setAll("game-label", "game-lblAboutSub");
                Text t20 = new Text("Powered by Board Game Framework");
                t20.getStyleClass().setAll("game-label", "game-lblAboutSub");
                Hyperlink link1 = new Hyperlink();
                link1.setText("");
                //link1.setOnAction(e->hostServices.showDocument("http://javafxports.org/page/home"));
                link1.getStyleClass().setAll("game-label", "game-lblAboutSub2");
                Text t21 = new Text(" \n\n");
                t21.getStyleClass().setAll("game-label", "game-lblAboutSub");
//                Text t23 = new Text("\u00A9 ");
//                t23.getStyleClass().setAll("game-label", "game-lblAboutSub");
                Hyperlink link2 = new Hyperlink();
                link2.setText("@Rules");
                link2.setOnAction(e->hostServices.showDocument("http://www.flyordie.com/games/help/reversi/en/games_rules_reversi.html"));
                link2.getStyleClass().setAll("game-label", "game-lblAboutSub2");
//                Text t22 = new Text(" & ");
//                t22.getStyleClass().setAll("game-label", "game-lblAboutSub");
//                Hyperlink link3 = new Hyperlink();
//                link3.setText("@brunoborges");
//                link3.setOnAction(e->hostServices.showDocument("https://twitter.com/brunoborges"));
//                Text t32 = new Text(" & ");
//                t32.getStyleClass().setAll("game-label", "game-lblAboutSub");
//                link3.getStyleClass().setAll("game-label", "game-lblAboutSub2");
                Text t24 = new Text("\n\n");
                t24.getStyleClass().setAll("game-label", "game-lblAboutSub");
                
                Text t31 = new Text(" Version "+Reversi.VERSION+" - 2018\n\n");
                t31.getStyleClass().setAll("game-label", "game-lblAboutSub");
                
                flow.getChildren().setAll(t00, t02, t1, t20, t21, link2);
                flow.getChildren().addAll(t24, t31);
                txtOverlay.getChildren().setAll(flow);
                buttonsOverlay.getChildren().setAll(bContinue);
                this.getChildren().removeAll(overlay,buttonsOverlay);
                this.getChildren().addAll(overlay,buttonsOverlay);
                layerOnProperty.set(true);
            }
        });
        gameQuitProperty.addListener(new Overlay("Quit Game?","Non saved data will be lost",bQuit, bContinueNo, "game-overlay-quit", "game-lblQuit",true));
        
        
        restoreRecord();
        
        gameScoreProperty.addListener((ov,i,i1)->{
            if(i1.intValue()>gameBestProperty.get()){
                gameBestProperty.set(i1.intValue());
            }
        });
        
        layerOnProperty.addListener((ov,b,b1)->{
            if(!b1){
                getChildren().removeAll(overlay, buttonsOverlay);
                // Keep the focus on the game when the layer is removed:
                getParent().requestFocus();
            } else if(b1){
                // Set focus on the first button
                buttonsOverlay.getChildren().get(0).requestFocus();
            }
        });
        
    }
    
    public void setHostServices(HostServices hostServices){
        this.hostServices=hostServices;
    }
    
    private void doClearGame() {
        saveRecord();
        getChildren().removeAll(overlay, buttonsOverlay);
        
        gridOperator.traverseGrid(0);
        clearGame.set(false);
        resetGame.set(false);
        restoreGame.set(false);
        saveGame.set(false);
        layerOnProperty.set(false);
        gameScoreProperty.set(0);
        gameWonProperty.set(false);
        gameOverProperty.set(false);
        gameAboutProperty.set(false);
        gameUndoProperty.set(false);
        gameTryAgainProperty.set(false);
        gameSaveProperty.set(false);
        gameRestoreProperty.set(false);
        gameQuitProperty.set(false);
        
        clearGame.set(true);
    }
    
    private void doResetGame() {
        doClearGame();
        resetGame.set(true);
    }
    
    public void animateScore() {
        if(gameMovePoints.get()==0){
            return;
        }
        
        final Timeline timeline = new Timeline();
        lblPoints.setText("+" + gameMovePoints.getValue().toString());
        lblPoints.setOpacity(1);
        double posX=vScore.localToScene(vScore.getWidth()/2d,0).getX();
        lblPoints.setTranslateX(0);
        lblPoints.setTranslateX(lblPoints.sceneToLocal(posX, 0).getX()-lblPoints.getWidth()/2d);
        lblPoints.setLayoutY(20);
        final KeyValue kvO = new KeyValue(lblPoints.opacityProperty(), 0);
        final KeyValue kvY = new KeyValue(lblPoints.layoutYProperty(), 100);

        Duration animationDuration = Duration.millis(600);
        final KeyFrame kfO = new KeyFrame(animationDuration, kvO);
        final KeyFrame kfY = new KeyFrame(animationDuration, kvY);

        timeline.getKeyFrames().add(kfO);
        timeline.getKeyFrames().add(kfY);

        timeline.play();
    }
    
    public Group getGridGroup() {
        return gridGroup;
    }
    
    public void startGame(){
        restoreRecord();
        
        time=LocalTime.now();
        timer.playFromStart();
    }
    
    public void setPoints(int points){
        gameMovePoints.set(points);
    }
   
    public int getPoints() {
        return gameMovePoints.get();
    }
    
    public void addPoints(int points){
        gameMovePoints.set(gameMovePoints.get() + points);
        gameScoreProperty.set(gameScoreProperty.get() + points);
    }
    
    public void setGameOver(boolean gameOver){
        gameOverProperty.set(gameOver);
    }
    
    public void setGameWin(boolean won){
        if(!gameWonProperty.get()){
            gameWonProperty.set(won);
        }
    }
    public void undo(){
        if(!gameUndoProperty.get()) {
            gameUndoProperty.set(true);
        }
    }
    public void aboutGame(){
        if(!gameAboutProperty.get()){
            gameAboutProperty.set(true);
        }
    }
    public void quitGame(){
        if(!gameQuitProperty.get()){
            gameQuitProperty.set(true);
        }
    }
    
    public BooleanProperty isLayerOn(){
        return layerOnProperty;
    }
    
    public BooleanProperty resetGameProperty() {
        return resetGame;
    }
    
    public BooleanProperty clearGameProperty() {
        return clearGame;
    }
    
    public BooleanProperty saveGameProperty() {
        return saveGame;
    }
    
    public BooleanProperty restoreGameProperty() {
        return restoreGame;
    }

    public BooleanProperty undoProperty() {
        return undo;
    }
    
    public boolean saveSession() {
        if(!gameSaveProperty.get()){
            gameSaveProperty.set(true);
        }
        return true;
    }
    
    public boolean restoreSession() {
        if(!gameRestoreProperty.get()){
            gameRestoreProperty.set(true);
        }
        return true;
    }
    
    public void saveRecord() {
//        RecordManager recordManager = new RecordManager(gridOperator.getGridSize());
//        recordManager.saveRecord(gameScoreProperty.getValue());
    }
    
    private void restoreRecord() {
//        RecordManager recordManager = new RecordManager(gridOperator.getGridSize());
//        gameBestProperty.set(recordManager.restoreRecord());
    }

	@Override
	public void updateScore(int currentplayer,int score1, int score2) {
		// TODO Auto-generated method stub
		lblScore1.setText(String.valueOf(score1));
		lblScore2.setText(String.valueOf(score2));
		
		Paint select=Color.GOLD;
		Paint unselect=Color.TRANSPARENT;
		if (currentplayer==1)
		{
			
			circle1.setStroke(select);
			circle2.setStroke(unselect);
		}
		else
		{
			circle1.setStroke(unselect);
			circle2.setStroke(select);
		}
			
	}

	@Override
	public void updateWinner(String info) {
		wonListener.message=info;
		setGameWin(true);
	}



    
}
