package uk.gov.dwp.uc.pairtest.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Holds the calculated details for the number of seats to be reserved and payment required
 */
public class TicketRequestCalculationResult {

    final private int seatsToReserve;
    final private BigDecimal calculatedPrice;

    /**
     * @param seatsToReserve
     * @param calculatedPrice
     */
    public TicketRequestCalculationResult(final int seatsToReserve, final BigDecimal calculatedPrice) {
        this.seatsToReserve = seatsToReserve;
        this.calculatedPrice = calculatedPrice;
    }

    public int getSeatsToReserve() {
        return seatsToReserve;
    }

    public BigDecimal getCalculatedPrice() {
        return calculatedPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculatedPrice, seatsToReserve);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TicketRequestCalculationResult other = (TicketRequestCalculationResult) obj;
        return Objects.equals(calculatedPrice, other.calculatedPrice)
                && Objects.equals(seatsToReserve, other.seatsToReserve);
    }

    @Override
    public String toString() {
        return "TicketRequestCalculationResult [seatsToReserve=" + seatsToReserve + ", calculatedPrice=" + calculatedPrice
                + "]";
    }


}
