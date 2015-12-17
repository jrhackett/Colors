import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private Stage stage;
    private StackPane root;

    private int rectangleWidth = 200;
    private int rectangleHeight = 200;
    private int timerWidth = 4;

    private Paint red = Paint.valueOf("#ae5a41");
    private Paint green = Paint.valueOf("#559e83");
    private Paint blue = Paint.valueOf("#1b85b8");
    private Paint yellow = Paint.valueOf("#c3cb71");
    private Paint white = Paint.valueOf("#e0e0e0");
    private Paint black = Paint.valueOf("#5a5255");

    private Rectangle topLeftRectangle;
    private Rectangle topRightRectangle;
    private Rectangle bottomLeftRectangle;
    private Rectangle bottomRightRectangle;

    private Circle insideCircle;
    private Circle circle;
    private Circle outerTimerCircle;
    private Circle innerTimerCircle;

    private Label scoreLabel;
    private Button lose;

    private LinkedList<Paint> colors;

    private SimpleBooleanProperty LOSE_VISIBLE;
    private SimpleBooleanProperty TIME_VISIBLE;
    public SimpleBooleanProperty FIRST_VISIBLE;

    private int counter = 1500;
    private int currentMax = 1500;
    private Timeline timeline;

    private int score = 0;

    private boolean playable = true;


    /** Frosty **/
    private static final double W = 330;
    private static final double H = 590;

    private static final double BLUR_AMOUNT = 60;

    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

    public Controller(Stage stage, StackPane root) {
        this.stage = stage;
        this.root = root;
        this.root.setStyle("-fx-background-color:#e0e0e0;");//5a5255

        this.colors = new LinkedList<>();
        this.colors.add(red);
        this.colors.add(green);
        this.colors.add(blue);
        this.colors.add(yellow);

        LOSE_VISIBLE = new SimpleBooleanProperty(false);
        TIME_VISIBLE = new SimpleBooleanProperty(true);
        FIRST_VISIBLE = new SimpleBooleanProperty(true);
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

        lose = new Button("play");
        lose.setTextFill(black);
        lose.setId("lose-button");
        lose.visibleProperty().bind(LOSE_VISIBLE);

        lose.setOnMouseClicked(e -> {
            this.playable = true;
            this.LOSE_VISIBLE.setValue(false);
            this.TIME_VISIBLE.setValue(true);
            this.counter = 1500;
            timeline.playFromStart();
            this.score = 0;
        });

        scoreLabel = new Label();
        scoreLabel.setText(Integer.toString(score));
        scoreLabel.setTextFill(black);
        scoreLabel.setId("score-label");

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(playable) {
                    counter--;
                    scoreLabel.setText(Integer.toString(score));
                    outerTimerCircle.setRadius(73 * counter / currentMax);
                    innerTimerCircle.setRadius(outerTimerCircle.getRadius() - timerWidth);
                    if (counter <= 0) {
                        timeline.stop();
                        playable = false;
                        LOSE_VISIBLE.setValue(true);
                        TIME_VISIBLE.setValue(false);
                    }
                }
                else
                {
                    timeline.stop();
                }
            }
        }));
        timeline.playFromStart();

        vbox.getChildren().addAll(scoreLabel, hbox1, hbox2);

        initCircles();

        this.root.getChildren().addAll(vbox, circle, insideCircle, lose, outerTimerCircle, innerTimerCircle);
    }

    public Paint randomColor() {
        return this.colors.get((int)(Math.random()*10) % 4);
    }

    public LinkedList<Integer> randomRectangleColors() {
        LinkedList<Integer> colors = new LinkedList<>();
        int i = 0;
        while(i < 4)
        {
            int x = (int)(Math.random() * 10) % 4;
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

    public void handleRectangleClick(Rectangle rectangle) {
        if(rectangle.getFill() == insideCircle.getFill() && playable) {
            this.score++;
            insideCircle.setFill(randomColor());
            this.changeRectangleColors();
            counter = (int)getNewCounterValue(this.score);
            innerTimerCircle.setFill(insideCircle.getFill());
        }
        else
        {
            LOSE_VISIBLE.setValue(true);
            TIME_VISIBLE.setValue(false);
            playable = false;
        }
    }

    public double getNewCounterValue(int score) {
        return 1500.0 * Math.pow(0.98, score);
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
            handleRectangleClick(topLeftRectangle);
        });

        topRightRectangle.setOnMouseClicked(e -> {
            handleRectangleClick(topRightRectangle);
        });

        bottomLeftRectangle.setOnMouseClicked(e -> {
            handleRectangleClick(bottomLeftRectangle);

        });

        bottomRightRectangle.setOnMouseClicked(e -> {
            handleRectangleClick(bottomRightRectangle);
        });
    }

    private void initCircles() {
        circle = new Circle();
        insideCircle = new Circle();
        innerTimerCircle = new Circle();
        outerTimerCircle = new Circle();

        circle.setRadius(90);
        circle.setFill(white);
        insideCircle.setRadius(80);
        insideCircle.setFill(randomColor());

        innerTimerCircle.setRadius(70);
        innerTimerCircle.setFill(insideCircle.getFill());

        outerTimerCircle.setRadius(innerTimerCircle.getRadius() + timerWidth);
        outerTimerCircle.setFill(white);

        innerTimerCircle.visibleProperty().bind(TIME_VISIBLE);
        outerTimerCircle.visibleProperty().bind(TIME_VISIBLE);
    }
}
