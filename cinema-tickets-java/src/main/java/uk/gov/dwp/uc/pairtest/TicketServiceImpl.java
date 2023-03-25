package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketRequestCalculationResult;
import uk.gov.dwp.uc.pairtest.domain.TicketRequestCalculator;
import uk.gov.dwp.uc.pairtest.domain.TicketRequestCalculatorImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketRequestValidator;
import uk.gov.dwp.uc.pairtest.domain.TicketRequestValidatorImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service to facilitate ticket purchase
 */
public class TicketServiceImpl implements TicketService {
    private static final TicketRequestValidator ticketRequestValidator = new TicketRequestValidatorImpl();
    private static final TicketRequestCalculator ticketRequestCalculator = new TicketRequestCalculatorImpl();
    private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class.getName());
    /**
     * Should only have private methods other than the one below.
     */
    private final TicketPaymentService ticketPaymentService;
    private final SeatReservationService seatReservationService;

    public TicketServiceImpl(TicketPaymentService ticketPaymentService, SeatReservationService seatReservationService) {
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
    }

    /**
     * @param accountId
     * @param ticketTypeRequests
     * @throws InvalidPurchaseException
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        LOGGER.log(Level.INFO, String.format("Tickets being purchased for account id %s", accountId));

        ticketRequestValidator.validate(accountId, ticketTypeRequests);

        TicketRequestCalculationResult result = ticketRequestCalculator.calculate(accountId, ticketTypeRequests);

        makeThirdPartyServiceCalls(accountId, result);

    }

    private void makeThirdPartyServiceCalls(Long accountId, TicketRequestCalculationResult result) {

        //Note: we are using BigDecimal as a preferred option for holding monetary values.
        //However the Payment Service accepts int values, which probably means that monetary sums are held in 'pence'
        //Without being able to clarify this the following multiplies values for submission by 100
        int calculatedPriceInPence = result.getCalculatedPrice().intValue() * 100;

        //No failures are handled on the calls to these services, though in the real world we would need to....
        ticketPaymentService.makePayment(accountId, calculatedPriceInPence);

        seatReservationService.reserveSeat(accountId, result.getSeatsToReserve());
    }

}
