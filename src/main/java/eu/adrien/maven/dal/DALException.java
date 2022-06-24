package eu.adrien.maven.dal;

public class DALException extends Exception{

    public DALException() {
        super();
    }

    public DALException(String message) {
        super("Erreur DAL " + message);
    }

    public DALException(String message, Throwable cause) {
        super("Erreur DAL " + message, cause);
    }
}
