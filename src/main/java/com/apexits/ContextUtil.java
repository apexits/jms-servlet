package com.apexits;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextUtil {

    public static Context getInitialContext() throws NamingException {

         	
    	     
       
       
        final String DEFAULT_USERNAME = "jmsuser";
        final String DEFAULT_PASSWORD = "jmsuser@123";
        final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
       //final String PROVIDER_URL = "remote://127.0.0.1:4447";
        //final String PROVIDER_URL = "remote://127.0.0.1:4547";
        final String PROVIDER_URL = "remote://127.0.0.1:4647";
        //final String PROVIDER_URL = "remote://127.0.0.1:4747";   	
    	
        Context namingContext = null;

        
            String userName = System.getProperty("username", DEFAULT_USERNAME);
            String password = System.getProperty("password", DEFAULT_PASSWORD);

            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);
            System.out.println("Creating Initial context");
            namingContext = new InitialContext(env);

        return namingContext;

    }
    
    

}

