package Domain.Exceptions;

public class ClientValidatorException extends RuntimeException {

    public ClientValidatorException (String s) {
        super("Client validator exception: " + s);
    }
}
