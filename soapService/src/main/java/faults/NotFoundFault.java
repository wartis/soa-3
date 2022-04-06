package faults;

import javax.xml.ws.WebFault;

@WebFault(name = "notFoundFault")
public class NotFoundFault extends Exception {

    private String message;
    private int code;

    public NotFoundFault(String message, int code) {
        super(message);
        this.code = code;
    }

    public NotFoundFault(String message) {
        super(message);
    }

}
