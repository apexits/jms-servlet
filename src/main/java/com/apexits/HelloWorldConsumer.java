package com.apexits;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;
import com.apexits.ContextUtil;

public class HelloWorldConsumer implements MessageListener {

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

                                 

            System.out.println("Create consumer");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.receive();    //this line of code make it receive all messages on the queue
          
             

            System.out.println("set message listener");
            consumer.setMessageListener(new HelloWorldConsumer());         

        } finally {

            if (connection != null) {

                System.out.println("close the connection");

                connection.close();

            }

        }

    }
 

    @Override

    public void onMessage(Message message) {

    		
        try {        	
        	              
             
            	 String text =((TextMessage) message).getText();      	
        	        	
        		System.out.println("message received");

        		System.out.println(text);        	
        		
        		//System.out.println("No message on the Queue");
             

        } catch (JMSException e) {

            e.printStackTrace();

        }

    }
 

}
