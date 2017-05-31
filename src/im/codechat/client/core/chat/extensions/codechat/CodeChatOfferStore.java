package im.codechat.client.core.chat.extensions.codechat;

import rocks.xmpp.addr.Jid;

/**
 * The description for CodeChatOfferStore class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/30/2017
 */
public class CodeChatOfferStore {
    private Jid offerTo;
    private CodeChatOffer offer;

    public CodeChatOfferStore(Jid to, CodeChatOffer offer){
        this.setOfferTo(to);
        this.setOffer(offer);
    }

    public Jid getOfferTo() {
        return offerTo;
    }

    public void setOfferTo(Jid offerTo) {
        this.offerTo = offerTo;
    }

    public CodeChatOffer getOffer() {
        return offer;
    }

    public void setOffer(CodeChatOffer offer) {
        this.offer = offer;
    }
}
