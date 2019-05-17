package database;

import java.sql.Timestamp;
import java.util.ArrayList;

import client.Challenge;
import client.Lobby;
import client.User;
import game.CollectiveGoalCard;
import game.CurrencyStone;
import game.Die;
import game.GlassWindow;
import game.Message;
import game.PatternCard;
import game.Player;
import game.Round;
import game.ToolCard;

//A nice facade so to hide the complexity and ugly shit behind this, you can also call this 
//the gate to hell. It's pretty much like at a Chinese restaurant
public class PersistenceFacade {
	private BaseDAO baseDAO = new BaseDAO();
	private GameDAO gameDAO = new GameDAO(baseDAO.getConnection());
	private PlayerDAO playerDAO = new PlayerDAO(baseDAO.getConnection());
	private MessageDAO messageDAO = new MessageDAO(baseDAO.getConnection());
	private DieDAO dieDAO = new DieDAO(baseDAO.getConnection());
	private PatternCardDAO patternCardDAO = new PatternCardDAO(baseDAO.getConnection());
	private ToolCardDAO toolCardDAO = new ToolCardDAO(baseDAO.getConnection());
	private CollectiveGoalCardDAO collectiveGoalCardDAO = new CollectiveGoalCardDAO(baseDAO.getConnection());
	private CurrencyStoneDAO currencyStoneDAO = new CurrencyStoneDAO(baseDAO.getConnection());
	private SpaceGlassDAO spaceGlassDAO = new SpaceGlassDAO(baseDAO.getConnection());
	
	// Client
	private ChallengeDAO challengeDAO = new ChallengeDAO(baseDAO.getConnection());
	private LobbyDAO lobbyDAO = new LobbyDAO(baseDAO.getConnection());
	private UserDAO userDAO = new UserDAO(baseDAO.getConnection());
	
	// Login
	private LoginDAO loginDAO = new LoginDAO(baseDAO.getConnection());
	
	// Login
	public boolean loginCorrect(String username, String password) {
		return loginDAO.loginCorrect(username, password);
	}
	
	public boolean insertCorrect(String username, String password) {
		return loginDAO.insertCorrect(username, password);
	}
	
	// ChallengeDAO
	public ArrayList<Challenge>	getChallenges(String username) {
		return challengeDAO.getChallenges(username);
	}
	
	public boolean updateChallenge(String username, ArrayList<Challenge> oldList) {
		return challengeDAO.checkUpdate(username, oldList);
	}
	
	// LobbyDAO
	public ArrayList<Lobby> getLobbies(String username) {
		return lobbyDAO.getLobbies(username);
	}
	
	public boolean updateLobby(String username, ArrayList<Lobby> oldList) {
		return lobbyDAO.checkUpdate(username, oldList);
	}
	
	// UserDAO
	public User getUser(String username) {
		return userDAO.getUser(username);
	}
	
	public boolean updateUser(String username, User oldUser) {
		return userDAO.checkUpdate(username, oldUser);
	}

	public void createGame(/* ArrayList<User> users */) {
		gameDAO.createGame();
	}

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

	public Player getCurrentPlayer(int idGame) {
		return playerDAO.getCurrentPlayer(idGame);
	}

	public void insertPlayer(int idGame, ArrayList<String> username) {
		playerDAO.insertPlayer(idGame, username);
	}

	public void updatePlayerTurn(Player oldPlayer, Player newPlayer) {
		playerDAO.updatePlayerTurn(oldPlayer, newPlayer);
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

	//SpaceGlassDAO
	public GlassWindow getGlassWindow(int idPlayer) {
		return spaceGlassDAO.getGlassWindow(idPlayer);
	}

	//PatternCardDAO
	public void insertPatternCardOptions(int idPlayer) {
		patternCardDAO.insertPatternCardOptions(idPlayer);
	}

	public ArrayList<PatternCard> getplayerPatternCard(int idPlayer) {
		return patternCardDAO.getplayerPatternCard(idPlayer);
	}

	//GameDAO

}
