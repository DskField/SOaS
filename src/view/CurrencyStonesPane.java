package view;

import controllers.GameController;
import game.CurrencyStone;
import javafx.scene.layout.FlowPane;

public class CurrencyStonesPane extends FlowPane {
	private GameController gc;
	private int size = 100;

	public CurrencyStonesPane(GameController gc) {
		this.gc = gc;
		setPrefSize(size, size);

		showStones();
	}

	// Draws a stone for each currencystone from player

	private void showStones() {
		for (CurrencyStone cs : gc.getClientPlayer().getCurrencyStones()) {
			getChildren().add(new CurrencyStonePane(gc.getClientPlayer().getGlassWindow().getColor()));
		}
	}
}
