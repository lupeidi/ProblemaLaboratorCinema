package Service.Exceptions;

    public class ClientServiceException extends RuntimeException {

        public ClientServiceException(String s){
            super("Client service exception: " + s);
        }
}
