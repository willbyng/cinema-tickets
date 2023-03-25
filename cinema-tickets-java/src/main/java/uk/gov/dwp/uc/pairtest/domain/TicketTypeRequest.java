package uk.gov.dwp.uc.pairtest.domain;

import java.math.BigDecimal;

/**
 * Immutable Object
 */
public class TicketTypeRequest {

    final private int noOfTickets;
    final private Type type;

    /**
     * Resposible for holding the submitted ticket requests, and returning
     *
     * @param type
     * @param noOfTickets
     */
    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public BigDecimal getCostOfTickets() {
        return type.ticketPrice.multiply(BigDecimal.valueOf(this.noOfTickets));
    }

    public int getSeatsRequired() {
        return getNoOfTickets() * type.seatMultiplier;
    }

    public enum Type {
        ADULT(new BigDecimal(20.00), 1),
        CHILD(new BigDecimal(10.00), 1),
        INFANT(new BigDecimal(0.00), 0);

        private final BigDecimal ticketPrice;
        private final int seatMultiplier;

        Type(BigDecimal ticketPrice, int seatMultipler) {
            this.ticketPrice = ticketPrice;
            this.seatMultiplier = seatMultipler;
        }

        public BigDecimal getTicketPrice() {
            return ticketPrice;
        }

        public int getSeatMultiplier() {
            return seatMultiplier;
        }
    }

}
