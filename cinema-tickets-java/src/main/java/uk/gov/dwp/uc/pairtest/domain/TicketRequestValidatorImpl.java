package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Implements validation logic to check a group of Ticket purchase requests
 */
public class TicketRequestValidatorImpl implements TicketRequestValidator {

    private static final String A_TICKET_REQUEST_MUST_HAVE_AN_ACCOUNT_ID_GREATER_THAN_ZERO = "A ticket request must have an account id greater than zero";
    private static final String THERE_MUST_BE_AT_LEAST_ONE_TICKET_IN_A_TICKET_REQUEST = "There must be at least one ticket in a ticket request";
    private static final String THERE_CANNOT_BE_MORE_THAN_TWENTY_TICKETS_IN_A_TICKET_REQUEST = "There cannot be more than twenty tickets in a ticket request";
    private static final String THERE_MUST_BE_AT_LEAST_ONE_ADULT_IN_A_TICKET_REQUEST = "There must be at least one adult in a ticket request";

    private static final Logger LOGGER = Logger.getLogger(TicketRequestValidatorImpl.class.getName());

    private static final int MIN_ACCOUNT_ID = 1;
    private static final int MIN_REQUESTS = 1;
    private static final int MAX_REQUESTS = 20;

    /**
     * @param accountId
     * @param ticketTypeRequests
     * @return the result of successful validation of a group of ticket purchase requests
     * @throws InvalidPurchaseException
     */
    @Override
    public void validate(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        LOGGER.log(Level.INFO, "Ticket Requests being validated");

        //Check accountId is valid i.e > zero
        if (accountId < MIN_ACCOUNT_ID) {
            throw new InvalidPurchaseException(A_TICKET_REQUEST_MUST_HAVE_AN_ACCOUNT_ID_GREATER_THAN_ZERO);
        }

        if (ticketTypeRequests.length < MIN_REQUESTS) {
            throw new InvalidPurchaseException(THERE_MUST_BE_AT_LEAST_ONE_TICKET_IN_A_TICKET_REQUEST);
        }

        boolean adultBooked = Stream.of(ticketTypeRequests).anyMatch(req -> {
            return req.getTicketType() == Type.ADULT;
        });
        if (!adultBooked) {
            throw new InvalidPurchaseException(THERE_MUST_BE_AT_LEAST_ONE_ADULT_IN_A_TICKET_REQUEST);
        }

        int ticketsRequested = Stream.of(ticketTypeRequests).mapToInt(t -> t.getNoOfTickets()).sum();
        if (ticketsRequested > MAX_REQUESTS) {
            throw new InvalidPurchaseException(THERE_CANNOT_BE_MORE_THAN_TWENTY_TICKETS_IN_A_TICKET_REQUEST);
        }

    }

}
