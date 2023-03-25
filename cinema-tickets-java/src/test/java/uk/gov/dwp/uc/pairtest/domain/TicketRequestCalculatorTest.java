package uk.gov.dwp.uc.pairtest.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TicketRequestCalculatorTest {

    protected static final long INVALID_ACCOUNT_ID = 0l;
    protected static final long VALID_ACCOUNT_ID = 1l;

    //TODO Duplicated in TicketServiceTest... maybe should be rationalised
    protected static final TicketTypeRequest REQUEST_ADULT_1 = new TicketTypeRequest(Type.ADULT, 1);
    protected static final TicketTypeRequest REQUEST_ADULT_7 = new TicketTypeRequest(Type.ADULT, 7);
    protected static final TicketTypeRequest REQUEST_CHILD_1 = new TicketTypeRequest(Type.CHILD, 1);
    protected static final TicketTypeRequest REQUEST_CHILD_7 = new TicketTypeRequest(Type.CHILD, 7);
    protected static final TicketTypeRequest REQUEST_INFANT_1 = new TicketTypeRequest(Type.INFANT, 1);
    protected static final TicketTypeRequest REQUEST_INFANT_7 = new TicketTypeRequest(Type.INFANT, 7);

    private static final TicketRequestCalculator calculator = new TicketRequestCalculatorImpl();

    @Test
    public void calculate_1Adult_returnsCorrectTicketCostAndSeatsRequired() {

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(1, BigDecimal.valueOf(20));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_1);

        assertEquals("Requested 1 adult tickets... ", expected, actual);

    }

    @Test
    public void calculate_1Adult1Child_returnsCorrectTicketCostAndSeatsRequired() {

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(2, BigDecimal.valueOf(30));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_1, REQUEST_CHILD_1);

        assertEquals("Requested 1 adult, 1 child tickets... ", expected, actual);

    }

    @Test
    public void calculate_1Adult1Infant_returnsCorrectTicketCostAndSeatsRequired() {

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(1, BigDecimal.valueOf(20));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_1, REQUEST_INFANT_1);

        assertEquals("Requested 1 adult, 1 infant tickets... ", expected, actual);

    }

    @Test
    public void calculate_1Adult1Child1Infant_returnsCorrectTicketCostAndSeatsRequired() {

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(2, BigDecimal.valueOf(30));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_1, REQUEST_CHILD_1, REQUEST_INFANT_1);

        assertEquals("Requested 1 adult, 1 child, 1 infant tickets... ", expected, actual);

    }

    @Test
    public void calculate_1Adult7Child_returnsCorrectTicketCostAndSeatsRequired() {

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(8, BigDecimal.valueOf(90));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_1, REQUEST_CHILD_7);

        assertEquals("Requested 1 adult, 7 child tickets... ", expected, actual);

    }

    @Test
    public void calculate_1Adult7Infant_returnsCorrectTicketCostAndSeatsRequired() {

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(1, BigDecimal.valueOf(20));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_1, REQUEST_INFANT_7);

        assertEquals("Requested 1 adult, 7 infant tickets... ", expected, actual);

    }

    @Test
    public void calculate_7Adult1Child1Infant_returnsCorrectTicketCostAndSeatsRequired() {

        TicketTypeRequest req1 = new TicketTypeRequest(Type.ADULT, 7);
        TicketTypeRequest req2 = new TicketTypeRequest(Type.CHILD, 1);
        TicketTypeRequest req3 = new TicketTypeRequest(Type.INFANT, 1);

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(8, BigDecimal.valueOf(150));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_7, REQUEST_CHILD_1, REQUEST_INFANT_1);

        assertEquals("Requested 7 adult, 1 child, 1 infant tickets... ", expected, actual);

    }

    @Test
    public void calculate_mixedTicketRequestTypes_returnsCorrectTicketCostAndSeatsRequired() {

        TicketRequestCalculationResult expected = new TicketRequestCalculationResult(16, BigDecimal.valueOf(300));

        TicketRequestCalculationResult actual = calculator.calculate(VALID_ACCOUNT_ID, REQUEST_ADULT_7, REQUEST_CHILD_1, REQUEST_INFANT_1, REQUEST_CHILD_1, REQUEST_ADULT_7);

        assertEquals("Requested a mixed up group of tickets... ", expected, actual);

    }
}
