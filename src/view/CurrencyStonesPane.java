package view;

import controllers.GameController;
import game.CurrencyStone;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class CurrencyStonesPane extends FlowPane {
	private GameController gc;
	private int size = 100;

	public CurrencyStonesPane(GameController gc) {
		this.gc = gc;
		setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		setPrefSize(size, size);

//		showStones();
	}

	// Draws a stone for each currencystone from player

	private void showStones() {
		for (CurrencyStone cs : gc.getClientPlayer().getCurrencyStones()) {
			getChildren().add(new CurrencyStonePane(gc.getClientPlayer().getGlassWindow().getColor()));
		}
	}
}
