package setproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESFileDecryption {
	AESFileDecryption(String new_path, String password, String old_path) throws Exception {

		// reading the salt
		// user should have secure mechanism to transfer the
		// salt, iv and password to the recipient
		FileInputStream saltFis = new FileInputStream("/Users/shreya/Downloads/Encoded/salt.enc");
		byte[] salt = new byte[8];
		saltFis.read(salt);
		saltFis.close();

		File myObj = new File("/Users/shreya/Downloads/Encoded/salt.enc");
		myObj.delete(); 

		// reading the iv
		FileInputStream ivFis = new FileInputStream("/Users/shreya/Downloads/Encoded/iv.enc");
		byte[] iv = new byte[16];
		ivFis.read(iv);
		ivFis.close();

		File myObj1 = new File("/Users/shreya/Downloads/Encoded/iv.enc");
		myObj1.delete();

		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
				256);
		SecretKey tmp = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

		// file decryption
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		FileInputStream fis = new FileInputStream(new_path);
		FileOutputStream fos = new FileOutputStream(old_path);
		byte[] in = new byte[64];
		int read;
		while ((read = fis.read(in)) != -1) {
			byte[] output = cipher.update(in, 0, read);
			if (output != null)
				fos.write(output);
		}

		byte[] output = cipher.doFinal();
		if (output != null)
			fos.write(output);
		fis.close();
		fos.flush();
		fos.close();

		File myObj2 = new File(new_path);
		myObj2.delete();

		System.out.println("File Decrypted.");
	}
}