package ru.wartis.soa2.soap;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import ru.wartis.soa2.exceptions.UnloadAllSpaceMarinesRequestFault;

import javax.xml.namespace.QName;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("code");
    private static final QName DESCRIPTION = new QName("description");

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed ", ex);
        if (ex instanceof UnloadAllSpaceMarinesRequestFault) {
            ServiceFault serviceFault = ((UnloadAllSpaceMarinesRequestFault) ex).getServiceFault();
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText(serviceFault.getCode());
            detail.addFaultDetailElement(DESCRIPTION).addText(serviceFault.getDescription());
        }
    }

}
