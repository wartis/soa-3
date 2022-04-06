package faults;

import javax.xml.ws.WebFault;

@WebFault(name = "serviceUnavailableFault")
public class ServiceUnavailableFault extends Exception {

    private String message;
    private int code;

    public ServiceUnavailableFault(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceUnavailableFault(String message) {
        super(message);
    }

}
