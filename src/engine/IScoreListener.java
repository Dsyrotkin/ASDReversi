package engine;

public interface IScoreListener {
	void setWinner(String info);
	void update(int currentplayer, int score1,int score2);
}	
