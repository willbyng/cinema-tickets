package uk.gov.dwp.uc.pairtest.domain;

public interface TicketRequestCalculator {

    /**
     * @param accountId
     * @param ticketTypeRequests
     * @return TicketRequestCalculationResult
     */
    TicketRequestCalculationResult calculate(Long accountId, TicketTypeRequest... ticketTypeRequests);

}
