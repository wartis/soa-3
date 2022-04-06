package ru.wartis.soa2.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import ru.wartis.soa2.soap.ServiceFault;

@SoapFault(
    faultCode = FaultCode.CUSTOM,
    customFaultCode = "{http://wartis}NOT_FOUND"
)
public class UnloadAllSpaceMarinesRequestFault extends Exception {

    final ServiceFault serviceFault;

    public ServiceFault getServiceFault() {
        return serviceFault;
    }

    public UnloadAllSpaceMarinesRequestFault(String message) {
        super(message);
        serviceFault = new ServiceFault("NOT_FOUND", message);
    }
}
