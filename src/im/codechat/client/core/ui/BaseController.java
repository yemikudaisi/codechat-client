package im.codechat.client.core.ui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

/**
 *
 * The base controller for all view controllers
 * It allows the controller to dynamically locate its view
 * if the view and the controller are in the same folder
 * and follow the same naming convention
 * example : MyView.fxml, MyViewController.java
 * hence the view can be opened and closed from inheriting
 * controller objects.
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 15/5/17
 */
public abstract class BaseController implements Initializable {

    protected String title;
    private Scene scene;
    private URL url;
    private ResourceBundle resourceBundle;


    public  void tryClose(){
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }

    /**
     * Get the name of the view based on the controllers name
     * i.e SomeViewController will be SomeView
     *
     * @return The name of the view
     */
    public String getViewName(){
        String str = this.getClass().getSimpleName();
        if (str != null && str.length() > 10) {
            str = str.substring(0, str.length()-10);
            return str;
        }
        return "";
    }

    /**
     * Overloaded method to show a not modal and undecorated view
     *
     * @throws IOException
     */
    public void  showView() throws IOException {
        showView(ViewModes.NORMAL);
    }

    /**
     * Show the corresponding view for this controller base on naming convention
     *
     * @param stage The stage to show the scene
     * @throws IOException If location of the view is not found
     */
    public void showView(Stage stage) throws IOException {

    }



    /**
     * Show the corresponding view for this controller base on naming convention
     * @param mode the view mode
     * @throws IOException If location of the view is not found
     */
    public void showView(ViewModes mode) throws IOException {
        //Pane pane = FXMLLoader.load(getClass().getResource(getViewName()+".fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(getViewName()+".fxml"));
        //loader.setRoot(pane);
        loader.setController(this);

        Parent root = loader.load();
        Stage stage = new Stage();

        if(mode == ViewModes.MODAL || mode == ViewModes.MODAL_UNDECORATED)
            stage.initModality(Modality.APPLICATION_MODAL);

        if(mode == ViewModes.MODAL || mode == ViewModes.MODAL_UNDECORATED)
            stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle(getTitle());
        Scene scene = new Scene(root);
        this.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gets the title of displayed on the view.
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setUrl(location);
        this.setResourceBundle(resources);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
