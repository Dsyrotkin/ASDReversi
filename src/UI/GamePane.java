package UI;

import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GamePane extends StackPane {
	
	private GameManager gameManager;
	private Bounds gameBounds;
    private final static int MARGIN = 36;
    
	public GamePane(HostServices hostServices) {
        gameManager = new GameManager();
        gameManager.setHostServices(hostServices);
        gameBounds = gameManager.getLayoutBounds();

        getChildren().add(gameManager);

        getStyleClass().addAll("game-root");
        ChangeListener<Number> resize = (ov, v, v1) -> {
            double scale = Math.min((getWidth() - MARGIN) / gameBounds.getWidth(), (getHeight() - MARGIN) / gameBounds.getHeight());
            gameManager.setScale(scale);
            gameManager.setLayoutX((getWidth() - gameBounds.getWidth()) / 2d);
            gameManager.setLayoutY((getHeight() - gameBounds.getHeight()) / 2d);
        };
        widthProperty().addListener(resize);
        heightProperty().addListener(resize);

        //addKeyHandler(this);
        //addSwipeHandlers(this);
        setFocusTraversable(true);
        this.setOnMouseClicked(e -> requestFocus());
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
