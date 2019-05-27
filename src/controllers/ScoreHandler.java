package controllers;

import java.util.ArrayList;

import game.CollectiveGoalCard;
import game.Die;
import game.Player;

public class ScoreHandler {

	private ArrayList<CollectiveGoalCard> collectiveGoalCards;
	private GoalCardHandler goalCardHandler;

	public ScoreHandler(ArrayList<CollectiveGoalCard> collectiveGoalCards) {
		this.collectiveGoalCards = collectiveGoalCards;
		goalCardHandler = new GoalCardHandler();
	}

	public void updateAllPrivateScore(ArrayList<Player> players) {
		int score;
		for (Player p : players) {
			score = 0;
			score += handlePrivateGoalCard(p);
			score -= handleEmptySpaces(p);
			//TODO substact used currencystones
			score += p.getCurrencyStones().size();
			score += goalCardHandler.getGoalCardHandler(p.getGlassWindow(), collectiveGoalCards);
			p.setScore(score);
		}
	}

	public int getScore(Player p, boolean privateScore) {
		int score = 0;
		score += privateScore ? handlePrivateGoalCard(p) : 0;
		score -= handleEmptySpaces(p);
		score += p.getCurrencyStones().size();
		score += goalCardHandler.getGoalCardHandler(p.getGlassWindow(), collectiveGoalCards);
		return score;
	}

	private int handlePrivateGoalCard(Player player) {
		int count = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				Die die = player.getGlassWindow().getSpace(x, y).getDie();
				if (die != null && die.getDieColor() == player.getPersonalGoalCard())
					count += die.getDieValue();
			}
		}
		return count;
	}

	private int handleEmptySpaces(Player player) {
		int count = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				if (player.getGlassWindow().getSpace(x, y).getDie() == null)
					count++;
			}
		}
		return count;
	}
}
