package view;

import java.util.ArrayList;

import controllers.MainApplication;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ToolCardPane extends FlowPane {
	/* CONSTANTS */
	private final double Width = 230*MainApplication.width;
	private final double height = 330*MainApplication.height;

	/* VARIABLES */
	private FlowPane flowPane;
	private ImageView imageView;
	private int seqNr;

	public ToolCardPane(int seqNr) {
		flowPane = new FlowPane();
		flowPane.setPrefWidth(Width);
		imageView = new ImageView();
		imageView.setFitHeight(height);
		imageView.setFitWidth(Width);
		lookForImage(seqNr);
		getChildren().addAll(imageView, flowPane);
		this.seqNr = seqNr;
	}

	public int getSeqNr() {
		return seqNr;
	}

	public void updateStones(ArrayList<CurrencyStonePane> stones) {
		flowPane.getChildren().clear();
		flowPane.getChildren().addAll(stones);
	}
	
	private void lookForImage(int seqNr) {
		switch (seqNr) {
		case 1:
			imageView.setImage(new Image("/images/Toolcards/Card1.png"));
			break;
		case 2:
			imageView.setImage(new Image("/images/Toolcards/Card2.png"));
			break;
		case 3:
			imageView.setImage(new Image("/images/Toolcards/Card3.png"));
			break;
		case 4:
			imageView.setImage(new Image("/images/Toolcards/Card4.png"));
			break;
		case 5:
			imageView.setImage(new Image("/images/Toolcards/Card5.png"));
			break;
		case 6:
			imageView.setImage(new Image("/images/Toolcards/Card6.png"));
			break;
		case 7:
			imageView.setImage(new Image("/images/Toolcards/Card7.png"));
			break;
		case 8:
			imageView.setImage(new Image("/images/Toolcards/Card8.png"));
			break;
		case 9:
			imageView.setImage(new Image("/images/Toolcards/Card9.png"));
			break;
		case 10:
			imageView.setImage(new Image("/images/Toolcards/Card10.png"));
			break;
		case 11:
			imageView.setImage(new Image("/images/Toolcards/Card11.png"));
			break;
		case 12:
			imageView.setImage(new Image("/images/Toolcards/Card12.png"));
			break;
		default:
			imageView.setImage(new Image("/images/GoalCard/Private/Back.png"));
		}
	}

}
