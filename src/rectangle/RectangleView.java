package rectangle;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RectangleView {
    private Rectangle rectangle;

    public RectangleView(int width, int height, Paint color, RectangleClick onClick) {
        this.rectangle = new Rectangle();
        this.rectangle.setWidth(width);
        this.rectangle.setHeight(height);
        this.rectangle.setFill(color);

        this.rectangle.setOnMouseClicked(e -> onClick.apply(this.rectangle.getFill()));
    }
}
