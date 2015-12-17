import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    private StackPane root;
    private Controller controller;

    private static final double W = 330;
    private static final double H = 590;

    private static final double BLUR_AMOUNT = 60;

    private static final Effect frostEffect =
            new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

    private boolean PATH_FLAG = true;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.root = new StackPane();
        stage = primaryStage;

        controller = new Controller(stage, root);
        controller.initDisplay();

        stage.setResizable(true);
        Scene scene = new Scene(this.root, 500, 500);
        scene.getStylesheets().add("css/custom.css");
        stage.setScene(scene);


        stage.setTitle("Colors");
        stage.show();
        this.showTutorial();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void showTutorial() {


        StackPane imageStack = new StackPane();

        javafx.scene.Node frost      = freeze(this.root);

        imageStack.setOnMouseClicked(e -> {
            this.root.getChildren().removeAll(imageStack, frost);
            this.controller.FIRST_VISIBLE.setValue(false);
        });

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(Background.EMPTY);

        Text text = new Text("click on the rectangle that is the same color as the circle");
        text.setWrappingWidth(200);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setStyle("-fx-font-size:20;");

        vbox.getChildren().add(text);
        imageStack.getChildren().add(vbox);

        this.root.getChildren().addAll(frost, imageStack);
    }

    public StackPane freeze(Node background) {
        Image frostImage = background.snapshot(
                new SnapshotParameters(),
                null
        );

        ImageView frost = new ImageView(frostImage);

        Rectangle filler = new Rectangle(0, 0, this.stage.getWidth(), this.stage.getHeight());
        filler.widthProperty().bind(this.stage.widthProperty());
        filler.heightProperty().bind(this.stage.heightProperty());
        filler.setFill(Color.AZURE);

        Pane frostPane = new Pane(frost);
        frostPane.setEffect(frostEffect);

        StackPane frostView = new StackPane(
                filler,
                frostPane
        );

        Rectangle clipShape = new Rectangle(0, 0, this.stage.getWidth(), this.stage.getHeight());
        clipShape.heightProperty().bind(this.stage.heightProperty());
        clipShape.widthProperty().bind(this.stage.widthProperty());
        frostView.setClip(clipShape);

        return frostView;
    }
}
