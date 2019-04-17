package Domain.Exceptions;

public class MovieValidatorException extends RuntimeException {

    public MovieValidatorException (String s) {
        super("Movie validator exception: " + s);
    }
}
