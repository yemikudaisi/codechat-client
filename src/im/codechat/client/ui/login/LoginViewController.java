package im.codechat.client.ui.login;

import java.io.IOException;

import com.anchorage.docks.node.DockNode;
import com.anchorage.docks.stations.DockStation;
import com.anchorage.system.AnchorageSystem;
import im.codechat.client.application.Globals;
import im.codechat.client.core.ui.BaseController;
import im.codechat.client.ui.main.MessengerComponent;
import im.codechat.client.xmpp.XMPPHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import rocks.xmpp.core.XmppException;

/**
 *
 * The Controller for the login view
 * accepts client user name and password
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 15/5/17
 */
public class LoginViewController  extends BaseController {

    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label status;
    public LoginViewController(){
        try {
            XMPPHandler handler = XMPPHandler.instance();
            Globals.setXmppHandler(handler);
        } catch (XmppException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loginAction(ActionEvent ev){
        try{

            MessengerComponent mainCtrl = new MessengerComponent();
            if(Globals.getXmppHandler().login(getLoginUsername(),getLoginPassword())){
                DockStation station = AnchorageSystem.createStation();

                Scene scene = new Scene(station, 1024, 768);
                DockNode messengerNode = AnchorageSystem.createDock("Messenger", mainCtrl.getPane());
                messengerNode.dock(station, DockNode.DockPosition.LEFT);
                messengerNode.floatableProperty().set(false);
                AnchorageSystem.installDefaultStyle();
                Stage stage = new Stage();
                stage.setTitle("CodeChat");
                stage.setScene(scene);
                stage.show();
                this.tryClose();
            }
            else{
                setStatus("Login Failed");
            }

        }catch(XmppException e){
            new Alert(AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLoginUsername(){
        return txtUsername.getText();
    }

    private String getLoginPassword(){
        return txtPassword.getText();
    }

    private void setStatus(String text){
        this.status.setText(text);
    }
}

