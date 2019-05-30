package view;

import java.util.ArrayList;

import javafx.scene.layout.FlowPane;

public class CurrencyStonesPane extends FlowPane {

	private static final int SIZE = 100;

	public CurrencyStonesPane() {
		setPrefSize(SIZE, SIZE);
	}

	// Draws a stone for each currencystone from player
	// FIXME remove enhanced for loop
	public void showStones(ArrayList<CurrencyStonePane> currencyStonePanes) {
		getChildren().clear();
		getChildren().addAll(currencyStonePanes);
	}
}
