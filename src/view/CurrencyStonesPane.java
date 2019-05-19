package view;

import controllers.GameController;
import game.CurrencyStone;
import game.Player;
import javafx.scene.layout.FlowPane;

public class CurrencyStonesPane extends FlowPane {
	
	
	private final int SIZE = 100;
	
	private GameController gc;

	private Player player;
	
	//TODO isnt MVC compliant, Give Player instead
	public CurrencyStonesPane(Player player) {
		setPrefSize(SIZE, SIZE);
		this.player = player;
		showStones();
	}

	// Draws a stone for each currenc	ystone from player

	private void showStones() {
		for (CurrencyStone cs : player.getCurrencyStones()) {
			getChildren().add(new CurrencyStonePane(player.getGlassWindow().getColor()));
		}
	}
}
