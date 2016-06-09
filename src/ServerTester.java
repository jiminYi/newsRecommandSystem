import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ServerTester implements Runnable {
	ServerSocket serverSocket;
	Thread[] threadArr;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		
		ServerTester server = new ServerTester(5);
		server.start();
	}
	
	public ServerTester(int num) {
		try {
			serverSocket = new ServerSocket(5559); 
			threadArr = new Thread[num];
		} catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void start() {
        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread(this);
            threadArr[i].start();
        }
    }
	
	@Override
    public void run() {
		ArrayList<String> url_title = new ArrayList<String>();
		RecommendationCotroller recommendationCotroller = new RecommendationCotroller();
		
        while (true) {
            try {
            	System.out.println(getTime() + " 가 연결 요청을 기다립니다.");
            	 
                Socket socket = serverSocket.accept();
                System.out.println("connection");
                
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
    	        ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
    	           
    	        String clientId = inFromClient.readLine(); 
    	        System.out.println(clientId);
    	        
    	        url_title = recommendationCotroller.recommend(clientId);

    	        outToClient.writeObject(url_title);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
	}
    
	static String getTime() {
        String name = Thread.currentThread().getName();
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date()) + name;
    }
}