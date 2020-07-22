package eu.grayroot.grayserver.cipher;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

	public RSA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KeyPair getRSAKey() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			return kp;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PrivateKey loadPrivateKey() {
		PrivateKey priv = null;
		String privateKey = "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCiDsYqfXc9XR+pHOt8pcjCUfujq3lzu7mcG4FUN9wxFTXW9+lmu8NJi1EuLmB3RSrq9YBdx/qddcuNriU9EH0Evj+UTHybFaapJYA9ajpymlkiu2Gdsa1TNMidMdx0qkaBWwK36T9pgNAsqbnLVi8kawgPHn+fJQD5ku2It85fAiLxXgbLQK5Eh6SfL9+2kVUWVLvtyRWtKcwmL4JSPmSUAwzrc+Q7lCqFiZjAbqabqgzoKomqDMrVUSGut/8iTJ3Pn881HVeCLIVGRiWbwaKGhEH9p8AEWtbmcKcFKQF3OKUGec/GkiMcIQq92NMl7KJUIWntF1GXsAkLdq+reuT1AgMBAAECggEAbn+AY0x1V2P9yIOtlao8vzLDUBAFmgIwRt8CSfhkrRr3QQJ9JM12FNlnmwOIRwrkfpyy0D8hBOhiyNIrFf3+IJPQKkAU3nHe14p4bGfYPng7utDleG7D7e4ZtAVhejL4LA9wRRJHaJqqVynLQ3gaYMySDC0Re+PxTDsAt64Miv6FczcVp/taGB2/EOFyqCzCkEwcHkmlv1NMFjANTtjUwdgSAMT1dzGK52lBgIqrMjvnnVqWTQu5ni9ga/ddYPjG25KqjBZ2qgWFdzIMIItbYUaN7YmulbfHuGqXTvD3ceo1VUbhmHQ3Wxx1NITkw5koI3f9QJtVw6wVFQopw9elKQKBgQDa9ax+KDadz50GOFCzK73Q0/ScgQippEfcTLaUbigcus6PAAQR5A2APv2SPC1vegDarqTFOH3ZBy5manIL43I85cVPBzr/Gt80QnVLZ8sGeKdKLV9o8entClsKt8YNlq0VFGZJQqCSnna3HMxO50x+9BYoSNEVuZ/qYJHPZYK4hwKBgQC9eOOWahawtSBiEsTkgn0EnThOBWd/lmQWrsTFVkBDmAF+kWV/NClmD/BDjZeBR927EY6Lw6oPqiE6IWK8WYC5C1jg9VWg8IqnEd2kn43oyG4BJrACSc1+w2E4v6/vVbbAMnHOW/Idj/EE3+HrvU144W9zOjICQ3/FYeySMFohowJ/dFIPUPYMDco9oRBJzuVt8YKocHwcHgMoKeCXHkNnC9wx38YiY17DzV2pWAagHJiz23jsD9nzAVjPTZk+/RHXnoJyT4mHzf54lUq5BVYehVGTjdSM2zOpEkRuGUOH87AYQxqTPFgo9bs5Vg32e/Rkrwz8uGn2qZPzYI/s5kEHMQKBgB2zMnGkhZQDgGT/Li/nqHMtteK9BCFC0MOhd6S7RW2TCUDux6st4QNnojDkpAgW3NHzCZYtAJ5d+8Fh9Fkz6nKXLcJtkpOcwQ85RLYGcLc5m8zAZLynFvg05iuAGLL6i6ALCD3huc0agBxUf9R1Iwy8wbydNbOXIxMsq2mq/KXjAoGAeruLiNZpRwY12n6QVsBF2IPmxMqtXZvUF5SonZEQkbK0+lmIXSHiweTi1YyCfuV7p1XbfKdJOQpfkIhH48XkZaiMZnX3zaDmdaRMCZskn1bnMC/xkhZA/hPWIj5mc/jPPUPL1/Xy0hX9uJ9QwbQFObKJSJhr7meJu4Z/MDCzyCY=";
		byte[] buffer = Base64.getDecoder().decode(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			priv = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return priv;
	}

	public PublicKey loadPublicKey() {
		PublicKey pub = null;
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtZ3OmLkgI4Js00uc0pLP9TFEMM4sT6xMf9ackSoIbcXPRQhK43QhzM/vZ0GFKscRLAhf/RTJjGn4OzSm0xtVoZGbmfhvQpvS6XDaV3kPO+FkYMRsTol1sYFyTd9/i7w02K4dtLeag4ms/xMPZXyR+RjEiE6lcn2WxEi5ayUPFEnbTO+2CwtsptfxRHDC0qIBoyp+UFgW1cZckA7ftdi2jfFIoNhSux0rE6pYHRkro5suwYAKHGjBeW5QlJgrOjbqYs0IWOM2ZQMm4qfMhPut/dWcuEACCAXHpfpigOJE42W5nKB1lRGVdM867TUB8Sf0v3ESph23lksg1m5pEGXIgQIDAQAB";
		byte[] buffer = Base64.getDecoder().decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pub = keyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pub;
	}

	public byte[] encryptText(String text, PublicKey publicKey) {
		byte[] encryptedText = null;

		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedText = cipher.doFinal(text.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	public String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}

	public String decrypt(String data, PrivateKey privateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return decrypt(Base64.getDecoder().decode(data.getBytes()), privateKey);
	}

}
