package im.codechat.client.core.chat.extensions.codechat;

import im.codechat.client.core.chat.extensions.codechat.exceptions.OfferStoreNotFoundException;

import java.util.List;

/**
 * The description for ICodeChatManager class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public interface ICodeChatManager {

    /**
     * Searches for an offer store whose offer's instance has the specified key from
     * a store container determine by the search type
     *
     *
     * @param keyToFind The key to store to find
     * @param searchType The type of container to conduct the search on
     * @return The offer store with an offer that has the key
     * @throws OfferStoreNotFoundException
     */
    CodeChatOfferStore findOfferStore(String keyToFind, CodeChatOfferStoreContainers searchType) throws OfferStoreNotFoundException;

    /**
     * Searches for an offer store whose offer's instance has the specified key from
     * a store container determine by the search type
     *
     *
     * @param keyToFind The key to store to find
     * @param containerToSearch The store container to search in
     * @return The offer store with an offer that has the key
     * @throws OfferStoreNotFoundException
     */
    CodeChatOfferStore findOfferStore(String keyToFind, List<CodeChatOfferStore> containerToSearch) throws OfferStoreNotFoundException;

    /**
     * Remove an offer store from the pending store container
     * and add it to approved guest store container if the CodeChat client is the Host,
     * otherwise simply add the store it to approved hosts store container
     *
     * @param keyToApprove The key of the store to approve
     * @throws OfferStoreNotFoundException
     */
    void approveOfferStore(String keyToApprove, CodeChatOfferStoreApprovals approvalType) throws OfferStoreNotFoundException;

    /**
     * Get container for pending offer stores
     * that was send out by an host (originator)
     *
     * @return List containing pending host offers
     */
    List<CodeChatOfferStore> getPendingOfferStoreContainer();


    /**
     * Get container for approved offer stores sent out by
     * by a CodeChat (you) host that yielded a guest (other) approval
     *
     * @return List containing offers sent by host (you) approved by a guest (other)
     */
    List<CodeChatOfferStore> getApprovedGuestOfferStoreContainer();

    /**
     * Get container for host (other) offers store who response was approved
     * by the CodeChat (you) guest
     *
     * @return List containing offers sent by host (other) approved by a guest (you)
     */
    List<CodeChatOfferStore> getApprovedHostOfferStoreContainer();

}
