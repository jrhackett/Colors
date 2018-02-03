package views;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import views.styles.Colors;

public class CircleView {

    private Circle circle;
    private Circle insideCircle;
    private Circle innerTimerCircle;
    private Circle outerTimerCircle;

    private int timerWidth = 4;

    public void initCircles(Paint color, SimpleBooleanProperty time_visible) {
        circle = new Circle();
        insideCircle = new Circle();
        innerTimerCircle = new Circle();
        outerTimerCircle = new Circle();

        circle.setRadius(90);
        circle.setFill(Colors.WHITE.getColor());

        insideCircle.setRadius(80);
        insideCircle.setFill(color);

        innerTimerCircle.setRadius(70);
        innerTimerCircle.setFill(insideCircle.getFill());
        innerTimerCircle.visibleProperty().bind(time_visible);

        outerTimerCircle.setRadius(innerTimerCircle.getRadius() + timerWidth);
        outerTimerCircle.setFill(Colors.WHITE.getColor());
        outerTimerCircle.visibleProperty().bind(time_visible);
    }
}
