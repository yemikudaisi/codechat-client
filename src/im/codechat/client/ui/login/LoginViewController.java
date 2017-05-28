package im.codechat.client.ui.login;

import java.io.IOException;

import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.ui.BaseViewController;
import im.codechat.client.ui.main.AppViewController;
import im.codechat.client.core.xmpp.XmppManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
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
public class LoginViewController  extends BaseViewController {

    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label status;
    public LoginViewController(){
        try {
            XmppManager handler = XmppManager.instance();
            AppManager.setXmppManager(handler);
        } catch (XmppException e) {
            e.printStackTrace();
        }

        /**
        try {
            new AppViewController().showView();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void setScene(Scene scene){
        super.setScene(scene);
        this.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                    case ENTER:
                        loginAction(new ActionEvent());
                        break;
                }
            }
        });
    }

    @FXML
    private void loginAction(ActionEvent ev){
        try{
            AppViewController mainCtrl = AppManager.getController();
            if(AppManager.getXmppManager().login(getLoginUsername(),getLoginPassword())){
                AppManager.setSessionUsername(getLoginUsername());
                mainCtrl.showView();
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

