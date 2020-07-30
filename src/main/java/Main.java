import client.Client;
import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Client client;

    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(() -> {
            try {
                new Main().start(new Stage());
            } catch (Exception e) {
                System.out.println("Error starting app:" + e);
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        System.out.println(getClass().getResource("menu.fxml"));
        loader.setLocation(getClass().getResource("menu.fxml"));
        Parent root = loader.load();
        final Scene scene = new Scene(root);
        Controller controller = loader.getController();
        client = new Client();
        stage.setOnCloseRequest(event -> {
            client.disconnect();
        });
        client.setReadObjectListener(controller.getReadObjectListener());
        client.connect();
        controller.setWriteObjectListener(client.getWriteObjectListener());

        stage.setScene(scene);
        stage.setTitle("Help system");

        stage.setMinHeight(250);
        stage.setMinWidth(500);

        stage.setMaxHeight(800);
        stage.setMaxWidth(1000);

        stage.show();
    }
}
