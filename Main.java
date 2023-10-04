package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * this main class, creates a gui that allows the user to draw diamond patterns,
 * it extends the java application class, and implements the eventHandler
 * interface.
 * 
 * @author samuel vossen
 *
 */
public class Main extends Application implements EventHandler<ActionEvent> {
	private Button drawButton = new Button("Draw");
	private Button exitButton = new Button("Exit");
	private Canvas canvas = new Canvas(400, 400);
	private GraphicsContext gc;
	private Point2D firstPoint;
	private Point2D secondPoint;
	private int clickCounter;

	/**
	 * the start method initiates the gui, creating a canvas of 400 by 400, adding
	 * two buttons one named draw, and the other exit.
	 *
	 */
	@Override
	public void start(Stage primaryStage) {
		clickCounter = 0;
		GridPane pane = new GridPane();
		pane.add(canvas, 0, 0);
		GridPane buttonPane = new GridPane();
		buttonPane.add(drawButton, 0, 0);
		buttonPane.add(exitButton, 1, 0);
		pane.add(buttonPane, 0, 1);
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		this.drawButton.addEventHandler(ActionEvent.ACTION, this);
		this.exitButton.addEventHandler(ActionEvent.ACTION, this);
		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				clickCounter = clickCounter + 1;
				int x = clickCounter % 2;
				if (x == 1) {
					firstPoint = new Point2D(event.getX(), event.getY());
				} else {
					secondPoint = new Point2D(event.getX(), event.getY());
				}
			}
		});
		primaryStage.show();

	}

	/**
	 * the handle method is a required method of the actionHandler interface, i
	 * takes in a parameter of an action event, and checks to see which button the
	 * action is from, if it is from the exit class the program is terminated if the
	 * action is from the draw button it will check to see if the click counter has
	 * reached 2 yet. if it has then it draws the diamond and resets the points and
	 * the clickcounter
	 * 
	 * @param event. a actionEvent object.
	 *
	 */
	@Override
	public void handle(ActionEvent event) {
		Button button = (Button) event.getSource();
		if (button == this.exitButton) {
			System.exit(0);

		} else if (button == this.drawButton) {
			if (this.clickCounter >= 2) {
				draw();
				this.firstPoint = null;
				this.secondPoint = null;
				this.clickCounter = 0;
			}
		}

	}

	/**
	 * the drawing method draws the diamond using the two points held in the
	 * instance variables it first checks to see if the points form a straight line
	 * by having either equal x or y variables. if it does it draws a straight line.
	 * if they are not in a straight line it then calculates the other two points
	 * and draws the diamond accordingly
	 */
	private void draw() {
		this.gc = this.canvas.getGraphicsContext2D();
		if ((this.firstPoint.getY() == this.secondPoint.getY())
				|| (this.firstPoint.getX() == this.secondPoint.getX())) {
			this.gc.strokeLine(this.firstPoint.getX(), this.firstPoint.getY(), this.secondPoint.getX(),
					this.secondPoint.getY());
		} else {
			Point2D pointThree;
			Point2D pointFour;
			pointThree = new Point2D(this.firstPoint.getX(), (2 * this.secondPoint.getY()) - this.firstPoint.getY());
			pointFour = new Point2D((2 * this.firstPoint.getX()) - this.secondPoint.getX(), this.secondPoint.getY());
			this.gc.strokeLine(this.firstPoint.getX(), this.firstPoint.getY(), this.secondPoint.getX(),
					this.secondPoint.getY());
			this.gc.strokeLine(this.secondPoint.getX(), this.secondPoint.getY(), pointThree.getX(), pointThree.getY());
			this.gc.strokeLine(pointThree.getX(), pointThree.getY(), pointFour.getX(), pointFour.getY());
			this.gc.strokeLine(pointFour.getX(), pointFour.getY(), this.firstPoint.getX(), this.firstPoint.getY());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
