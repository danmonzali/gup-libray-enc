package it.tim.enc.pojo;

public class DecEncPojo {

	private String chiperText;
	private String iv;
	private String keyName;
	private String keyGroup;

	public DecEncPojo() {
		super();
	}

	public DecEncPojo(String chiperText, String iv, String keyName, String keyGroup) {
		super();
		this.chiperText = chiperText;
		this.iv = iv;
		this.keyName = keyName;
		this.keyGroup = keyGroup;
	}

	public String getChiperText() {
		return chiperText;
	}

	public void setChiperText(String chiperText) {
		this.chiperText = chiperText;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyGroup() {
		return keyGroup;
	}

	public void setKeyGroup(String keyGroup) {
		this.keyGroup = keyGroup;
	}

}
