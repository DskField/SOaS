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
		setMinSize(625, 65);
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
	}

	public void addDice(ArrayList<Die> roundDies) {
		if (!checkDice(roundDies)) {
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
	}

	private boolean checkDice(ArrayList<Die> newDice) {
		if (newDice.size() == dice.size()) {
			for (Die die : newDice) {
				if (die.getDieValue() != dice.get(newDice.indexOf(die)).getEyes() && die.getDieColor() != dice.get(newDice.indexOf(die)).getColor()) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private void updateDice() {
		getChildren().clear();

		getChildren().addAll(dice);
	}

	public void removeDie(DiePane diePane) {
		getChildren().remove(diePane);
		dice.remove(dice.indexOf(diePane));
	}

}
