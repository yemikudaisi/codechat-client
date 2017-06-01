package im.codechat.client.core.xmpp.extensions.codechat;

import im.codechat.client.core.xmpp.XmppManager;
import rocks.xmpp.addr.Jid;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents any CodeChat model that can be comapared
 * against a CodeChat offer store
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 * @see CodeChatSession
 */
@XmlTransient
public abstract class CodeChatSessionComparator {

    @XmlAttribute
    private String key;

    /**
     * Returns true if given key is same with instance key and the sender
     * of this extension's message is the same as the store's offer destination
     *
     * @param store The store to check against
     * @param responseFrom The sender of this message
     * @return true if the extension is for the store
     */
    public boolean isForStore(CodeChatSession store, Jid responseFrom){
        if(XmppManager.getLocalDomainJid(responseFrom).equals(XmppManager.getLocalDomainJid(store.getOfferTo()))){
            if(this.key.equals(store.getOffer().getKey())){
                return true;
            }
        }
        return false;
    }
}
