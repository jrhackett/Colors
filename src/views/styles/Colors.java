package views.styles;

import javafx.scene.paint.Paint;

/**
 * Created by jacobhackett on 2/3/18.
 */
public enum Colors {
    RED (Paint.valueOf("#ae5a41")),
    GREEN (Paint.valueOf("#559e83")),
    BLUE (Paint.valueOf("#1b85b8")),
    YELLOW (Paint.valueOf("#c3cb71")),
    WHITE (Paint.valueOf("#e0e0e0")),
    BLACK (Paint.valueOf("#5a5255"));

    private Paint color;

    Colors(Paint color) {
        this.color = color;
    }

    public Paint getColor() {
        return this.color;
    }
}
