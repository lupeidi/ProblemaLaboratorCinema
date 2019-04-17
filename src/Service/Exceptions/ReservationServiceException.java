package Service.Exceptions;

public class ReservationServiceException extends RuntimeException {

    public ReservationServiceException(String s) {
        super("Reservation service exception: " + s);
    }
}