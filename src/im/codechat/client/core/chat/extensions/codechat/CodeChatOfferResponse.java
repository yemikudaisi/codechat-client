package im.codechat.client.core.chat.extensions.codechat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a response to a code chat offer
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/30/2017
 * @see CodeChatOffer
 */
@XmlRootElement(name = "codeChatOfferResponse", namespace = "http://xmpp.rocks")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeChatOfferResponse extends CodeChatOfferStoreComparer {

    @XmlAttribute
    private boolean accept;

    private CodeChatOfferResponse() {
        // Private no-args default constructor for JAXB.
    }

    public CodeChatOfferResponse(boolean response) {
        this.accept = response;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}