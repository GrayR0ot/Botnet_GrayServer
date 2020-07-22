package eu.grayroot.grayserver.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

	private Connection connection;
	private String urlbase,host,database,user,pass;

	public SqlConnection(String urlbase, String host, String database, String user, String pass) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
	}

	public void connect(){
		if(!isConnected()){
			try {
				connection = DriverManager.getConnection(urlbase + host + " /" + database + "?autoReconnect=true&useSSL=false", user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void disconnect(){
		if(isConnected()){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected(){
		return connection != null;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
