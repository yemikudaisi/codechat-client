package im.codechat.client.core.ui;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The description for BaseController class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class BaseController implements Initializable{
    private URL url;
    private ResourceBundle resourceBundle;
    private boolean isInitialized = false;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isInitialized = true;
        this.setUrl(location);
        this.setResourceBundle(resources);
    }

    public boolean isInitialized(){
        return isInitialized;
    }
}
