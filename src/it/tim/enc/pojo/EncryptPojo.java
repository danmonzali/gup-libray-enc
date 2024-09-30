package it.tim.enc.pojo;

public class EncryptPojo {

	private String clearText;
//	private String masterKey;
	private String keyGroup;

	private String keyId;
	private String iv;
	private String chiperText;

	public String getClearText() {
		return clearText;
	}

	public void setClearText(String clearText) {
		this.clearText = clearText;
	}
//
//	public String getMasterKey() {
//		return masterKey;
//	}
//
//	public void setMasterKey(String masterKey) {
//		this.masterKey = masterKey;
//	}

	public String getKeyGroup() {
		return keyGroup;
	}

	public void setKeyGroup(String keyGroup) {
		this.keyGroup = keyGroup;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getChiperText() {
		return chiperText;
	}

	public void setChiperText(String chiperText) {
		this.chiperText = chiperText;
	}

}
