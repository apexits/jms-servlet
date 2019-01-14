package com.apexits;
//import java.util.Properties;

import com.apexits.ContextUtil;

import javax.jms.Connection;
//import javax.jms.QueueConnection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
//import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.NamingException;
//import javax.naming.InitialContext;


public class HelloWorldProducer {

    public static void main(String[] args) throws NamingException, JMSException {

        Connection connection = null;
        String userName = "jmsuser";
        String password = "jmsuser@123";
        String queueName = "jms/queue/myTestQueue1";

        try {

            
        	System.out.println("Create JNDI Context");        	
        	
        	Context context = ContextUtil.getInitialContext();
        	
        	
        	System.out.println("Get connection facory");
  		  	ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory"); 
  		  
  		  	                     

            System.out.println("Start connection");            
            connection = cf.createConnection(userName, password);
            connection.start();
            
            System.out.println("Create session");
            Session session = connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);     
                                
            System.out.println("Lookup queue");
 		  	 Queue queue = (Queue) context.lookup(queueName);                            


            System.out.println("Create producer");
            MessageProducer producer = session.createProducer(queue);           

                         

            System.out.println("Send hello world message");
            int msgCount = 10;
            int i = 0;
            for (i=0; i<=msgCount; i++)  {
            	
            	System.out.println("Sending  hello world message-" + Integer.toString(i));
                Message hellowWorldText = session.createTextMessage("Hello World! Message-" + Integer.toString(i));
                producer.send(hellowWorldText);
            }
            
        	}catch (NamingException ne) {
      		
        		ne.printStackTrace();
      

        	} finally {

        		if (connection != null) {

                System.out.println("close the connection");

                connection.close();

            }

 

        }

 

    }

}
