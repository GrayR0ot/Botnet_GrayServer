package eu.grayroot.grayserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool extends Thread{
	private ServerSocket server;
	private ConcurrentHashMap <Socket,Streams> dialog;
	private ExecutorService executor = Executors.newFixedThreadPool(4);
	public ThreadPool(ServerSocket server, ConcurrentHashMap<Socket, Streams> dialog) {
		if (server == null || dialog == null) throw new IllegalStateException();
		this.server = server;
		this.dialog = dialog;
	}


	public void run() {
		while (server == null || dialog == null || executor == null);
		for(int i=0;i<4;i++)
			executor.submit(new SingleShot(server,executor,dialog));
	}

	public void shutdown() {

	}

}
