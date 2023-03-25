package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Implements calculation logic to determine seat reservations and total cost for a group of Ticket purchase requests
 */
public class TicketRequestCalculatorImpl implements TicketRequestCalculator {

    private static final Logger LOGGER = Logger.getLogger(TicketRequestCalculatorImpl.class.getName());

    private static final int MIN_ACCOUNT_ID = 1;
    private static final int MIN_REQUESTS = 1;
    private static final int MAX_REQUESTS = 20;

    /**
     * @param accountId
     * @param ticketTypeRequests
     * @return the result of the successful calculation of a group of ticket purchase requests
     * @throws InvalidPurchaseException
     */
    @Override
    public TicketRequestCalculationResult calculate(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        LOGGER.log(Level.INFO, String.format("Ticket Requests being processed to determine seat reservations and total ticket cost for Account Id %s ", accountId));

        int seatsToReserve = Stream.of(ticketTypeRequests).mapToInt(t -> t.getSeatsRequired() * t.getTicketType().getSeatMultiplier()).sum();

        BigDecimal totalCost = Stream.of(ticketTypeRequests).map(TicketTypeRequest::getCostOfTickets).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TicketRequestCalculationResult(seatsToReserve, totalCost);
    }

}
