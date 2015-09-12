package networking;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;

/**
 *
 * @author Leon
 */
public class Server extends JFrame{
    private JTextArea jta = new JTextArea();
    private DataInputStream inputFromClient;
    private DataOutputStream outputToClient;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        new Server();
    }
    public Server(){
        setLayout(new BorderLayout());
        add(new JScrollPane(jta),BorderLayout.CENTER);
        
        setTitle("Server");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        try{
            ServerSocket serverSocket= new ServerSocket(8000);
            jta.append("Server started at " + new Date() + '\n');
            
            while(true){
                Socket socket = serverSocket.accept();
                
                HandleAClient task = new HandleAClient(socket);
                
                new Thread(task).start();
                
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    class HandleAClient implements Runnable{
        private Socket socket;
        public HandleAClient(Socket socket){
            this.socket=socket;
        }
        
        @Override
        public void run(){
            try{
                inputFromClient = new DataInputStream(socket.getInputStream());
                outputToClient = new DataOutputStream(socket.getOutputStream());
                while(true){
                    double radius = inputFromClient.readDouble();

                    double area = radius * radius * Math.PI;

                    outputToClient.writeDouble(area);

                    jta.append("Radius received from client: " + radius + '\n');
                    jta.append("Area found: " + area + '\n');
                }
            }
            catch(Exception ex){
                
            }
        }
    }
}

