package im.codechat.client.core.chat;

import rocks.xmpp.core.stanza.model.Message;

/**
 * The description for MessageQueue class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class MessageQueue {
    private Message message;
    private MessageDirections direction;

    public MessageQueue(Message message, MessageDirections direction){
        this.message = message;
        this.direction = direction;
    }

    public Message getMessage() {
        return message;
    }

    public MessageDirections getDirection() {
        return direction;
    }
}
