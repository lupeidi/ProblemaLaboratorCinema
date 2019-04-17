package Repository.Exceptions;

public class RepositoryException extends RuntimeException{

    public RepositoryException(String s){
        super("Repository Exception: " + s);
    }
}
