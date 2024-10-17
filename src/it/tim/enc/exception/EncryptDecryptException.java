package it.tim.enc.exception;

public class EncryptDecryptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7069242920307745978L;

	public EncryptDecryptException() {
	}

	public EncryptDecryptException(String message) {
		super(message);
	}

	public EncryptDecryptException(Throwable cause) {
		super(cause);
	}

	public EncryptDecryptException(String message, Throwable cause) {
		super(message, cause);
	}

}
