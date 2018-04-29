package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage); // **Set the Stage**
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.setStyle("-fx-background-color: #f0ede5;");
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1340, 800));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
