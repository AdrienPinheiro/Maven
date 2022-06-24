package eu.adrien.maven.bll;

public class BLLException extends Exception{
    public BLLException() {
        super();
    }

    public BLLException(String message) {
        super("Erreur BLL :" + message);
    }

    public BLLException(String message, Throwable cause) {
        super("Erreur BLL :" + message, cause);
    }
}
