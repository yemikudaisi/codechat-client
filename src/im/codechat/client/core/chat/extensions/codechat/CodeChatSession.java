package im.codechat.client.core.chat.extensions.codechat;

import rocks.xmpp.addr.Jid;

/**
 * The description for CodeChatSession class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/30/2017
 */
public class CodeChatSession {
    private Jid offerTo = null;
    private Jid offerFrom = null;
    private CodeChatOffer offer;

    /**
     * Public constructor for outbound offer to a guest
     * Note: The flow (Jid-->Offer)
     *
     * @param to The guest Jid
     * @param offer The offer to send
     */
    public CodeChatSession(Jid to, CodeChatOffer offer){
        this.offerTo = to;
        this.offer = offer;
    }

    /**
     * Public constructor for inbound offer from a host
     * Note: The flow (Offer --> Jid)
     *
     * @param offer The offer received
     * @param from The host G\Jid
     */
    public CodeChatSession(CodeChatOffer offer, Jid from){
        this.offerFrom = from;
        this.offer = offer;
    }

    /**
     * Gets the offer's destination or return null if the offer
     * is inbound.
     *
     * @return the receiver Jid
     */
    public Jid getOfferTo(){
        return offerTo;
    }

    /**
     * Gets the offer's sender or return null if the offer
     * is outbound.
     *
     * @return the sender Jid
     */
    public Jid getOfferFrom() {
        return offerFrom;
    }

    public CodeChatOffer getOffer() {
        return offer;
    }

    /**
     * Get the Key to the store's offer
     * @return The key
     */
    public String getKey(){ return getOffer().getKey();}

    @Override
    public boolean equals(Object s){
        if(s.getClass().equals(this.getClass())){
            CodeChatSession otherStore = (CodeChatSession)s;
            if(this.getKey().equals(otherStore.getKey()))
                return true;
        }
        return false;
    }
}
