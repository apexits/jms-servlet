package com.apexits;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.PrintWriter;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: UserServlet
 *
 */
public class UserServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    @Resource(name="jms/TestConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(name="jms/TestQueue")
    private Queue queue;
    static final long serialVersionUID = 1L;


    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        if(connectionFactory == null) {
            out.println("Connection Factory lookup has failed");
            return;
        }

        if(queue == null) {
            out.println("Queue lookup has failed");
            return;
        }



        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage();
            Enumeration arr=request.getParameterNames();
            while(arr.hasMoreElements())
            {
                String fields= (String)arr.nextElement();
                String paramname[]=request.getParameterValues(fields);
                for (int i=0; i<paramname.length;i++)
                {
                    String s=null;
                    s=fields+":" + paramname[i];
                    message.setText(s);
                    producer.send(message);
                }

            }
            out.println("Your request has been sent to administrator.");
            //Send a non-text control message indicating end of messages.
            producer.send(session.createMessage());
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (JMSException e1) { }
            }
        }
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}