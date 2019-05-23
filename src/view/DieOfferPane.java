package view;

import java.util.ArrayList;

import controllers.GameController;
import game.Die;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DieOfferPane extends HBox {
	private final int squareSize = 55;

	private GameController gameController;
	private ArrayList<DiePane> dice;

	public DieOfferPane(GameController gameController) {
		this.gameController = gameController;
		dice = new ArrayList<DiePane>();

		setMaxSize(625, 65);
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
	}

	public void addDice(ArrayList<Die> roundDies) {
		dice.clear();

		for (Die die : roundDies) {
			DiePane diePane = new DiePane(die.getDieId(), die.getDieValue(), die.getDieColor());
			setPadding(new Insets(5));
			setSpacing(15);
			diePane.resize(squareSize);
			dice.add(diePane);

			diePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					diePane.requestFocus();
				}
			});

			diePane.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						diePane.setBorder(new Border(new BorderStroke(Color.TURQUOISE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
						gameController.selectDie(diePane);
					} else {
						diePane.setBorder(null);
					}
				}
			});
		}

		updateDice();
	}

	private void updateDice() {
		getChildren().clear();

		getChildren().addAll(dice);
	}

	public void removeDie(DiePane diePane) {
		for (DiePane die : dice) {
			if (diePane.getNumber() == die.getNumber() && diePane.getColor() == die.getColor()) {
				dice.remove(dice.indexOf(die));
			}
		}

		updateDice();
	}

}
