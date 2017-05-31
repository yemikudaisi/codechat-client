package im.codechat.client.core.application;

import im.codechat.client.core.chat.extensions.codechat.CodeChatOfferStore;

import java.util.ArrayList;
import java.util.List;

/**
 * The description for CodeChatManger class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public class CodeChatManger {
    static List<CodeChatOfferStore> pendingOfferStoreContainer = new ArrayList<>();
    static List<CodeChatOfferStore> approvedOfferStoresContainer = new ArrayList<>();
}
