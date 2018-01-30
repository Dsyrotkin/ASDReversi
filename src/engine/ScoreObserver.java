package engine;

public interface ScoreObserver {
	void updateScore(int currentplayer, int score1,int score2);
	void updateWinner(String info);
}
