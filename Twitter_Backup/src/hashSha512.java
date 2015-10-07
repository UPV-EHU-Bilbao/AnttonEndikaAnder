import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashSha512 {
	
	private String hash;
	
	public hashSha512(String pHash){
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(pHash.getBytes(), 0, pHash.length());
			this.hash=new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getHash(){
		return this.hash;
	}

}
