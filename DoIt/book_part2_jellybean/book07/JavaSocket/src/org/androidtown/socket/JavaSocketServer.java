package org.androidtown.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 표준 자바에서 소켓 서버를 사용하는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class JavaSocketServer {

	public static void main(String[] args) {

		try {

			int portNumber = 11001;

			System.out.println("Starting Java Socket Server ...");
			ServerSocket aServerSocket = new ServerSocket(portNumber);
			System.out.println("Listening at port " + portNumber + " ...");

			while(true) {
				Socket aSocket = aServerSocket.accept();
				InetAddress clientHost = aSocket.getLocalAddress();
				int clientPort = aSocket.getPort();
				System.out.println("A client connected. host : " + clientHost + ", port : " + clientPort);

				ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
				Object obj = instream.readObject();
				System.out.println("Input : " + obj);

				ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
				outstream.writeObject(obj + " from Server.");
				outstream.flush();
				aSocket.close();
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}

}
