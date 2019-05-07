package database;

import java.sql.Timestamp;
import java.util.ArrayList;

import game.CollectiveGoalCard;
import game.CurrencyStone;
import game.Die;
import game.Message;
import game.Player;
import game.Round;
import game.ToolCard;

//A nice facade so to hide the complexity and ugly shit behind this, you can also call this 
//the gate to hell. It's pretty much like at a Chinese restaurant
public class PersistenceFacade {
	private GameDAO gameDAO = new GameDAO();
	private PlayerDAO playerDAO = new PlayerDAO();
	private MessageDAO messageDAO = new MessageDAO();
	private DieDAO dieDAO = new DieDAO();
	private PatternCardDAO patternCardDAO = new PatternCardDAO();
	private ToolCardDAO toolCardDAO = new ToolCardDAO();
	private CollectiveGoalCardDAO collectiveGoalCardDAO = new CollectiveGoalCardDAO();
	private CurrencyStoneDAO currencyStoneDAO = new CurrencyStoneDAO();

	// CollectiveGoalCardDAO
	public ArrayList<CollectiveGoalCard> getSharedCollectiveGoalCards(int idGame) {
		return collectiveGoalCardDAO.getSharedCollectiveGoalCards(idGame);
	}

	public CollectiveGoalCard getSelectedCollectiveGoalCard(int idpublic_objectivecard) {
		return collectiveGoalCardDAO.getSelectedCollectiveGoalCard(idpublic_objectivecard);
	}

	public void insertRandomSharedCollectiveGoalCards(int idGame) {
		collectiveGoalCardDAO.insertRandomSharedCollectiveGoalCards(idGame);
	}

	// CurrencyStoneDAO
	public ArrayList<CurrencyStone> getAllStonesInGame(int idGame) {
		return currencyStoneDAO.getAllStonesInGame(idGame);
	}

	public ArrayList<CurrencyStone> getCurrencyStonesPlayer(int idGame, int playerID) {
		return currencyStoneDAO.getCurrencyStonesPlayer(idGame, playerID);
	}

	public ArrayList<CurrencyStone> getCurrencyStonesOnCard(int idToolCard, int idGame) {
		return currencyStoneDAO.getCurrencyStonesOnCard(idToolCard, idGame);
	}

	public void updateGameFavorTokenUsed(int stoneID, int idGame, int gametoolcard) {
		currencyStoneDAO.updateGameFavorTokenUsed(stoneID, idGame, gametoolcard);
	}

	public void updateGivePlayerCurrencyStones(int idGame, int idPlayer, int ammount) {
		currencyStoneDAO.updateGivePlayerCurrencyStones(idGame, idPlayer, ammount);
	}

	// PlayerDAO
	public ArrayList<Player> getAllPlayers() {
		return playerDAO.getAllPlayers();
	}

	public ArrayList<Player> getAllPlayersInGame(int idGame) {
		return playerDAO.getAllPlayersInGame(idGame);
	}

	public void insertPlayer(int idGame, ArrayList<String> username) {
		playerDAO.insertPlayer(idGame, username);
	}

	// ToolCardDAO
	public ArrayList<ToolCard> getGameToolCards(int idGame) {
		return toolCardDAO.getGameToolCards(idGame);
	}

	public ToolCard getSelectedToolCard(int idtoolcard) {
		return toolCardDAO.getSelectedToolCard(idtoolcard);
	}

	public void insertRandomGameToolCards(int idGame) {
		toolCardDAO.insertRandomGameToolCards(idGame);
	}

	// DieDAO
	public void updateDiceRoll(int gameID, ArrayList<Die> dice) {
		dieDAO.updateDiceRoll(gameID, dice);
	}

	public void updateDiceRound(int gameID, int round, ArrayList<Die> dice) {
		dieDAO.updateDiceRound(gameID, round, dice);
	}

	public ArrayList<Die> getGameDice(int gameID) {
		return dieDAO.getGameDice(gameID);
	}

	public Round[] getRoundTrack(int gameID) {
		return dieDAO.getRoundTrack(gameID);
	}

	//MessageDAO
	public ArrayList<Message> getALLMessages(ArrayList<Player> players) {
		return messageDAO.getALLMessages(players);
	}

	public ArrayList<Message> updateChat(ArrayList<Player> players, Timestamp time) {
		return messageDAO.updateChat(players, time);
	}
	
	public void insertMessage(Message message) {
		messageDAO.sendMessage(message);
	}
}
