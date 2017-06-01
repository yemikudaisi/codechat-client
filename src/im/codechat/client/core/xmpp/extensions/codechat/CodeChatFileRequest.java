package im.codechat.client.core.xmpp.extensions.codechat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The description for CodeChatFileRequest class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */

@XmlRootElement(name = "codeChatFileRequest", namespace = "http://xmpp.rocks")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeChatFileRequest extends CodeChatSessionComparator {
}
