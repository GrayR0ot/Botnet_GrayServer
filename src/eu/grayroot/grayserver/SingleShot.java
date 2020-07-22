package eu.grayroot.grayserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class SingleShot implements Runnable {
	private ServerSocket server;
	private ConcurrentHashMap <Socket,Streams> dialog;
	private ExecutorService executor;
	public SingleShot(ServerSocket server, ExecutorService executor, ConcurrentHashMap<Socket, Streams> dialog) {
		if (server == null || dialog == null) throw new IllegalStateException();
		this.server = server;
		this.dialog = dialog;
		this.executor = executor;
	}

	@Override
	public void run() {
		try {
			Socket socket = server.accept();
			Main.sockets.add(socket);
			if (dialog.putIfAbsent(socket, new Streams(socket)) == null) {
				System.out.println(ConsoleColors.GREEN + "[+] " + socket.getRemoteSocketAddress().toString().replace("/", "") + ConsoleColors.RESET);
			}
			executor.submit(new SingleShot(server,executor,dialog));

		}
		catch (Exception e) {
			executor.submit(new SingleShot(server,executor,dialog));
		}
	}

}
