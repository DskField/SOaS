package view;

import java.util.ArrayList;

import controllers.MainApplication;
import javafx.scene.layout.FlowPane;

public class CurrencyStonesPane extends FlowPane {

	private final double SIZE = 100*MainApplication.height;

	public CurrencyStonesPane() {
		setPrefSize(SIZE, SIZE);
	}

	/**
	 * Draws a stone for each {@code CurrencyStone} from player
	 * 
	 * @param currencyStonePanes - {@code ArrayList<CurrencyStonePane>} A list of panes that needs to be drawn to the screen
	 */
	public void showStones(ArrayList<CurrencyStonePane> currencyStonePanes) {
		getChildren().clear();
		getChildren().addAll(currencyStonePanes);
	}
}
