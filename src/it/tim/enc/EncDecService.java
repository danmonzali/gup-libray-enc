package it.tim.enc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.tim.enc.exception.EncryptDecryptException;
import it.tim.enc.pojo.DecEncPojo;
import it.tim.enc.pojo.DecryptPojo;
import it.tim.enc.pojo.EncryptPojo;

@Service
public class EncDecService {

	@Autowired
	private DecryptService decryptService;

	@Autowired
	private EncryptService encryptService;

	public EncryptPojo decryptAndEncrypt(DecEncPojo decEncPojo) throws EncryptDecryptException {

		DecryptPojo decryptPojo = new DecryptPojo();
		decryptPojo.setChiperText(decEncPojo.getChiperText());
		decryptPojo.setIv(decEncPojo.getIv());
		decryptPojo.setKeyName(decEncPojo.getIv());
		String decString = decryptService.decryptText(decryptPojo);

		EncryptPojo encryptPojo = new EncryptPojo();
		encryptPojo.setClearText(decString);
		encryptPojo.setKeyGroup(decEncPojo.getKeyGroup());
		encryptPojo = encryptService.encryptText(encryptPojo);

		return encryptPojo;
	}

}
