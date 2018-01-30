package engine;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreSubject {
	
	private List<ScoreObserver> observers;
	
	public ScoreSubject()
	{
		observers=new ArrayList<ScoreObserver>();
	}
	public void addScoreObserver(ScoreObserver observer)
	{
		observers.add(observer);
	}
	public void removeScoreObserver(ScoreObserver observer)
	{
		observers.remove(observer);
	}
	public void notifyScore(int currentplayer, int score1,int score2)
	{
		observers.forEach(c->c.updateScore(currentplayer, score1, score2));
	}
	public void notifyWinner(String info)
	{
		observers.forEach(c->c.updateWinner(info));
	}
}
