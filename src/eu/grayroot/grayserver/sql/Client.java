package eu.grayroot.grayserver.sql;

public class Client {
	
	private int id;
	private String name;
	private String os;
	private String publicIP;
	private String privateIP;
	private String MAC;
	private String hash;
	public Client(int id, String name, String os, String publicIP, String privateIP, String mAC, String hash) {
		super();
		this.id = id;
		this.name = name;
		this.os = os;
		this.publicIP = publicIP;
		this.privateIP = privateIP;
		MAC = mAC;
		this.hash = hash;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getPublicIP() {
		return publicIP;
	}
	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}
	public String getPrivateIP() {
		return privateIP;
	}
	public void setPrivateIP(String privateIP) {
		this.privateIP = privateIP;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mAC) {
		MAC = mAC;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	

}
