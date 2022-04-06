package ru.wartis.soa2.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(
    faultCode = FaultCode.CUSTOM,
    customFaultCode = "{http://wartis}BAD_REQUEST"
)
public class ResponseParsingException extends RuntimeException {
    public ResponseParsingException(String message) {
        super(message);
    }
}
