package im.codechat.client.view.login;

import java.io.IOException;

import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.ui.BaseViewController;
import im.codechat.client.core.chat.ChatManager;
import im.codechat.client.view.main.MainViewController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.XmppSession;

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

    @FXML TextField txtUsername;
    @FXML PasswordField txtPassword;
    @FXML Label status;
    @FXML ProgressIndicator loginProgressIndicator;

    public LoginViewController(){
        try {
            ChatManager handler = ChatManager.instance();
            AppManager.setChatManager(handler);
        } catch (XmppException e) {
            e.printStackTrace();
        }

        /**
        try {
            new MainViewController().showView();
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
        LoginViewController temp = this;
        Task task = new Task<Void>() {

            @Override public Void call() {

                try{
                    boolean result = AppManager.getChatManager().login(getLoginUsername(),getLoginPassword());
                    if(result){
                        this.cancel();
                    }
                    else{
                        this.failed();
                    }
                }catch (XmppException e) {
                    XmppSession.Status status = AppManager.getChatManager().getClient().getStatus();
                    e.printStackTrace();
                    this.failed();
                }
                return null;
            }
        };

        task.setOnRunning(event -> loginProgressIndicator.setVisible(true));
        task.setOnSucceeded(event -> loginFail());
        task.setOnCancelled(event -> loginSuccess());
        new Thread(task).start();
    }

    private void loginSuccess(){
        loginProgressIndicator.setVisible(false);
        MainViewController mainCtrl = AppManager.getController();
        AppManager.setSessionUsername(getLoginUsername());
        try {
            mainCtrl.showView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tryClose();
    }

    private void loginFail(){
        loginProgressIndicator.setVisible(false);
        setStatus("Login Failed");
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

