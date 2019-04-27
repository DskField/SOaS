package view;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.layout.Pane;

public class RoundPane extends Pane {
	private HashMap<Integer, ArrayList<DiePane>> roundTrack;

	public RoundPane() {
		roundTrack = new HashMap<Integer, ArrayList<DiePane>>();
	}
}
