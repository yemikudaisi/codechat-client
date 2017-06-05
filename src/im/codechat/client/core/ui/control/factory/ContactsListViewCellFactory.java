package im.codechat.client.core.ui.control.factory;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import im.codechat.client.core.application.AppManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.im.roster.model.Contact;

import java.util.HashMap;

/**
 * The description for ContactsListViewCellFactory class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class ContactsListViewCellFactory implements Callback<ListView<Contact>,ListCell<Contact>> {
    @Override
    public ListCell<Contact> call(ListView<Contact> param) {
        ListCell<Contact> cell = new ListCell<Contact>(){

            @Override
            protected void updateItem(Contact contact, boolean isEmpty) {
                super.updateItem(contact, isEmpty);

                setGraphic(null);
                setText(null);
                if (contact != null) {
                    HBox hBox = new HBox();

                    Label name = new Label(contact.getName().substring(0, 1).toUpperCase() + contact.getName().substring(1));
                    name.setFont(new Font(14));
                    name.setPadding(new Insets(10,0,10,0));
                    name.setAlignment(Pos.TOP_LEFT);
                    name.setTextFill(Color.WHITE);
                    name.setFont(Font.font(
                            Font.getDefault().getFamily(),
                            FontWeight.BOLD, FontPosture.REGULAR,
                            Font.getDefault().getSize() + 2
                    ));

                    Text status = new Text();
                    Font statusFont = Font.font(
                            Font.getDefault().getFamily(),
                            //FontPosture.ITALIC,
                            Font.getDefault().getSize()
                    );
                    status.setFill(Color.web("#CCCCCC"));
                    status.setFont(statusFont);
                    name.setAlignment(Pos.TOP_LEFT);

                    MaterialDesignIconView userIcon = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT);
                    //Presence p = AppManager.getChatManager().getClient().getManager(PresenceManager.class).getPresence(contact.getJid());
                    userIcon.setSize("28px");
                    HashMap<String, Presence> list = AppManager.getChatManager().getPresences();
                    if(list.containsKey(AppManager.getChatManager().getLocalDomainJid(contact.getJid()))){

                        Presence p = list.get(AppManager.getChatManager().getLocalDomainJid(contact.getJid()));
                        if(null != list.get(AppManager.getChatManager().getLocalDomainJid(contact.getJid())).getShow()){
                            switch (p.getShow()) {
                                case AWAY:
                                    status.setText("Away");
                                    userIcon.setFill(Color.web("#D7CA2D"));
                                    break;
                                case XA:
                                    status.setText("Extended away");
                                    userIcon.setFill(Color.web("#D7CA2D"));
                                    break;
                                case DND:
                                    status.setText("Do not disturb");
                                    userIcon.setFill(Color.web("#CE4D35"));
                                    break;
                                case CHAT:
                                    status.setText("Chat");
                                    userIcon.setFill(Color.web("#7DB829"));
                                    break;
                                default:
                                    status.setText("Unknown");
                                    userIcon.setFill(Color.web("#7DB829"));
                                    break;
                            }
                        }else{ // show is null when an online status is sent
                            if(p.getType() != Presence.Type.UNAVAILABLE) {
                                status.setText("Online");
                                userIcon.setFill(Color.web("#7DB829"));
                            }
                            else {
                                status.setText("Offline");
                                userIcon.setFill(Color.GREY);
                            }
                        }
                    }else{
                        status.setText("Offline");
                        userIcon.setFill(Color.GREY);
                    }

                    HBox left = new HBox();
                    VBox right = new VBox();
                    name.setPadding(new Insets(0));
                    name.setAlignment(Pos.TOP_LEFT);
                    right.setAlignment(Pos.TOP_LEFT);
                    VBox.setMargin(userIcon,new Insets(10,0,0,0));
                    //VBox.setVgrow(userIcon, Priority.ALWAYS);
                    //VBox.setVgrow(right, Priority.ALWAYS);
                    //VBox.setVgrow(right, Priority.ALWAYS);
                    left.getChildren().add(userIcon);
                    right.getChildren().addAll(name,status);
                    //hBox.getChildren().addAll(userIcon, name, status);
                    //hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.getChildren().addAll(left,right);
                    hBox.setSpacing(10);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}
