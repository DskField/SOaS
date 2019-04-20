package database;

import java.util.List;

import game.Die;

//A nice facade so to hide the complexity and ugly shit behind this, you can also call this 
//the gate to hell. It's pretty much ike at a Chinese restaurant
public class PersistenceFacade {
	private GameDAO gameDAO= new GameDAO();
	private PlayerDAO playerDAO = new PlayerDAO();
	private MessageDAO chatDAO = new MessageDAO();
	private DieDAO dieDAO = new DieDAO();
	private ObjectiveCardDAO objectiveCardDAO = new ObjectiveCardDAO();
	private PatternCardDAO patternCardDAO = new PatternCardDAO();
	private ToolCardDAO toolCardDAO = new ToolCardDAO();
	
	
	
	
	
	public List<Die> getAllDies() {
		return dieDAO.getAllDies();
		}
}
