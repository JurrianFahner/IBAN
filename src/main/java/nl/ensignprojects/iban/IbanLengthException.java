package nl.ensignprojects.iban;

/**
 * Exception class for IBAN length.
 * @author Jurrian Fahner
 */
public class IbanLengthException extends Exception {

    /**
     * Creates a new instance of <code>IbanLengthException</code> without detail
     * message.
     */
    public IbanLengthException() {
    }

    /**
     * Constructs an instance of <code>IbanLengthException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IbanLengthException(String msg) {
        super(msg);
    }
}
