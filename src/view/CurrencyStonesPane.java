package view;

import game.CurrencyStone;
import game.Player;
import javafx.scene.layout.FlowPane;

public class CurrencyStonesPane extends FlowPane {

	private static final int SIZE = 100;


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
