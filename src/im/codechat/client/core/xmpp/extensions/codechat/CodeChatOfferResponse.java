package im.codechat.client.core.xmpp.extensions.codechat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a response to a code xmpp offer
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/30/2017
 * @see CodeChatOffer
 */
@XmlRootElement(name = "codeChatOfferResponse", namespace = "http://xmpp.rocks")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeChatOfferResponse extends CodeChatSessionComparator {

    @XmlAttribute
    private boolean accept;

    @XmlAttribute
    private String key;

    private CodeChatOfferResponse() {
        // Private no-args default constructor for JAXB.
    }

    public CodeChatOfferResponse(boolean response, CodeChatOffer offer) {

        this.accept = response;
        this.key = offer.getKey();
    }

    public boolean isAccept() {
        return accept;
    }

    public String getKey(){ return this.key; }
}