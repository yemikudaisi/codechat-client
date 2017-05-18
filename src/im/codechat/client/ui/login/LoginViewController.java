package im.codechat.client.ui.login;

import java.io.IOException;

import im.codechat.client.application.Globals;
import im.codechat.client.core.ui.BaseController;
import im.codechat.client.ui.main.MainViewController;
import im.codechat.client.xmpp.XMPPHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
            MainViewController mainCtrl = new MainViewController();
            if(Globals.getXmppHandler().login(getLoginUsername(),getLoginPassword())){
                this.tryClose();
                mainCtrl.showView();
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

