package im.codechat.client.core;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import im.codechat.client.core.application.AppManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.im.roster.model.Contact;
import rocks.xmpp.im.subscription.PresenceManager;

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
            protected void updateItem(Contact contact, boolean bln) {
                super.updateItem(contact, bln);

                setGraphic(null);
                setText(null);
                if (contact != null) {
                    HBox hBox = new HBox();

                    Label name = new Label(contact.getName());
                    name.setFont(new Font(14));
                    name.setPadding(new Insets(10,0,10,0));


                    MaterialDesignIconView userIcon = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT);
                    Presence p = AppManager.getXmppManager().getClient().getManager(PresenceManager.class).getPresence(contact.getJid());
                    userIcon.setSize("24px");

                    hBox.getChildren().addAll(userIcon, name);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setSpacing(10);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}
