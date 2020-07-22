package eu.grayroot.grayserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Streams {
	private PrintWriter writer;
	private BufferedReader reader;
	public Streams(Socket socket) throws IOException {
		if (socket == null) throw new IllegalStateException();
		writer = new PrintWriter(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public boolean sendMsG(String Msg) {
		try {
			writer.println(Msg);
			writer.flush();
		}
		catch (Exception e) {return false;}
		return true;
	}
	public String ReadMsg() {
		try {
			String msg = reader.readLine();
			return msg;
		}
		catch (Exception e) {return null;}
	}	
}
