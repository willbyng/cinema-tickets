package uk.gov.dwp.uc.pairtest.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class TicketRequestValidatorTest {

    protected static final long INVALID_ACCOUNT_ID = 0l;
    protected static final long VALID_ACCOUNT_ID = 1l;

    //TODO Duplicated in TicketServiceTest... maybe should be rationalised
    protected static final TicketTypeRequest REQUEST_ADULT_1 = new TicketTypeRequest(Type.ADULT, 1);
    protected static final TicketTypeRequest REQUEST_ADULT_7 = new TicketTypeRequest(Type.ADULT, 7);
    protected static final TicketTypeRequest REQUEST_CHILD_1 = new TicketTypeRequest(Type.CHILD, 1);
    protected static final TicketTypeRequest REQUEST_CHILD_7 = new TicketTypeRequest(Type.CHILD, 7);
    protected static final TicketTypeRequest REQUEST_INFANT_1 = new TicketTypeRequest(Type.INFANT, 1);
    protected static final TicketTypeRequest REQUEST_INFANT_7 = new TicketTypeRequest(Type.INFANT, 7);

    private static final TicketRequestValidator validator = new TicketRequestValidatorImpl();

    // QUESTIONS
    // 1. Are there any restrictions on how many infants can be sitting on an adult lap
    // 2. Should we validate all errors from the payment submission, or (as is) just throw the first one encountered)

    @Test
    public void validate_zeroAccountId_throwsInvalidPurchaseException() {

        try {
            validator.validate(INVALID_ACCOUNT_ID, REQUEST_ADULT_7);

            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("A ticket request must have an account id greater than zero"));
        }
    }

    @Test
    public void validate_zeroTickets_throwsInvalidPurchaseException() {

        try {
            validator.validate(VALID_ACCOUNT_ID);
            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("There must be at least one ticket in a ticket request"));
        }

    }

    @Test
    public void validate_zeroAdultTickets_throwsInvalidPurchaseException() {

        try {
            validator.validate(VALID_ACCOUNT_ID, REQUEST_CHILD_1, REQUEST_INFANT_1);
            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("There must be at least one adult in a ticket request"));
        }

    }

    @Test
    public void validate_overTwentyTickets_throwsInvalidPurchaseException() {

        try {
            validator.validate(VALID_ACCOUNT_ID, REQUEST_ADULT_7, REQUEST_CHILD_7, REQUEST_INFANT_7);
            fail("Expected an InvalidPurchaseException to be thrown");
        } catch (InvalidPurchaseException ipe) {
            assertThat(ipe.getMessage(), is("There cannot be more than twenty tickets in a ticket request"));
        }

    }

}
