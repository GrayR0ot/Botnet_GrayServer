package eu.grayroot.grayserver.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.grayroot.grayserver.Main;

public class ClientData {

	private Connection connection;

	public ClientData(Connection connection) {
		this.connection = Main.server.getConnection();
	}

	public boolean isRegistered(String hash){
		try {
			PreparedStatement q = connection.prepareStatement("SELECT `hash` FROM `clients` WHERE `hash` = ?");
			q.setString(1, hash);
			ResultSet resultat = q.executeQuery();
			boolean isRegistered = resultat.next();
			q.close();
			return isRegistered;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void registerClient(Client client){
		try {
			PreparedStatement rs = connection.prepareStatement("INSERT INTO `clients` (name, os, publicIP, privateIP, MAC, hash) VALUES (?, ?, ?, ?, ?, ?)");
			rs.setString(1, client.getName());
			rs.setString(2, client.getOs());
			rs.setString(3, client.getPublicIP());
			rs.setString(4, client.getPrivateIP());
			rs.setString(5, client.getMAC());
			rs.setString(6, client.getHash());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateClient(Client client){
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE `clients` set `name` = ?, `os` = ?, `privateIP` = ? WHERE `hash` = ?");
			rs.setString(1, client.getName());
			rs.setString(2, client.getOs());
			rs.setString(3, client.getPrivateIP());
			rs.setString(4, client.getHash());
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Client getClientData(String hash){
		Client client = null;
		try {
			PreparedStatement q = connection.prepareStatement("SELECT * FROM `clients` WHERE `hash` = ?");
			q.setString(1, hash);
			ResultSet rs = q.executeQuery();
			while(rs.next()) {
				client = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("os"), rs.getString("publicIP"), rs.getString("privateIP"), rs.getString("MAC"), rs.getString("hash"));
			}
			q.close();
			return client;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return client;
	}

}
