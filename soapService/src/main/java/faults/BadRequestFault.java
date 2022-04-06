package faults;

import javax.xml.ws.WebFault;

@WebFault(name = "badRequestFault")
public class BadRequestFault extends Exception {

    private String message;
    private int code;

    public BadRequestFault(String message, int code) {
        super(message);
        this.code = code;
    }

    public BadRequestFault(String message) {
        super(message);
    }

}
