package eu.grayroot.grayserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;

import eu.grayroot.grayserver.sql.SqlConnection;

public class Server {
	private SqlConnection sql;
	private Connection sqlConnection;
	private ServerSocket server;
	private ConcurrentHashMap <Socket,Streams> dialog = new  ConcurrentHashMap <Socket,Streams>();
	private ThreadPool tp;
	private boolean running = false;
	public Server(int port) throws IOException {
		sql = new SqlConnection("jdbc:mysql://", "127.0.0.1", "dbName", "dbUser", "dbPassword");
		sql.connect();
		sqlConnection = sql.getConnection();
		server = new ServerSocket(port);
		tp = new ThreadPool(server,dialog);
	}

	public boolean isRunning() {
		return running;
	}
	public void startserver() {
		if (isRunning()) throw new IllegalStateException("Server Is Running");
		running = true;
		tp.start();
	}
	public void stopserver() throws IOException {
		if (isRunning()) System.out.println("Server Is Stopped");
		running = false;
		server.close();
		tp.shutdown();
		System.exit(1);
	}
	public ConcurrentHashMap <Socket,Streams> getmap() {
		return dialog;
	}
	
	public SqlConnection getSqlConnection() {
		return sql;
	}
	
	public Connection getConnection() {
		return sqlConnection;
	}
}
