package framework;

public class GameBoardDirector {
	
	private GameBoardBuilder builder = null; //or multiple meal builders
	
	public GameBoardDirector(GameBoardBuilder builder) {
		this.builder = builder;
	}
	
	public void constructBoard() {
		builder.createScore();
		builder.createToolBar();
		builder.createBoard();
		
		
	}
	public GameBoard getBoard() {
		return builder.getBoard();
	}
}
