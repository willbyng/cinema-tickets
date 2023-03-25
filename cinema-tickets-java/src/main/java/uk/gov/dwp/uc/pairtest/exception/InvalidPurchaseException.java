package uk.gov.dwp.uc.pairtest.exception;

/**
 *
 */
public class InvalidPurchaseException extends RuntimeException {

    /**
     * @param errorMessage
     */
    public InvalidPurchaseException(String errorMessage) {
        super(errorMessage);
    }


}
