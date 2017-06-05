package im.codechat.client.core.ui;

import im.codechat.client.core.exception.ComponentViewNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The description for BaseComponentController class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/18/2017
 */
public class BaseComponentController extends BaseController{

    protected Pane pane;

    public Pane getPane() throws ComponentViewNotFoundException {
        if(this.pane == null)
            try {
                this.pane = FXMLLoader.load(getClass().getResource(this.getClass().getSimpleName()+".fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        return this.pane;
    }

    public void setPane(Pane pane){
        this.pane = pane;
    }

}
