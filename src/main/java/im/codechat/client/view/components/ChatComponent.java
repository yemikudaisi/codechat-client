package im.codechat.client.view.components;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.application.CodeChatManager;
import im.codechat.client.core.ui.control.CodeChatTab;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatOffer;
import im.codechat.client.core.xmpp.message.MessageDirections;
import im.codechat.client.core.xmpp.message.MessageQueueItem;
import im.codechat.client.core.ui.*;
import im.codechat.client.core.ui.control.ChatEntryContainer;
import im.codechat.client.core.ui.control.ChatPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.extensions.chatstates.model.ChatState;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * A reusable component for rendering sent and received message
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/18/2017
 */
public class ChatComponent extends BaseComponentController {

    ChatPane chatPane;
    TabPane tabPane;
    Tab chatTab;

    ChatEntryContainer chatArea;
    TextArea outboundMessageText;
    Label chatContactLabel;
    Button btnSendChat;
    Button btnShareFolder;
    Button btnStartConference;
    Button btnUploadFile;
    Button btnShowContactDetails;
    Button btnShowContactHistory;
    Button btnEncryptConversation;
    List<MessageQueueItem> messageQueueItems;

    private String jid;

    public ChatComponent(){
        messageQueueItems = new ArrayList<>();
        initializeComponents();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location,resources);
        chatContactLabel.setText(this.getJid());
    }


    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
        chatPane.setJid(jid);
        chatContactLabel.setText(jid);

    }

    public void sendChat(){
        Message msg = AppManager.getChatManager().buildMessage(this.getJid(),outboundMessageText.getText());
        msg.setType(Message.Type.CHAT);
        chatArea.add(msg, MessageDirections.OUTBOUND);
        msg.addExtension(ChatState.ACTIVE);
        AppManager.getChatManager().sendMessage(msg);
        outboundMessageText.clear();
    }

    public void startCodeChat(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select project folder");
        File selectedDirectory = chooser.showDialog(AppManager.getController().getScene().getWindow());
        Message msg = AppManager.getChatManager().buildMessage(this.getJid());
        try {
            String key =  UUID.randomUUID().toString();
            String rootPath = selectedDirectory.getCanonicalPath();
            msg.addExtension(new CodeChatOffer(selectedDirectory.getName(),key));
            CodeChatManager.getInstance().getSessionRootPaths().put(key, rootPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppManager.getChatManager().sendMessage(msg);
    }

    public void initializeComponents(){
        chatContactLabel = new Label();

        chatPane = new ChatPane();
        chatPane.setId("xmpp-pane");
        tabPane = new TabPane();
        chatTab = new Tab("Chat");
        chatTab.setClosable(false);

        chatArea = new ChatEntryContainer();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        chatArea.getColumnConstraints().add(columnConstraints);
        HBox.setHgrow(chatArea, Priority.ALWAYS);
        VBox.setVgrow(chatArea, Priority.ALWAYS);

        outboundMessageText = new TextArea();
        outboundMessageText.setMaxHeight(50);
        outboundMessageText.setMinHeight(50);
        outboundMessageText.setPrefHeight(50);
        HBox.setHgrow(outboundMessageText, Priority.ALWAYS);
        outboundMessageText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != "")
                btnSendChat.setDisable(false);
        });

        btnSendChat = new Button();
        btnSendChat.setDisable(true);
        btnSendChat.getStyleClass().add("accent-button");
        MaterialDesignIconView sendIconView = new MaterialDesignIconView(MaterialDesignIcon.ACCESS_POINT.SEND);
        sendIconView.setFill(Color.WHITE);
        sendIconView.setSize("15px");
        btnSendChat.setGraphic(sendIconView);
        btnSendChat.setAlignment(Pos.TOP_RIGHT);
        btnSendChat.setOnAction(e -> sendChat());

        btnShareFolder = new Button();
        btnShareFolder.getStyleClass().add("accent-button");
        MaterialDesignIconView folderUploadIconView = IconBuilder.get(MaterialDesignIconBuilder.class)
                .fill(Color.WHITE)
                .icon(MaterialDesignIcon.FOLDER_UPLOAD)
                .build();
        btnShareFolder.setGraphic(folderUploadIconView);
        btnShareFolder.setAlignment(Pos.TOP_RIGHT);
        btnShareFolder.setOnAction(e -> startCodeChat());

        AnchorPane rootPane = new AnchorPane();
        VBox rootBox = new VBox();

        //BorderPane topPane = new BorderPane();
        HBox topBox = new HBox();
        topBox.getStyleClass().add("chat-component-top-box");
        HBox topBoxLabel = new HBox();
        topBoxLabel.getChildren().add(chatContactLabel);
        //topPane.setLeft(chatContactLabel);

        HBox topBoxButtons = new HBox();

        btnShowContactDetails = new Button();
        btnStartConference = new Button();
        btnUploadFile = new Button();
        btnShowContactHistory = new Button();
        btnEncryptConversation = new Button();

        btnShowContactDetails.setGraphic(MaterialDesignIconBuilder.get(MaterialDesignIconBuilder.class)
                .fill(Color.GRAY)
                .icon(MaterialDesignIcon.MORE)
                .size("16px")
                .build());
        btnShowContactDetails.getStyleClass().add("chat-action-button");

        btnStartConference.setGraphic(MaterialDesignIconBuilder.get(MaterialDesignIconBuilder.class)
                .fill(Color.GRAY)
                .icon(MaterialDesignIcon.GOOGLE_CIRCLES_GROUP)
                .size("16px")
                .build());
        btnStartConference.getStyleClass().add("chat-action-button");

        btnUploadFile.setGraphic(MaterialDesignIconBuilder.get(MaterialDesignIconBuilder.class)
                .fill(Color.GRAY)
                .icon(MaterialDesignIcon.UPLOAD)
                .size("16px")
                .build());
        btnUploadFile.getStyleClass().add("chat-action-button");

        btnShowContactHistory.setGraphic(MaterialDesignIconBuilder.get(MaterialDesignIconBuilder.class)
                .fill(Color.GRAY)
                .icon(MaterialDesignIcon.CLOCK)
                .size("16px")
                .build());
        btnShowContactHistory.getStyleClass().add("chat-action-button");

        btnEncryptConversation.setGraphic(MaterialDesignIconBuilder.get(MaterialDesignIconBuilder.class)
                .fill(Color.GRAY)
                .icon(MaterialDesignIcon.FILE_LOCK)
                .size("16px")
                .build());
        btnEncryptConversation.getStyleClass().add("chat-action-button");

        topBoxButtons.getChildren().add(btnShowContactDetails);
        topBoxButtons.getChildren().add(btnStartConference);
        topBoxButtons.getChildren().add(btnUploadFile);
        topBoxButtons.getChildren().add(btnShowContactHistory);
        topBoxButtons.getChildren().add(btnEncryptConversation);
        topBoxButtons.setSpacing(5);
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBox.getChildren().addAll(topBoxLabel,spacer,topBoxButtons);
        topBox.setPadding(new Insets(10));
        //topPane.setRight(topBoxButtons);

        HBox hBox2 = new HBox();
        hBox2.getStyleClass().add("chat-entry-area");
        hBox2.getChildren().add(outboundMessageText);
        hBox2.getChildren().add(btnSendChat);
        hBox2.getChildren().add(btnShareFolder);
        hBox2.setSpacing(5);
        hBox2.setPadding(new Insets(10,20,10,10));

        rootBox.getChildren().add(topBox);
        rootBox.getChildren().add(chatArea);
        VBox.setMargin(chatArea, new Insets(0,0,10,0));
        rootBox.getChildren().add(hBox2);
        rootBox.setFillWidth(true);

        AnchorPane.setTopAnchor(rootBox,10.0);
        AnchorPane.setRightAnchor(rootBox,0.0);
        AnchorPane.setLeftAnchor(rootBox,0.0);
        AnchorPane.setBottomAnchor(rootBox,0.0);

        AnchorPane chatContent = new AnchorPane();
        chatContent.getChildren().add(rootBox);

        AnchorPane.setTopAnchor(chatPane,0.0);
        AnchorPane.setRightAnchor(chatPane,0.0);
        AnchorPane.setLeftAnchor(chatPane,0.0);
        AnchorPane.setBottomAnchor(chatPane,0.0);

        chatTab.setContent(chatContent);
        tabPane.getTabs().add(chatTab);
        tabPane.getTabs().add(new CodeChatTab("Project - CodeChat"));

        AnchorPane.setTopAnchor(tabPane,0.0);
        AnchorPane.setRightAnchor(tabPane,0.0);
        AnchorPane.setLeftAnchor(tabPane,0.0);
        AnchorPane.setBottomAnchor(tabPane,0.0);

        chatPane.setChatEntryArea(this.chatArea);
        chatPane.getChildren().add(tabPane);
        this.setPane(chatPane);
    }
}
