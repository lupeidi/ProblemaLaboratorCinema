package Domain;

import Domain.Exceptions.ReservationValidatorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservationValidator implements IValidator<Reservation> {

    public void validate(Reservation reservation) {
        if (reservation.getDate().getYear() > (Calendar.getInstance().get(Calendar.YEAR) + 1) ){
            throw new ReservationValidatorException("Reservation year is not allowed yet");
        }
        else if (reservation.getDate().getYear() < Calendar.getInstance().get(Calendar.YEAR) ){
            throw new ReservationValidatorException("Reservation year must not be in the past");
        }

        if (!reservation.getId().equals(new StringBuilder(reservation.getId()).reverse().toString())) {
            throw new ReservationValidatorException("Id must be a palindrome");
        }
    }
}
