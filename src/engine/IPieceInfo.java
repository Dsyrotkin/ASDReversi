package engine;

public interface IPieceInfo {
	int getRow();
	int getPiece(int row, int col);
	void setPiece(int row, int col, int player);

	void storeGameState();
	void clearStoredGameStates();
}
