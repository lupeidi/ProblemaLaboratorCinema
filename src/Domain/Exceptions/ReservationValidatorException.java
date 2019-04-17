package Domain.Exceptions;

public class ReservationValidatorException extends RuntimeException{

    public ReservationValidatorException (String s) {
        super("Reservation validator exception: " + s);
    }
}
