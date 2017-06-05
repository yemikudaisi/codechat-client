package im.codechat.client;

import im.codechat.client.view.login.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login/LoginView.fxml"));
        LoginViewController login = new LoginViewController();
        loader.setController(login);
        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 275);
        login.setScene(scene);
        primaryStage.setTitle("CodeChat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
