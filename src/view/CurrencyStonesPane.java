package view;

import controllers.GameController;
import game.CurrencyStone;
import game.Player;
import javafx.scene.layout.FlowPane;

public class CurrencyStonesPane extends FlowPane {

	private final int SIZE = 100;

	private GameController gc;

	public CurrencyStonesPane(Player player) {
		setPrefSize(SIZE, SIZE);
		showStones(player);
	}

	// Draws a stone for each currencystone from player

	public void showStones(Player player) {
		getChildren().clear();
		for (CurrencyStone cs : player.getCurrencyStones()) {
			getChildren().add(new CurrencyStonePane(player.getGlassWindow().getColor()));
		}
	}
}
