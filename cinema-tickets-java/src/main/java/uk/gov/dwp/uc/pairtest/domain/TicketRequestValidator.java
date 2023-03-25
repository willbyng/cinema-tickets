package uk.gov.dwp.uc.pairtest.domain;

public interface TicketRequestValidator {

    /**
     * @param accountId
     * @param ticketTypeRequests
     * @return TicketRequestValidationResult
     */
    void validate(Long accountId, TicketTypeRequest... ticketTypeRequests);

}
