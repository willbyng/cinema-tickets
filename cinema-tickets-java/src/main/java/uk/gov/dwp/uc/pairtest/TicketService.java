package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * Interface for service to facilitate ticket purchase
 */
public interface TicketService {

    /**
     * @param accountId
     * @param ticketTypeRequests
     * @throws InvalidPurchaseException
     */
    void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException;

}
