package circle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CircleView {

    private Circle circle;

    public CircleView(Paint color, int radius, SimpleBooleanProperty time_visible) {
        this.circle = new Circle();
        this.circle.setRadius(radius);
        this.circle.setFill(color);
        this.circle.visibleProperty().bind(time_visible);
    }

    public void setRadius(int radius) {
        this.circle.setRadius(radius);
    }

    public void setColor(Paint color) {
        this.circle.setFill(color);
    }
}
