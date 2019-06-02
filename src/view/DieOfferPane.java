package view;

import java.util.ArrayList;

import controllers.GameController;
import game.Die;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DieOfferPane extends HBox {
	private final int squareSize = 55;
	private final int WIDTH = 625 , HEIGHT = 65;
	private final int dieSpacing = 15;
	private final int inset = 5;
	private final int borderWidth = 5;
	
	private GameController gameController;
	private ArrayList<DiePane> dice;

	public DieOfferPane(GameController gameController) {
		this.gameController = gameController;
		dice = new ArrayList<DiePane>();

		setMaxSize(WIDTH, HEIGHT);
		setMinSize(WIDTH, HEIGHT);

	}

	/**
	 * adds dice to the pane
	 * @param roundDice the dice that have to be added
	 */
	public void addDice(ArrayList<Die> roundDice) {
		if (!checkDice(roundDice)) {
			dice.clear();

			for (Die die : roundDice) {
				DiePane diePane = new DiePane(die.getDieId(), die.getDieValue(), die.getDieColor());
				setPadding(new Insets(inset));
				setSpacing(dieSpacing);
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
							diePane.setBorder(new Border(new BorderStroke(Color.TURQUOISE, BorderStrokeStyle.SOLID, null, new BorderWidths(borderWidth))));
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

	/**
	 * refresh this pane
	 */
	private void updateDice() {
		getChildren().clear();

		getChildren().addAll(dice);
	}

	/**
	 * remove a die from this pane
	 * @param diePane the die to be removed
	 */
	public void removeDie(DiePane diePane) {
		getChildren().remove(diePane);
		dice.remove(dice.indexOf(diePane));
	}

}
