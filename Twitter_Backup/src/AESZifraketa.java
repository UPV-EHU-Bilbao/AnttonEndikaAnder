
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESZifraketa {

	private Cipher zifratu;
	private Cipher dezifratu;
	private int mezuLuzera = 200;
	private SecretKey pasahitza;

	public AESZifraketa(String mezua) {
		try {
			zifratu = Cipher.getInstance("AES");
			dezifratu = Cipher.getInstance("AES");
			int luzera = mezua.length();
			if (luzera<5) throw new Exception();
			int bete = 0;
			for (int i = luzera; i < mezuLuzera; i++) {
				if (i >= mezuLuzera-2) mezua = mezua + "=";
				else mezua = mezua + bete;
				if (bete == 9) bete = 0;
				else bete++;
			}
			pasahitza = new SecretKeySpec(DatatypeConverter.parseBase64Binary(mezua), "AES");
			zifratu.init(Cipher.ENCRYPT_MODE, pasahitza);
			dezifratu.init(Cipher.DECRYPT_MODE, pasahitza);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String str) throws Exception {
		// Stringa kodetu byte erara utf-8 erabiliz
		byte[] utf8 = str.getBytes("UTF8");

		// zifratu
		byte[] enc = zifratu.doFinal(utf8);

		System.out.println(DatatypeConverter.printBase64Binary(enc));//konprobaketa

		// Byta kodetu base64 erabilita stringa lortzeko
		return DatatypeConverter.printBase64Binary(enc);
	}

	public String decrypt(String str) throws Exception {
		// Base64 dekodetu bytak lortzeko
		byte[] dec = DatatypeConverter.parseBase64Binary(str);
		byte[] utf8 = dezifratu.doFinal(dec);

		System.out.println(new String(utf8, "UTF8"));//konprobaketa

		// utf-8 erabiliz dekodetu
		return new String(utf8, "UTF8");
	}

	public String generateKey() {
		String key = "";
		Random r = new Random();
		for (int i = 0; i < mezuLuzera; i++) {
			int aukera = r.nextInt(3);
			if (i >= mezuLuzera-2) key = key + "=";
			else if (aukera == 0) key = key + ((char)(r.nextInt(26)+65));
			else if (aukera == 1) key = key + ((char)(r.nextInt(26)+97));
			else key = key + ((char)(r.nextInt(9)+48));
		}
		return key;
	}

}
