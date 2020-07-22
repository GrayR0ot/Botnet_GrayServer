package eu.grayroot.grayserver;

import java.security.SecureRandom;
public class RandomString {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();
	public static String Generate( int len ){
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
}
