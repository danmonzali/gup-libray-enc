package it.tim.enc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.tim.enc.exception.EncryptDecryptException;
import it.tim.enc.pojo.BaseEnc;
import it.tim.enc.pojo.DecryptPojo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Service
public class DecryptService extends BaseEnc {

	private static final Logger logger = LogManager.getLogger(DecryptService.class);

	private static final String CRYPTO_UTILS_DECRYPT = "CRYPTO_UTILS.DECRYPT";
	@PersistenceContext
	private EntityManager entityManager;

	@Value("${master.key.encrypt.decrypt}")
	private String masterKeyEncryptDecrypt;
	
	@Transactional
	public String decryptText(DecryptPojo decryptPojo) throws EncryptDecryptException {
		if (decryptPojo == null) {
			throw new EncryptDecryptException("Nessun dato da decryptare");
		}
		try {
			// Crea una StoredProcedureQuery per la funzione PL/SQL
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CRYPTO_UTILS_DECRYPT);
			// PROCEDURE CRYPT(CLEAR_TEXT IN VARCHAR2, MASTER_KEY IN VARCHAR2, KEY_GROUP IN
			// VARCHAR2, KEY_ID OUT VARCHAR2, IV OUT VARCHAR2,CIPHER_TEXT OUT VARCHAR2);
			// Registrare i parametri di input
			query.registerStoredProcedureParameter(CIPHER_TEXT, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(MASTER_KEY, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(KEY_NAME, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(IV, String.class, ParameterMode.IN);

			// Registrare i parametri di output
			query.registerStoredProcedureParameter(CLEAR_TEXT, String.class, ParameterMode.OUT);

			// Impostare i parametri di input
			query.setParameter(CIPHER_TEXT, decryptPojo.getChiperText());
			query.setParameter(MASTER_KEY, masterKeyEncryptDecrypt);
			query.setParameter(KEY_NAME, decryptPojo.getKeyName());
			query.setParameter(IV, decryptPojo.getIv());

			// Eseguire la funzione e ottenere il risultato
			query.execute();

			// Ottenere i parametri di output
			String clearText = (String) query.getOutputParameterValue(CLEAR_TEXT);

			return clearText;
		} catch (Throwable t) {
			logger.error("Errore in fase di decrypt: " + decryptPojo.toString());
			throw new EncryptDecryptException("Errore in fase di decrypt: " + decryptPojo.toString());
		}

	}

}
