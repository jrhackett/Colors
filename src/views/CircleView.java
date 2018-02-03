package views;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import views.styles.Colors;

public class CircleView {

    private Circle circle;

    public Circle initCircle(Paint color, int radius, SimpleBooleanProperty time_visible) {
        circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        circle.visibleProperty().bind(time_visible);
        return circle;
    }
}
