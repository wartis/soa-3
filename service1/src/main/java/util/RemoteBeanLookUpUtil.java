package util;

import service.SpaceMarineService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public final class RemoteBeanLookUpUtil {

    final static Hashtable jndiProps = new Hashtable();

    public static SpaceMarineService lookupSpaceMarineServiceBean()  {
        try {
            System.out.println("JUST POOLED EJB");
            return (SpaceMarineService) getContext().lookup(System.getenv("BASE_LOOKUP"));
        } catch (NamingException e) {
            System.out.println("--------");
            System.out.println(e.getMessage());
            System.out.println("-----------");
            throw new RuntimeException("Can't lookup SpaceMarineService");
        }
    }

    private static Context getContext() throws NamingException {
        jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return new InitialContext(jndiProps);
    }

    private RemoteBeanLookUpUtil() {}

}
