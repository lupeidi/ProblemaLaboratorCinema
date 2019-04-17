package Domain;
import Domain.Exceptions.ClientValidatorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ClientValidator implements IValidator<Client> {
    public void validate(Client client) {
        if (client.getCnp().length() != 13) {
            throw new ClientValidatorException("Client CNP is not valid!");
        }
        if (client.getPoints() < 0) {
            throw new ClientValidatorException("Bonus points must be positive.");
        }
        int curentYear = Calendar.getInstance().get(Calendar.YEAR);
        int dif = curentYear - client.getBirthdayDate().getYear();
        if (dif > 100 || dif < 10) {
            throw new ClientValidatorException("Client must be between 10 and 100 years old");
        }
        if (curentYear - client.getRegistrationDate().getYear() > 10) {
            throw new ClientValidatorException("Registration date must be at most 10 years ago");
        }

        if (!client.getId().equals(new StringBuilder(client.getId()).reverse().toString())) {
            throw new ClientValidatorException("Id must be a palindrome");
        }
    }
}
