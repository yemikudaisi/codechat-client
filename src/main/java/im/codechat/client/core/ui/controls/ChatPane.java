package im.codechat.client.core.ui.controls;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * The description for ChatPane class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/28/2017
 */
public class ChatPane extends AnchorPane {

    private String jid;
    private ChatEntryContainer chatEntryArea;

    public Node getChild(String childId){
        for (int i = 0; i < this.getChildren().size(); i++){
            Node n = this.getChildren().get(i);
            String id = n.getId();
            if (id == childId){
                return n;
            }
        }
        return null;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public ChatEntryContainer getChatEntryArea() {
        return chatEntryArea;
    }

    public void setChatEntryArea(ChatEntryContainer chatEntryArea) {
        this.chatEntryArea = chatEntryArea;
    }
}
