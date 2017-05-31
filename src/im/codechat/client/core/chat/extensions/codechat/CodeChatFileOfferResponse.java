package im.codechat.client.core.chat.extensions.codechat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The description for CodeChatFileOfferResponse class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
@XmlRootElement(name = "codeChatFileOfferResponse", namespace = "http://xmpp.rocks")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeChatFileOfferResponse extends CodeChatOfferStoreComparer {
}
