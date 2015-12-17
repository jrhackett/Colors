import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.LinkedList;
import java.util.Timer;

public class Controller {

    private Stage stage;
    private StackPane root;

    private int rectangleWidth = 200;
    private int rectangleHeight = 200;

    private Paint red = Paint.valueOf("red");
    private Paint green = Paint.valueOf("green");
    private Paint blue = Paint.valueOf("blue");
    private Paint yellow = Paint.valueOf("yellow");

    private Rectangle topLeftRectangle;
    private Rectangle topRightRectangle;
    private Rectangle bottomLeftRectangle;
    private Rectangle bottomRightRectangle;

    private Circle insideCircle;
    private Circle circle;

    private LinkedList<Paint> colors;

    private SimpleBooleanProperty LOSE_VISIBLE;

    private Timer timer;

    private boolean playable = true;

    public Controller(Stage stage, StackPane root) {
        this.stage = stage;
        this.root = root;
        this.root.setStyle("-fx-background-color:#333;");

        this.colors = new LinkedList<>();
        this.colors.add(red);
        this.colors.add(green);
        this.colors.add(blue);
        this.colors.add(yellow);

        LOSE_VISIBLE = new SimpleBooleanProperty(false);
    }

    public void initDisplay() {
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();

        initRectangles();

        hbox1.getChildren().addAll(topLeftRectangle, topRightRectangle);
        hbox2.getChildren().addAll(bottomLeftRectangle, bottomRightRectangle);

        hbox1.setSpacing(10);
        hbox2.setSpacing(10);
        vbox.setSpacing(10);

        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

        Button lose = new Button("Try again");
        lose.setTextFill(Paint.valueOf("#333"));
        lose.setStyle("-fx-font-size:30; -fx-background-color:white;");
        lose.visibleProperty().bind(LOSE_VISIBLE);

        lose.setOnMouseClicked(e -> {
            playable = true;
            LOSE_VISIBLE.setValue(false);
        });

        vbox.getChildren().addAll(hbox1, hbox2);

        circle = new Circle();
        insideCircle = new Circle();

        circle.setRadius(90);
        circle.setFill(Paint.valueOf("#333"));
        insideCircle.setRadius(80);
        insideCircle.setFill(randomColor());

        this.root.getChildren().addAll(vbox, circle, insideCircle,lose);
    }

    public Paint randomColor() {
        return this.colors.get((int)(Math.random()*10) % 4);
    }

    public LinkedList<Integer> randomRectangleColors() {
        LinkedList<Integer> colors = new LinkedList<>();
        int i = 0;
        while(i < 4)
        {
            int x = (int)(Math.random()*10) % 4;
            if(!colors.contains(x)) {
                colors.add(x);
                i++;
            }
        }
        return colors;
    }

    public void changeRectangleColors() {
        LinkedList<Integer> list = this.randomRectangleColors();
        topLeftRectangle.setFill(this.colors.get(list.get(0)));
        topRightRectangle.setFill(this.colors.get(list.get(1)));
        bottomLeftRectangle.setFill(this.colors.get(list.get(2)));
        bottomRightRectangle.setFill(this.colors.get(list.get(3)));
    }

    public void initRectangles() {
        topLeftRectangle = new Rectangle();
        topRightRectangle = new Rectangle();
        bottomLeftRectangle = new Rectangle();
        bottomRightRectangle = new Rectangle();

        topLeftRectangle.setWidth(rectangleWidth);
        topRightRectangle.setWidth(rectangleWidth);
        bottomLeftRectangle.setWidth(rectangleWidth);
        bottomRightRectangle.setWidth(rectangleWidth);

        topLeftRectangle.setHeight(rectangleHeight);
        topRightRectangle.setHeight(rectangleHeight);
        bottomLeftRectangle.setHeight(rectangleHeight);
        bottomRightRectangle.setHeight(rectangleHeight);

        topLeftRectangle.setFill(red);
        topRightRectangle.setFill(blue);
        bottomRightRectangle.setFill(green);
        bottomLeftRectangle.setFill(yellow);

        topLeftRectangle.setOnMouseClicked(e -> {
            if(topLeftRectangle.getFill() == insideCircle.getFill() && playable) {
                insideCircle.setFill(randomColor());
                this.changeRectangleColors();
            }
            else
            {
                LOSE_VISIBLE.setValue(true);
                playable = false;
            }
        });

        topRightRectangle.setOnMouseClicked(e -> {
            if(topRightRectangle.getFill() == insideCircle.getFill() && playable) {
                insideCircle.setFill(randomColor());
                this.changeRectangleColors();
            }
            else
            {
                LOSE_VISIBLE.setValue(true);
                playable = false;
            }
        });

        bottomLeftRectangle.setOnMouseClicked(e -> {
            if(bottomLeftRectangle.getFill() == insideCircle.getFill() && playable) {
                insideCircle.setFill(randomColor());
                this.changeRectangleColors();
            }
            else
            {
                LOSE_VISIBLE.setValue(true);
                playable = false;
            }
        });

        bottomRightRectangle.setOnMouseClicked(e -> {
            if(bottomRightRectangle.getFill() == insideCircle.getFill() && playable) {
                insideCircle.setFill(randomColor());
                this.changeRectangleColors();
            }
            else
            {
                LOSE_VISIBLE.setValue(true);
                playable= false;
            }
        });
    }
}
