package uk.gov.dwp.uc.pairtest.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    //Duplicated in TicketRequestValidatorTest... maybe should be rationalised
    private static final TicketTypeRequest REQUEST_ADULT_1 = new TicketTypeRequest(Type.ADULT, 1);
    private static final TicketTypeRequest REQUEST_ADULT_7 = new TicketTypeRequest(Type.ADULT, 7);
    private static final TicketTypeRequest REQUEST_CHILD_1 = new TicketTypeRequest(Type.CHILD, 1);
    private static final TicketTypeRequest REQUEST_CHILD_7 = new TicketTypeRequest(Type.CHILD, 7);
    private static final TicketTypeRequest REQUEST_INFANT_1 = new TicketTypeRequest(Type.INFANT, 1);
    private static final TicketTypeRequest REQUEST_INFANT_7 = new TicketTypeRequest(Type.INFANT, 7);

    private TicketService ticketService;
    private TicketPaymentService mockTicketPaymentService;
    private SeatReservationService mockSeatReservationService;

    @Before
    public void setup() {
        mockTicketPaymentService = mock(TicketPaymentServiceImpl.class);
        mockSeatReservationService = mock(SeatReservationServiceImpl.class);
        ticketService = new TicketServiceImpl(mockTicketPaymentService, mockSeatReservationService);
    }

    @Test
    public void purchaseTickets_validAccountId_callsThirdPartyServices() {

        try {
            ticketService.purchaseTickets(1L, REQUEST_ADULT_1);

            verify(mockTicketPaymentService, times(1)).makePayment(1, 2000);
            verify(mockSeatReservationService, times(1)).reserveSeat(1, 1);

        } catch (InvalidPurchaseException ipe) {
            fail("Did not expect an InvalidPurchaseException to be thrown");
        }
    }

    @Test
    public void purchaseTickets_zeroAccountId_throwsInvalidPurchaseException() {

        try {
            ticketService.purchaseTickets(0L, REQUEST_ADULT_1);

            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("A ticket request must have an account id greater than zero"));
        }
    }

    @Test
    public void purchaseTickets_zeroTickets_throwsInvalidPurchaseException() {

        try {
            ticketService.purchaseTickets(1L);
            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("There must be at least one ticket in a ticket request"));
        }

    }

    @Test
    public void purchaseTickets_zeroAdultTickets_throwsInvalidPurchaseException() {

        try {
            ticketService.purchaseTickets(1L, REQUEST_CHILD_1, REQUEST_INFANT_1);
            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("There must be at least one adult in a ticket request"));
        }

    }

    @Test
    public void purchaseTickets_overTwentyTickets_throwsInvalidPurchaseException() {

        try {
            ticketService.purchaseTickets(1L, REQUEST_ADULT_7, REQUEST_CHILD_7, REQUEST_INFANT_7);
            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("There cannot be more than twenty tickets in a ticket request"));
        }

    }
}
