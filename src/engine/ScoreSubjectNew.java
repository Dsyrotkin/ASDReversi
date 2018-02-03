package engine;

import framework.player.factory.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreSubjectNew {

	private List<ScoreObserverNew> observers;

	public ScoreSubjectNew() {
		observers=new ArrayList<>();
	}

	public void addScoreObserver(ScoreObserverNew observer) {
		observers.add(observer);
	}

	public void removeScoreObserver(ScoreObserver observer) {
		observers.remove(observer);
	}

	public void notifyScore(Player currentplayer) {

		observers.forEach(c -> c.updateScore(currentplayer));

	}

	public void notifyWinner() {
		observers.forEach(c ->  c.updateWinner());
	}
}
