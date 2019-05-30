package view;

import game.CurrencyStone;
import game.Player;
import javafx.scene.layout.FlowPane;

public class CurrencyStonesPane extends FlowPane {

	private static final int SIZE = 100;

	public CurrencyStonesPane() {
		setPrefSize(SIZE, SIZE);
	}

	// Draws a stone for each currencystone from player
//FIXME remove enhanced for loop
	public void showStones(Player player) {
		getChildren().clear();
		for (CurrencyStone cs : player.getCurrencyStones()) {
			getChildren().add(new CurrencyStonePane(player.getGlassWindow().getColor()));
		}
	}
}
