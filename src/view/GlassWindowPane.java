package view;

import game.Die;
import game.GameColor;
import game.PatternCard;
import game.PatternCardGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class GlassWindowPane extends BorderPane {
	private final int glassWindowWidth = 400;
	private final int glassWindowHeight = 800;
	private final int spacingAmount = 20;
	private Insets spacing;
	private PatternPane patternField;
	private StackPane scoreField;
	private CornerRadii windowCurve;
	
	private PatternCardGenerator yay;
	
	
	
	public GlassWindowPane(boolean isSmall, GameColor color) {
		windowCurve = new CornerRadii(5,3,3,5,0,0,0,0,true,true,true,true,false,false,false,false);
		spacing = new Insets(spacingAmount);
		setBackground(new Background(new BackgroundFill(Color.rgb(68, 47, 25), windowCurve, null)));
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, windowCurve, new BorderWidths(5))));
		setPrefSize(glassWindowWidth, glassWindowHeight);
		setMaxSize(glassWindowWidth, glassWindowHeight);
		
		yay = new PatternCardGenerator();
		setScore(color, isSmall);
		setPattern(yay.getCard(), isSmall);
	}
	
	public void addContent(PatternCard pattern, Boolean small) {
		setPattern(pattern, small);
	}
	
	public void setSizeSmall() {
		setPrefSize(glassWindowWidth / 2, glassWindowHeight / 2.5);
		setMargin(patternField, new Insets(10));
	}
	
	public void setPattern(PatternCard pattern, Boolean isSmall) {
		setBottom(patternField = new PatternPane(pattern, isSmall));
		setMargin(patternField, spacing);
	}
	
	private void setPlayerList() {
		
	}
	
	private void setPlayerName() {
			
	}
	
	private void setScore(GameColor color, Boolean isSmall) {
		scoreField = new StackPane();
		Circle circle = null;
		Label label = new Label();
		label.setText("HELLO");
		label.setTextFill(Color.ALICEBLUE);
		setTop(scoreField);
		if(isSmall == true) {
			circle = new Circle(180, 180, 60);
			label.setFont(Font.font(20));
			setMargin(scoreField, new Insets(10, 20, 0, 20));

		}
		else {
			circle = new Circle(180, 180, 140);
			label.setFont(Font.font(40));
			setMargin(scoreField, spacing);
		}
		circle.setFill(color.getColor());
		circle.setStroke(Color.BLACK);
		scoreField.getChildren().addAll(circle,label);


	}
	
	public void addDie(Die die, int position) {
		patternField.addDie(position, die.getDieValue(), die.getDieColor());
	}
}
