package im.codechat.client.core.chat.extensions.codechat;

import javax.xml.bind.annotation.*;

/**
 * Represents an offer to share a folder with another user or group
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/30/2017
 */
@XmlRootElement(name = "folderShareOffer", namespace = "http://xmpp.rocks")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeChatOffer {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String key;

    private CodeChatOffer(){
        // Private no-args default constructor for JAXB.
    }

    public CodeChatOffer(String name, String key){
        this.setName(name);
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
