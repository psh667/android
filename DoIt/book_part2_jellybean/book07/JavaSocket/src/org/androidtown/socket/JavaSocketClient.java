package org.androidtown.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 표준 자바에서 소켓을 사용하는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class JavaSocketClient {

	public static void main(String[] args) {

		try {

			int portNumber = 11001;
			Socket aSocket = new Socket("localhost", portNumber);
			ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
			outstream.writeObject("Hello Android Town");
			outstream.flush();

			ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
			System.out.println(instream.readObject());
			aSocket.close();

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
