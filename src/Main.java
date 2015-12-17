import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    private StackPane root;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.root = new StackPane();
        stage = primaryStage;

        Controller controller = new Controller(stage, root);
        controller.initDisplay();

        stage.setResizable(true);
        Scene scene = new Scene(this.root, 500, 500);
        scene.getStylesheets().add("css/custom.css");
        stage.setScene(scene);


        stage.setTitle("Colors");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
