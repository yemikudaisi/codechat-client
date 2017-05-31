package im.codechat.client.core.application;

import im.codechat.client.core.chat.extensions.codechat.CodeChatOfferStore;
import im.codechat.client.core.chat.extensions.codechat.CodeChatOfferStoreApprovals;
import im.codechat.client.core.chat.extensions.codechat.CodeChatOfferStoreContainers;
import im.codechat.client.core.chat.extensions.codechat.ICodeChatManager;
import im.codechat.client.core.chat.extensions.codechat.exceptions.OfferStoreNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The description for CodeChatManger class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public class CodeChatManger implements ICodeChatManager {

    private List<CodeChatOfferStore> pendingOfferStoreContainer;
    private List<CodeChatOfferStore> approvedGuestOfferStoresContainer;
    private List<CodeChatOfferStore> approvedHostOfferStoresContainer;

    public CodeChatManger(){
        pendingOfferStoreContainer = new ArrayList<>();
        approvedGuestOfferStoresContainer = new ArrayList<>();
        approvedHostOfferStoresContainer = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    public CodeChatOfferStore findOfferStore(String keyToFind, CodeChatOfferStoreContainers searchType) throws OfferStoreNotFoundException {
        switch (searchType){
            case PENDING:
                return findOfferStore(keyToFind, pendingOfferStoreContainer);
            case APPROVED_GUESTS:
                return findOfferStore(keyToFind, approvedGuestOfferStoresContainer);
            case APPROVED_HOSTS:
                return findOfferStore(keyToFind, approvedGuestOfferStoresContainer);
            default:
                throw new OfferStoreNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     */
    public CodeChatOfferStore findOfferStore(String keyToFind, List<CodeChatOfferStore> containerToSearch) throws OfferStoreNotFoundException {
        for(CodeChatOfferStore s: containerToSearch){
            if(keyToFind.equals(s.getOffer().getKey())){
                return s;
            }
        }
        throw new OfferStoreNotFoundException();
    }

    /**
     * {@inheritDoc}
     */
    public void approveOfferStore(String keyToApprove, CodeChatOfferStoreApprovals approvalType) throws OfferStoreNotFoundException{
        CodeChatOfferStore store = findOfferStore(keyToApprove, pendingOfferStoreContainer);

        switch (approvalType){
            case GUEST:
                for(Iterator<CodeChatOfferStore> iter = approvedGuestOfferStoresContainer.listIterator(); iter.hasNext();){

                }
                return;
            case HOST:
                return;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<CodeChatOfferStore> getPendingOfferStoreContainer() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<CodeChatOfferStore> getApprovedGuestOfferStoreContainer() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<CodeChatOfferStore> getApprovedHostOfferStoreContainer() {
        return null;
    }
}
