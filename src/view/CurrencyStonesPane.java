package view;

import java.util.ArrayList;

import controllers.GameController;
import game.CurrencyStone;
import game.GameColor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class CurrencyStonesPane extends FlowPane {
	GameController gc;

	public CurrencyStonesPane(GameController gc) {
		this.gc = gc;
		setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		setPrefSize(100, 100);

		showStones();
	}

	public void showStones() {
		ArrayList<CurrencyStone> currencyStones = new ArrayList<>();
		int i = 0;
		while (i < 5) {
			CurrencyStone ff = new CurrencyStone(i + 1, 1);
			currencyStones.add(ff);
			i += 1;
		}
		for (CurrencyStone cs : currencyStones) {
			System.out.println("ur mum");
			getChildren().add(new CurrencyStonePane(GameColor.BLUE));
		}
	}
}
