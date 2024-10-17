package it.tim.enc.pojo;

public class DecryptPojo {

	
	private static final String CLEAR_TEXT = "CLEAR_TEXT";
	private static final String IV = "IV";
	private static final String KEY_NAME = "KEY_NAME";
	private static final String MASTER_KEY = "MASTER_KEY";
	private static final String CIPHER_TEXT = "CIPHER_TEXT";
	private String chiperText;
//	private String masterKey;
	private String keyName;
	private String iv;

	public String getChiperText() {
		return chiperText;
	}

	public void setChiperText(String chiperText) {
		this.chiperText = chiperText;
	}


	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	@Override
	public String toString() {
		return "DecryptPojo [chiperText=" + chiperText + ", keyName=" + keyName + ", iv=" + iv + "]";
	}

}
