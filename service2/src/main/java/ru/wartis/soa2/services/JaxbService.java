package ru.wartis.soa2.services;


import ru.wartis.soa2.entities.SpaceMarine;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;


public class JaxbService {
    
    public static <T> T fromStr(String str, Class<T> tClass) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(tClass);

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new StringReader(str));
    }

    public static String jaxbObjectToXML(SpaceMarine marine)
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(SpaceMarine.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(marine, sw);

            //Verify XML Content
            String xmlContent = sw.toString();

            return xmlContent;
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}


